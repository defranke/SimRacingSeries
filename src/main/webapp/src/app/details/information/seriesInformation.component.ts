import {Component, Input} from "@angular/core";
import {SeriesDO} from "../../model/SeriesDO";

import {BsModalService} from "ngx-bootstrap/modal";
import {BsModalRef} from "ngx-bootstrap/modal/bs-modal-ref.service";

import "rxjs/add/operator/map";
import "rxjs/add/operator/switchMap";
import {ReglementModalComponent} from "../../modals/series/reglementModal.component";
import {PointsModalComponent} from "../../modals/series/pointsModal.component";

@Component({
  selector: 'series-information',
  templateUrl: './seriesInformation.component.html'
})

export class SeriesInformationComponent {
  bsModalRef: BsModalRef;

  @Input() series: SeriesDO
  @Input() editing: boolean = false;

  visibleInfoTab: string = "driverStanding";

  constructor(private modalService: BsModalService) {

  }

  private editReglement(): void {
    if(!this.editing) {
      return;
    }
    this.bsModalRef = this.modalService.show(ReglementModalComponent, {class: 'modal-lg'});
    this.bsModalRef.content.showFor(this.series);
    this.bsModalRef.content.submitted.subscribe(res => {
      if(res) {
        // Done
      }
    });
  }

  private editPoints(): void {
    if(!this.editing) {
      return;
    }
    this.bsModalRef = this.modalService.show(PointsModalComponent, {class: 'modal-lg'});
    this.bsModalRef.content.showFor(this.series);
    this.bsModalRef.content.submitted.subscribe(res => {
      if(res) {
        // Done
      }
    });
  }
}
