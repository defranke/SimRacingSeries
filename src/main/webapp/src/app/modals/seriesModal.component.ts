
import {Component} from "@angular/core";
import {BaseModal} from "./BaseModal";
import {BsModalRef} from "ngx-bootstrap/modal";
import {SeriesDO} from "../model/SeriesDO";
import {PasswordHashService} from "../services/passwordHash.service";
import {SeriesService} from "../services/series.service";
import {Router} from "@angular/router";

@Component({
  selector: 'modal-content',
  templateUrl: './seriesModal.component.html'
})
export class SeriesModalComponent extends BaseModal {
  public currentSeries: SeriesDO;
  public series: SeriesDO;

  constructor(private router: Router, public bsModalRef: BsModalRef, private seriesService: SeriesService, private passwordHashService: PasswordHashService) {
    super(bsModalRef);
  }


  public showFor(series: SeriesDO): void {
    super.show();

    this.currentSeries = series;
    this.series = new SeriesDO();
    this.series.name = this.currentSeries.name;
    this.series.slugName = this.currentSeries.slugName;
    this.series.password = "";
    this.series.isPublic = this.currentSeries.isPublic;
    this.series.description = this.currentSeries.description;
  }


  protected submit(): void {
    let slugChanged = this.currentSeries.slugName !== this.series.slugName;

    this.currentSeries.name = this.series.name;
    this.currentSeries.slugName = this.series.slugName;
    if(this.series.password) {
      this.currentSeries.password = this.series.password;
    }
    this.currentSeries.isPublic = this.series.isPublic;
    this.currentSeries.description = this.series.description;

    this.seriesService.postSeries(this.currentSeries).subscribe(
      data => {
        super.submit();
        if(slugChanged) {
          this.router.navigate(["/s", data.slugName]);
        }
      },
      err => {
        alert('Failed to save');
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

