import {Component, OnInit} from "@angular/core";
import {ActivatedRoute} from "@angular/router";

import {SeriesService} from "../services/series.service";
import {SeriesDO} from "../model/SeriesDO";

import { BsModalService } from 'ngx-bootstrap/modal';
import { BsModalRef } from 'ngx-bootstrap/modal/modal-options.class';

import "rxjs/add/operator/map";
import "rxjs/add/operator/switchMap";
import {PasswordModalComponent} from "../modals/passwordModal.component";

@Component({
  selector: 'seriesDetails',
  templateUrl: './details.component.html',
  styleUrls: ['./details.component.css']
})


export class DetailsComponent implements OnInit {
  series: SeriesDO
  editing: boolean = false;

  bsModalRef: BsModalRef;

  constructor(private route: ActivatedRoute, private seriesService: SeriesService, private modalService: BsModalService) {

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

  private editSeries(): void {
    this.checkIsEditable()
      .then(_ => {
        this.editing = true;
      })
      .catch(_ => alert('Falsches Passwort eingegeben.'));
  }

  private checkIsEditable() : Promise<String> {
    return new Promise((resolve, reject) => {
      if(this.editing) {
        resolve();
      }else{
        this.bsModalRef = this.modalService.show(PasswordModalComponent);
        this.bsModalRef.content.show();
        this.bsModalRef.content.submitted.subscribe(res => {
          if(res && this.isCorrectPassword(this.bsModalRef.content.password)) {
            resolve();
          }else{
            reject();
          }
        });
      }
    });
  }

  private isCorrectPassword(password: string) : boolean {
    return password === this.series.password;
  }
}
