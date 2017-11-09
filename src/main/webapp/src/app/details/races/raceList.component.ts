import {Component, Input} from "@angular/core";
import {SeriesDO} from "../../model/SeriesDO";

import {BsModalService} from "ngx-bootstrap/modal";
import {BsModalRef} from "ngx-bootstrap/modal/bs-modal-ref.service";

import "rxjs/add/operator/map";
import "rxjs/add/operator/switchMap";
import {ErrorService} from "../../services/error.service";
import {RaceDO} from "../../model/RaceDO";
import {RaceModalComponent} from "../../modals/raceModal.component";

@Component({
  selector: 'race-list',
  templateUrl: './raceList.component.html'
})

export class RaceListComponent {
  bsModalRef: BsModalRef;

  @Input() series: SeriesDO;
  @Input() races: RaceDO[]
  @Input() selectedRace: RaceDO;
  @Input() editing: boolean = false;


  constructor(private modalService: BsModalService, private errorRenderer: ErrorService) {

  }

  public createNewRace(): void {
    if (!this.editing) {
      return;
    }
    this.bsModalRef = this.modalService.show(RaceModalComponent, {class: 'modal-lg'});
    this.bsModalRef.content.showForNewRace(this.series.id);
    this.bsModalRef.content.submitted.subscribe(res => {
      if (res) {
        this.races.push(this.bsModalRef.content.resultingRace);
        this.races.sort((a, b) => a.timestamp - b.timestamp);
      }
    });
  }

  /*
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
   */

}
