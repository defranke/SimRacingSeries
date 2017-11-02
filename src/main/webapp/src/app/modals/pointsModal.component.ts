
import {Component} from "@angular/core";
import {BaseModal} from "./BaseModal";
import {BsModalRef} from "ngx-bootstrap/modal";
import {SeriesDO} from "../model/SeriesDO";

@Component({
  selector: 'modal-content',
  templateUrl: './pointsModal.component.html'
})
export class PointsModalComponent extends BaseModal {
  public series: SeriesDO;

  constructor(public bsModalRef: BsModalRef) {
    super(bsModalRef);
  }


  public showFor(series: SeriesDO): void {
    super.show()
    // TODO: Copy values
    this.series = series;
  }

  protected submit(): void {
    // TODO: Save values
    super.submit();
  }
}

