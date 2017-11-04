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

@Component({
  selector: 'teams',
  templateUrl: './teams.component.html'
})

export class TeamsComponent {
  bsModalRef: BsModalRef;

  @Input() series: SeriesDO;
  @Input() teams: TeamDO[]
  @Input() editing: boolean = false;

  constructor(private modalService: BsModalService, private teamService: TeamService,
              private errorRenderer: ErrorService) {

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
    this.teamService.deleteTeam(team.id).subscribe(
      _ => {
        let i = this.teams.indexOf(team);
        this.teams.splice(i, 1);
      },
      err => {
        alert(this.errorRenderer.getFromError(err));
      }
    );
  }

}
