import {Component, OnInit} from "@angular/core";
import {ActivatedRoute} from "@angular/router";

import {SeriesService} from "../services/series.service";
import {SeriesDO} from "../model/SeriesDO";

import {BsModalService} from "ngx-bootstrap/modal";
import {BsModalRef} from "ngx-bootstrap/modal/modal-options.class";

import "rxjs/add/operator/map";
import "rxjs/add/operator/switchMap";
import {PasswordModalComponent} from "../modals/passwordModal.component";
import {PasswordHashService} from "../services/passwordHash.service";
import {SeriesModalComponent} from "../modals/seriesModal.component";
import {ReglementModalComponent} from "../modals/reglementModal.component";
import {PointsModalComponent} from "../modals/pointsModal.component";

@Component({
  selector: 'seriesDetails',
  templateUrl: './details.component.html',
  styleUrls: ['./details.component.css']
})


export class DetailsComponent implements OnInit {
  series: SeriesDO
  editing: boolean = false;

  bsModalRef: BsModalRef;

  constructor(private route: ActivatedRoute, private seriesService: SeriesService,
              private modalService: BsModalService, private passwordHashService: PasswordHashService) {

  }

  ngOnInit() {
    this.route.paramMap
      .map(params => params.get("slugName"))
      .switchMap(slugName => this.seriesService.findSeries(slugName))
      .subscribe(
        data => {
          this.editing = false;
          this.series = data
        },
        _ => {
          this.editing = false;
          this.series = null;
        });
  }

  private startEditing(): void {
    this.checkIsEditable()
      .then(_ => {
        this.editing = true;
      })
      .catch(err => {
        if(err !== "CANCEL") {
          alert('Falsches Passwort eingegeben.');
        }
      });
  }

  private editSeries(): void {
    this.bsModalRef = this.modalService.show(SeriesModalComponent, {class: 'modal-lg'});
    this.bsModalRef.content.showFor(this.series);
    this.bsModalRef.content.submitted.subscribe(res => {
      if(res) {
        // alert('Ok');
      }
    });
  }

  private checkIsEditable(): Promise<String> {
    return new Promise((resolve, reject) => {
      if (this.editing) {
        resolve();
      } else {
        this.bsModalRef = this.modalService.show(PasswordModalComponent);
        this.bsModalRef.content.show();
        this.bsModalRef.content.submitted.subscribe(res => {
          if(!res) {
            reject("CANCEL");
          }else if (this.isCorrectPassword(this.bsModalRef.content.password)) {
            resolve();
          } else {
            reject();
          }
        });
      }
    });
  }

  private isCorrectPassword(password: string): boolean {
    return this.passwordHashService.hashPassword(password) === this.series.password;
  }
}
