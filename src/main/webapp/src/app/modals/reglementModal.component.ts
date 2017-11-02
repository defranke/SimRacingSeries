
import {Component} from "@angular/core";
import {BaseModal} from "./BaseModal";
import {BsModalRef} from "ngx-bootstrap/modal";
import {SeriesDO} from "../model/SeriesDO";
import {SeriesService} from "../services/series.service";

@Component({
  selector: 'modal-content',
  templateUrl: './reglementModal.component.html'
})
export class ReglementModalComponent extends BaseModal {
  public currentSeries: SeriesDO;
  public series: SeriesDO;

  constructor(public bsModalRef: BsModalRef, private seriesService: SeriesService) {
    super(bsModalRef);
  }


  public showFor(series: SeriesDO): void {
    super.show()
    this.currentSeries = series;
    this.series = new SeriesDO();
    this.series.reglement = this.currentSeries.reglement;
  }

  protected submit(): void {
    this.currentSeries.reglement = this.series.reglement;

    this.seriesService.postSeries(this.currentSeries).subscribe(
      _ => {
        super.submit();
      },
      _ => {
        alert('Failed to save');
      }
    );
  }
}

