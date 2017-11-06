import {Component, OnInit} from "@angular/core";
import {ActivatedRoute} from "@angular/router";

import {BsModalService} from "ngx-bootstrap/modal";
import {BsModalRef} from "ngx-bootstrap/modal/modal-options.class";

import "rxjs/add/operator/map";
import "rxjs/add/operator/switchMap";
import {PasswordModalComponent} from "../modals/passwordModal.component";
import {PasswordHashService} from "../services/passwordHash.service";
import {SeriesModalComponent} from "../modals/seriesModal.component";
import {CompleteSeriesTO} from "../model/CompleteSeriesTO";
import {GeneralService} from "../services/general.service";

@Component({
  selector: 'seriesDetails',
  templateUrl: './details.component.html',
  styleUrls: ['./details.component.css']
})


export class DetailsComponent implements OnInit {
  data: CompleteSeriesTO;
  editing: boolean = false;

  bsModalRef: BsModalRef;

  constructor(private route: ActivatedRoute, private generalService: GeneralService,
              private modalService: BsModalService, private passwordHashService: PasswordHashService) {

  }

  ngOnInit() {
    this.route.paramMap
      .map(params => params.get("slugName"))
      .switchMap(slugName => this.generalService.getCompleteSeries(slugName))
      .subscribe(
        data => {
          this.editing = false;
          this.data = data
          this.data.teams.sort((a, b) => a.name.localeCompare(b.name));
          this.data.drivers.sort((a, b) => a.name.localeCompare(b.name));
        },
        _ => {
          this.editing = false;
          this.data = null;
        });
  }

  private startEditing(): void {
    if (!this.editing) {
      this.bsModalRef = this.modalService.show(PasswordModalComponent);
      this.bsModalRef.content.show();
      this.bsModalRef.content.submitted.subscribe(res => {
        if (res && this.isCorrectPassword(this.bsModalRef.content.password)) {
          this.editing = true;
        }else if(res) {
          alert('Falsches Passwort eingegeben.');
        }
      });
    }
  }

  private isCorrectPassword(password: string): boolean {
    return this.passwordHashService.hashPassword(password) === this.data.series.password;
  }

  private stopEditing(): void {
    this.editing = false;
  }

  private editSeries(): void {
    if(!this.editing) {
      return;
    }
    this.bsModalRef = this.modalService.show(SeriesModalComponent, {class: 'modal-lg'});
    this.bsModalRef.content.showFor(this.data.series);
    this.bsModalRef.content.submitted.subscribe(res => {
      if (res) {
        // alert('Ok');
      }
    });
  }
}
