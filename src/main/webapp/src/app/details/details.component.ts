import {Component, OnInit} from "@angular/core";
import {ActivatedRoute} from "@angular/router";

import {BsModalService} from "ngx-bootstrap/modal";
import {BsModalRef} from "ngx-bootstrap/modal/bs-modal-ref.service";

import "rxjs/add/operator/map";
import "rxjs/add/operator/switchMap";
import {PasswordModalComponent} from "../modals/passwordModal.component";
import {PasswordHashService} from "../services/passwordHash.service";
import {SeriesModalComponent} from "../modals/series/seriesModal.component";
import {CompleteSeriesTO} from "../model/CompleteSeriesTO";
import {GeneralService} from "../services/general.service";
import {AuthenticationService} from "../services/authentication.service";
import {Title} from "@angular/platform-browser";
import {RaceDO} from "../model/RaceDO";
import {BehaviorSubject} from "rxjs/BehaviorSubject";

@Component({
  selector: 'seriesDetails',
  templateUrl: './details.component.html',
  styleUrls: ['./details.component.css']
})


export class DetailsComponent implements OnInit {
  data: CompleteSeriesTO;
  selectedRace = new BehaviorSubject<RaceDO>(undefined);
  editing: boolean = false;

  bsModalRef: BsModalRef;

  constructor(private route: ActivatedRoute, private generalService: GeneralService, private auth: AuthenticationService,
              private modalService: BsModalService, private passwordHashService: PasswordHashService,
              private titleService: Title) {

  }

  ngOnInit() {
    this.route.paramMap
      .map(params => params.get("slugName"))
      .switchMap(slugName => this.generalService.getCompleteSeries(slugName))
      .subscribe(
        data => {
          this.editing = false;
          this.data = data
          this.titleService.setTitle(this.data.series.name);

          this.data.sort();
        },
        _ => {
          this.editing = false;
          this.data = null;
        });
  }

  public onSelectedRace(race: RaceDO) {
    this.selectedRace.next(race);
  }

  private startEditing(): void {
    if (!this.editing) {
      this.bsModalRef = this.modalService.show(PasswordModalComponent);
      this.bsModalRef.content.show();
      this.bsModalRef.content.submitted.subscribe(res => {
        if (res && this.isCorrectPassword(this.bsModalRef.content.password)) {
          this.auth.setAuthentication(this.data.series.slugName, this.bsModalRef.content.password)
          this.editing = true;
        } else if (res) {
          alert('Falsches Passwort eingegeben.');
        }
      });
    }
  }

  private isCorrectPassword(password: string): boolean {
    return this.passwordHashService.hashPassword(password) === this.data.series.password;
  }

  private stopEditing(): void {
    this.auth.clearAuthentication();
    this.editing = false;
  }

  private editSeries(): void {
    if (!this.editing) {
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
