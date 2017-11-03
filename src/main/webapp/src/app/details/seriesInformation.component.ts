import {Component, Input} from "@angular/core";
import {ActivatedRoute} from "@angular/router";

import {SeriesService} from "../services/series.service";
import {SeriesDO} from "../model/SeriesDO";

import {BsModalService} from "ngx-bootstrap/modal";
import {BsModalRef} from "ngx-bootstrap/modal/modal-options.class";

import "rxjs/add/operator/map";
import "rxjs/add/operator/switchMap";
import {PasswordHashService} from "../services/passwordHash.service";
import {ReglementModalComponent} from "../modals/reglementModal.component";
import {PointsModalComponent} from "../modals/pointsModal.component";

@Component({
  selector: 'series-information',
  templateUrl: './seriesInformation.component.html'
})


export class SeriesInformationComponent {
  bsModalRef: BsModalRef;

  @Input() series: SeriesDO
  @Input() editing: boolean = false;

  visibleInfoTab: string = "driverStanding";

  constructor(private route: ActivatedRoute, private seriesService: SeriesService,
              private modalService: BsModalService, private passwordHashService: PasswordHashService) {

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
