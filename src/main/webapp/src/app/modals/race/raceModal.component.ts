import {Component} from "@angular/core";
import {BaseModal} from "../BaseModal";
import {BsModalRef} from "ngx-bootstrap/modal";
import {ErrorService} from "../../services/error.service";
import {RaceDO} from "../../model/RaceDO";
import {RaceService} from "../../services/race.service";
import {BsDatepickerConfig, de, defineLocale} from "ngx-bootstrap";

@Component({
  selector: 'modal-content',
  templateUrl: './raceModal.component.html'
})
export class RaceModalComponent extends BaseModal {
  public currentRace: RaceDO;
  public tmpRace: RaceDO;
  public resultingRace: RaceDO;

  bsConfig: Partial<BsDatepickerConfig>;
  selectedDate: Date;

  title = 'Rennen bearbeiten';
  submitText = 'Speichern';
  formValidationMsg = '';

  constructor(public bsModalRef: BsModalRef, private raceService: RaceService,
              private errorRenderer: ErrorService) {
    super(bsModalRef);
    defineLocale('de', de);
    this.bsConfig = Object.assign({}, {containerClass: "theme-dark-blue", locale: "de"});
  }


  public showFor(race: RaceDO): void {
    super.show();
    this.title = 'Rennen bearbeiten';
    this.submitText = 'Speichern';

    this.currentRace = race;
    this.tmpRace = new RaceDO();
    this.tmpRace.track = this.currentRace.track;
    this.selectedDate = new Date(this.currentRace.timestamp);
  }

  public showForNewRace(seriesId: string): void {
    super.show();
    this.title = 'Rennen erstellen';
    this.submitText = 'Erstellen';

    this.tmpRace = new RaceDO();

    this.currentRace = new RaceDO();
    this.currentRace.seriesId = seriesId;
    this.selectedDate = new Date()
  }

  protected submit(): void {
    this.formValidationMsg = '';

    this.currentRace.track = this.tmpRace.track;
    this.currentRace.timestamp = this.selectedDate.getTime();
    if (!this.currentRace.id) {
      this.createRace();
    } else {
      this.saveRace();
    }
  }

  private saveRace(): void {
    this.raceService.postRace(this.currentRace).subscribe(
      data => {
        this.resultingRace = data;
        super.submit()
      },
      err => {
        this.formValidationMsg = this.errorRenderer.getFromError(err);
      }
    );
  }

  private createRace(): void {
    this.raceService.putRace(this.currentRace).subscribe(
      data => {
        this.resultingRace = data;
        super.submit()
      },
      err => {
        this.formValidationMsg = this.errorRenderer.getFromError(err);
      }
    );
  }
}

