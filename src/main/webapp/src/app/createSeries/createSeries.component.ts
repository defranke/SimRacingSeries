import {Component} from "@angular/core";

import {SeriesDO} from "../model/SeriesDO";
import {SeriesService} from "../services/series.service";
import {Router} from "@angular/router";
import {HttpErrorResponse} from "@angular/common/http";
import {PasswordHashService} from "../services/passwordHash.service";
import {ErrorService} from "../services/error.service";
import {Title} from "@angular/platform-browser";

@Component({
  selector: 'createSeries',
  templateUrl: './createSeries.component.html',
  styleUrls: ['./createSeries.component.css']
})


export class CreateSeriesComponent {
  formValidationMsg = '';
  series = new SeriesDO();

  constructor(private router: Router, private seriesService: SeriesService, private passwordHashService: PasswordHashService,
              private errorRenderer: ErrorService, private titleService: Title) {
    this.series.isPublic = true;
    this.titleService.setTitle("SimRacingSeries");
  }

  private createSeries(): void {
    this.formValidationMsg = '';
    this.seriesService.putSeries(this.series).subscribe(
      data => {
        this.formValidationMsg = '';
        this.router.navigate(["/s", data.slugName]);
      },
      (err: HttpErrorResponse) => {
        this.formValidationMsg = this.errorRenderer.getFromError(err);
      }
    );
  }

  private updateSlugNameBased(newName: string) {
    this.series.slugName = this.series.name.replace(/[^a-zA-Z0-9_-]/g, "_");
  }

  private setMd5Password(newPassword: string) {
    this.series.password = this.passwordHashService.hashPassword(newPassword);
  }
}
