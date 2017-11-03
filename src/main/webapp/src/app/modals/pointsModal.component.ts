import {Component} from "@angular/core";
import {BaseModal} from "./BaseModal";
import {BsModalRef} from "ngx-bootstrap/modal";
import {SeriesDO} from "../model/SeriesDO";
import {SeriesService} from "../services/series.service";
import {ErrorService} from "../services/error.service";

@Component({
  selector: 'modal-content',
  templateUrl: './pointsModal.component.html'
})
export class PointsModalComponent extends BaseModal {
  public currentSeries: SeriesDO;
  public tmpSeries: SeriesDO;

  formValidationMsg = '';
  items = [1,2,3];

  constructor(public bsModalRef: BsModalRef, private seriesService: SeriesService, private errorRenderer: ErrorService) {
    super(bsModalRef);
  }


  public showFor(series: SeriesDO): void {
    super.show()
    this.currentSeries = series;
    this.tmpSeries = new SeriesDO();
    this.tmpSeries.pointsForFastestLap = this.currentSeries.pointsForFastestLap;
    this.tmpSeries.pointsForQualifying = this.currentSeries.pointsForQualifying;
    this.tmpSeries.points = this.currentSeries.points.slice();
  }

  protected submit(): void {
    this.currentSeries.pointsForFastestLap = this.tmpSeries.pointsForFastestLap;
    this.currentSeries.pointsForQualifying = this.tmpSeries.pointsForQualifying;
    this.currentSeries.points = this.tmpSeries.points.slice();

    this.seriesService.postSeries(this.currentSeries).subscribe(
      _ => {
        super.submit();
      },
      err => {
        this.formValidationMsg = this.errorRenderer.getFromError(err);
      }
    );
  }

  public addPoint(): void {
    if(!this.tmpSeries.points) {
      this.tmpSeries.points = [];
    }
    this.tmpSeries.points.push(0);
  }

  public removePointsAt(index: number): void {
    this.tmpSeries.points.splice(index, 1);
  }

  public trackByPoints(index: number, value: number): number {
    return index;
  }
}

