import {Component, EventEmitter, Input, Output} from "@angular/core";
import {SeriesDO} from "../../model/SeriesDO";

import {BsModalService} from "ngx-bootstrap/modal";
import {BsModalRef} from "ngx-bootstrap/modal/bs-modal-ref.service";

import "rxjs/add/operator/map";
import "rxjs/add/operator/switchMap";
import {ErrorService} from "../../services/error.service";
import {RaceDO} from "../../model/RaceDO";
import {RaceModalComponent} from "../../modals/raceModal.component";
import {RaceService} from "../../services/race.service";

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

  @Output() onSelectedRace = new EventEmitter<RaceDO>();


  constructor(private modalService: BsModalService, private errorRenderer: ErrorService,
              private raceService: RaceService) {

  }

  public selectRace(race: RaceDO): void {
    this.onSelectedRace.emit(race)
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


  public editRace(race: RaceDO): void {
    if (!this.editing) {
      return;
    }
    this.bsModalRef = this.modalService.show(RaceModalComponent, {class: 'modal-lg'});
    this.bsModalRef.content.showFor(race);
    this.bsModalRef.content.submitted.subscribe(res => {
      if (res) {
        this.races.sort((a, b) => a.timestamp - b.timestamp);
      }
    });
  }


  public deleteRace(race: RaceDO): void {
    if (!this.editing) {
      return;
    }
    let reallyDelete = confirm('Soll das Rennen wirklich gelöscht werden?');
    if (!reallyDelete) {
      return;
    }
    this.raceService.deleteRace(race).subscribe(
      _ => {
        let i = this.races.indexOf(race);
        this.races.splice(i, 1);
      },
      err => {
        alert(this.errorRenderer.getFromError(err));
      }
    );
  }


}
