import {Component, Input} from "@angular/core";

import {BsModalService} from "ngx-bootstrap/modal";
import {BsModalRef} from "ngx-bootstrap/modal/bs-modal-ref.service";

import "rxjs/add/operator/map";
import "rxjs/add/operator/switchMap";
import {TeamDO} from "../../model/TeamDO";
import {TeamModalComponent} from "../../modals/team/teamModal.component";
import {TeamService} from "../../services/team.service";
import {ErrorService} from "../../services/error.service";
import {DriverModalComponent} from "../../modals/team/driverModal.component";
import {DriverDO} from "../../model/DriverDO";
import {DriverService} from "../../services/driver.service";
import {CompleteSeriesTO} from "../../model/CompleteSeriesTO";

@Component({
  selector: 'teams',
  templateUrl: './teams.component.html'
})

export class TeamsComponent {
  bsModalRef: BsModalRef;

  @Input() data: CompleteSeriesTO;
  @Input() editing: boolean = false;

  activeTab = 'teams';
  isTeamVisible = [];

  constructor(private modalService: BsModalService, private teamService: TeamService,
              private driverService: DriverService, private errorRenderer: ErrorService) {

  }

  public getDriverDetails(driver: DriverDO): string {
    let items: string[] = [];
    if (driver.car) {
      items.push(driver.car);
    }
    if (driver.number) {
      items.push(driver.number);
    }
    if (driver.gamertag) {
      items.push(driver.gamertag);
    }
    return items.join(", ");
  }

  public getTeamName(driver: DriverDO): string {
    for (let team of this.data.teams) {
      if (team.id === driver.teamId) {
        return team.name;
      }
    }
    return "";
  }

  public createNewTeam(): void {
    if (!this.editing) {
      return;
    }
    this.bsModalRef = this.modalService.show(TeamModalComponent, {class: 'modal-lg'});
    this.bsModalRef.content.showForNewTeam(this.data.series.id);
    this.bsModalRef.content.submitted.subscribe(res => {
      if (res) {
        this.data.addTeam(this.bsModalRef.content.resultingTeam);
      }
    });
  }

  public editTeam(team: TeamDO): void {
    if (!this.editing) {
      return;
    }
    this.bsModalRef = this.modalService.show(TeamModalComponent, {class: 'modal-lg'});
    this.bsModalRef.content.showFor(team);
    this.bsModalRef.content.submitted.subscribe(res => {
      if (res) {
        this.data.sortTeams();
      }
    });
  }

  public deleteTeam(team: TeamDO): void {
    if (!this.editing) {
      return;
    }
    let reallyDelete = confirm('Soll das Team inklusive Fahrer wirklich gelöscht werden?');
    if (!reallyDelete) {
      return;
    }
    this.teamService.deleteTeam(this.data.series.id, team.id).subscribe(
      _ => {
        this.data.deleteTeam(team);
      },
      err => {
        alert(this.errorRenderer.getFromError(err));
      }
    );
  }

  public createNewDriver(): void {
    if (!this.editing) {
      return;
    }
    this.bsModalRef = this.modalService.show(DriverModalComponent, {class: 'modal-lg'});
    this.bsModalRef.content.showForNewTeam(this.data.series.id, this.data.teams);
    this.bsModalRef.content.submitted.subscribe(res => {
      if (res) {
        this.data.addDriver(this.bsModalRef.content.resultingDriver);
      }
    });
  }

  public editDriver(driver: DriverDO): void {
    if (!this.editing) {
      return;
    }
    this.bsModalRef = this.modalService.show(DriverModalComponent, {class: 'modal-lg'});
    this.bsModalRef.content.showFor(driver, this.data.teams);
    this.bsModalRef.content.submitted.subscribe(res => {
      if (res) {
        this.data.sortDriver();
      }
    });
  }

  public deleteDriver(driver: DriverDO): void {
    if (!this.editing) {
      return;
    }
    let reallyDelete = confirm('Soll der Fahrer wirklich gelöscht werden?');
    if (!reallyDelete) {
      return;
    }
    this.driverService.deleteDriver(this.data.series.id, driver.id).subscribe(
      _ => {
        this.data.deleteDriver(driver);
      },
      err => {
        alert(this.errorRenderer.getFromError(err));
      }
    );
  }

}
