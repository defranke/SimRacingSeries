import {Component} from "@angular/core";
import {BaseModal} from "../BaseModal";
import {BsModalRef} from "ngx-bootstrap/modal";
import {ErrorService} from "../../services/error.service";
import {RaceDO} from "../../model/RaceDO";
import {RaceService} from "../../services/race.service";

@Component({
  selector: 'modal-content',
  templateUrl: './raceDescriptionModal.component.html'
})
export class RaceDescriptionModalComponent extends BaseModal {
  public currentRace: RaceDO;
  public tmpRace: RaceDO;

  formValidationMsg = '';

  constructor(public bsModalRef: BsModalRef, private raceService: RaceService, private errorRenderer: ErrorService) {
    super(bsModalRef);
  }

  public showFor(race: RaceDO): void {
    super.show()
    this.currentRace = race;
    this.tmpRace = {...this.currentRace};
  }

  protected submit(): void {
    Object.assign(this.currentRace, this.tmpRace);

    this.raceService.postRace(this.currentRace).subscribe(
      _ => {
        super.submit();
      },
      err => {
        this.formValidationMsg = this.errorRenderer.getFromError(err);
      }
    );
  }
}

