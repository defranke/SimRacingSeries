import {Component} from "@angular/core";
import {BaseModal} from "./BaseModal";
import {BsModalRef} from "ngx-bootstrap/modal";
import {SeriesDO} from "../model/SeriesDO";
import {SeriesService} from "../services/series.service";
import {ErrorService} from "../services/error.service";

@Component({
  selector: 'modal-content',
  templateUrl: './reglementModal.component.html'
})
export class ReglementModalComponent extends BaseModal {
  public currentSeries: SeriesDO;
  public tmpSeries: SeriesDO;

  formValidationMsg = '';

  constructor(public bsModalRef: BsModalRef, private seriesService: SeriesService, private errorRenderer: ErrorService) {
    super(bsModalRef);
  }

  public showFor(series: SeriesDO): void {
    super.show()
    this.currentSeries = series;
    this.tmpSeries = new SeriesDO();
    this.tmpSeries.reglement = this.currentSeries.reglement;
  }

  protected submit(): void {
    this.currentSeries.reglement = this.tmpSeries.reglement;

    this.seriesService.postSeries(this.currentSeries).subscribe(
      _ => {
        super.submit();
      },
      err => {
        this.formValidationMsg = this.errorRenderer.getFromError(err);
      }
    );
  }
}

