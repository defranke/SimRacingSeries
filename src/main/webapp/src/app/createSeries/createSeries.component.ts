import {Component} from "@angular/core";
import {Http} from "@angular/http";

import {SeriesDO} from "../model/SeriesDO";
import {SeriesService} from "../services/series.service";

@Component({
  selector: 'createSeries',
  templateUrl: './createSeries.component.html',
  styleUrls: ['./createSeries.component.css']
})


export class CreateSeriesComponent {
  formValidationMsg = '';
  series = new SeriesDO();

  constructor(private http: Http, private seriesService: SeriesService) {

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
      this.seriesService.putSeries(this.series)
        .then(response => alert(response))
        .catch(error => this.formValidationMsg = 'Erstellen fehlgeschlagen');
    }
  }

  private isEmpty(str: string): boolean {
    return typeof str == 'undefined' || !str || str.length == 0;
  }
}
