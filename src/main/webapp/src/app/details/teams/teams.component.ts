import {Component, Input} from "@angular/core";
import {SeriesDO} from "../../model/SeriesDO";

import {BsModalService} from "ngx-bootstrap/modal";
import {BsModalRef} from "ngx-bootstrap/modal/modal-options.class";

import "rxjs/add/operator/map";
import "rxjs/add/operator/switchMap";
import {TeamDO} from "../../model/TeamDO";
import {TeamModalComponent} from "../../modals/teamModal.component";
import {TeamService} from "../../services/team.service";
import {ErrorService} from "../../services/error.service";
import {DriverModalComponent} from "../../modals/driverModal.component";
import {DriverDO} from "../../model/DriverDO";
import {DriverService} from "../../services/driver.service";

@Component({
  selector: 'teams',
  templateUrl: './teams.component.html'
})

export class TeamsComponent {
  bsModalRef: BsModalRef;

  @Input() series: SeriesDO;
  @Input() teams: TeamDO[]
  @Input() drivers: DriverDO[];
  @Input() editing: boolean = false;

  activeTab = 'teams';

  constructor(private modalService: BsModalService, private teamService: TeamService,
              private driverService: DriverService, private errorRenderer: ErrorService) {

  }

  public getDriverDetails(driver: DriverDO): string {
    let items: string[] = [];
    if(driver.car) {
      items.push(driver.car);
    }
    if(driver.number) {
      items.push(driver.number);
    }
    if(driver.gamertag) {
      items.push(driver.gamertag);
    }
    return items.join(", ");
  }

  public getTeamName(driver: DriverDO): string {
    for(let team of this.teams) {
      if(team.id === driver.teamId) {
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
    this.bsModalRef.content.showForNewTeam(this.series.id);
    this.bsModalRef.content.submitted.subscribe(res => {
      if (res) {
        this.teams.push(this.bsModalRef.content.resultingTeam);
        this.teams.sort((a, b) => a.name.localeCompare(b.name));
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
        this.teams.sort((a, b) => a.name.localeCompare(b.name));
      }
    });
  }

  public deleteTeam(team: TeamDO): void {
    if (!this.editing) {
      return;
    }
    let reallyDelete = confirm('Soll das Team inklusive Fahrer wirklich gelöscht werden?');
    if(!reallyDelete) {
      return;
    }
    this.teamService.deleteTeam(this.series.id, team.id).subscribe(
      _ => {
        let i = this.teams.indexOf(team);
        this.teams.splice(i, 1);
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
    this.bsModalRef.content.showForNewTeam(this.series.id, this.teams);
    this.bsModalRef.content.submitted.subscribe(res => {
      if (res) {
        this.drivers.push(this.bsModalRef.content.resultingDriver);
        this.drivers.sort((a, b) => a.name.localeCompare(b.name));
      }
    });
  }

  public editDriver(driver: DriverDO): void {
    if (!this.editing) {
      return;
    }
    this.bsModalRef = this.modalService.show(DriverModalComponent, {class: 'modal-lg'});
    this.bsModalRef.content.showFor(driver, this.teams);
    this.bsModalRef.content.submitted.subscribe(res => {
      if (res) {
        this.drivers.sort((a, b) => a.name.localeCompare(b.name));
      }
    });
  }

  public deleteDriver(driver: DriverDO): void {
    if (!this.editing) {
      return;
    }
    let reallyDelete = confirm('Soll der Fahrer wirklich gelöscht werden?');
    if(!reallyDelete) {
      return;
    }
    this.driverService.deleteDriver(this.series.id, driver.id).subscribe(
      _ => {
        let i = this.drivers.indexOf(driver);
        this.drivers.splice(i, 1);
      },
      err => {
        alert(this.errorRenderer.getFromError(err));
      }
    );
  }

}
