import {Component} from "@angular/core";

import {SeriesDO} from "../model/SeriesDO";
import {SeriesService} from "../services/series.service";
import {Router} from "@angular/router";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'createSeries',
  templateUrl: './createSeries.component.html',
  styleUrls: ['./createSeries.component.css']
})


export class CreateSeriesComponent {
  formValidationMsg = '';
  series = new SeriesDO();

  constructor(private router: Router, private seriesService: SeriesService) {

  }

  private createSeries(): void {
    if (this.isEmpty(this.series.name)) {
      this.formValidationMsg = 'Es muss ein Name f&uuml;r die Rennserie angegeben werden.';
    } else if (this.isEmpty(this.series.slugName) || !this.series.slugName.match("^[a-zA-Z0-9_-]+$")) {
      this.formValidationMsg = 'Die Kennung darf nur aus Buchstaben, Zahlen und folgenden Sonderzeichen bestehen: - und _';
    } else if (this.isEmpty(this.series.password)) {
      this.formValidationMsg = 'Es muss ein Passwort zum Bearbeiten der Rennserie festgelegt werden.';
    } else {
      this.formValidationMsg = '';
      this.seriesService.putSeries(this.series).subscribe(
        data => {
          this.formValidationMsg = '';
          this.router.navigate(["/s", data.slugName]);
        },
        (err: HttpErrorResponse) => {
          this.formValidationMsg = err.error.message;
        }
      );
    }
  }

  private isEmpty(str: string): boolean {
    return typeof str == 'undefined' || !str || str.length == 0;
  }
}
