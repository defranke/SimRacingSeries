import {Component, EventEmitter, Input, Output} from "@angular/core";

import {BsModalService} from "ngx-bootstrap/modal";
import {BsModalRef} from "ngx-bootstrap/modal/bs-modal-ref.service";

import "rxjs/add/operator/map";
import "rxjs/add/operator/switchMap";
import {ErrorService} from "../../services/error.service";
import {RaceDO} from "../../model/RaceDO";
import {RaceModalComponent} from "../../modals/race/raceModal.component";
import {RaceService} from "../../services/race.service";
import {BehaviorSubject} from "rxjs/BehaviorSubject";
import {CompleteSeriesTO} from "../../model/CompleteSeriesTO";

@Component({
  selector: 'race-list',
  templateUrl: './raceList.component.html'
})

export class RaceListComponent {
  bsModalRef: BsModalRef;

  @Input() data: CompleteSeriesTO;
  @Input() selectedRace: BehaviorSubject<RaceDO>;
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
    this.bsModalRef.content.showForNewRace(this.data.series.id);
    this.bsModalRef.content.submitted.subscribe(res => {
      if (res) {
        this.data.addRace(this.bsModalRef.content.resultingRace);
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
        this.data.sortRaces();
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
        this.data.deleteRace(race);
      },
      err => {
        alert(this.errorRenderer.getFromError(err));
      }
    );
  }


}
