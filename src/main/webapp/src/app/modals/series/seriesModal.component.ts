import {Component} from "@angular/core";
import {BaseModal} from "../BaseModal";
import {BsModalRef} from "ngx-bootstrap/modal";
import {SeriesDO} from "../../model/SeriesDO";
import {PasswordHashService} from "../../services/passwordHash.service";
import {SeriesService} from "../../services/series.service";
import {Router} from "@angular/router";
import {ErrorService} from "../../services/error.service";

@Component({
  selector: 'modal-content',
  templateUrl: './seriesModal.component.html'
})
export class SeriesModalComponent extends BaseModal {
  public currentSeries: SeriesDO;
  public tmpSeries: SeriesDO;

  formValidationMsg = '';

  constructor(private router: Router, public bsModalRef: BsModalRef, private seriesService: SeriesService,
              private passwordHashService: PasswordHashService, private errorRenderer: ErrorService) {
    super(bsModalRef);
  }


  public showFor(series: SeriesDO): void {
    super.show();

    this.currentSeries = series;
    this.tmpSeries = new SeriesDO();
    this.tmpSeries.name = this.currentSeries.name;
    this.tmpSeries.slugName = this.currentSeries.slugName;
    this.tmpSeries.password = "";
    this.tmpSeries.isPublic = this.currentSeries.isPublic;
    this.tmpSeries.description = this.currentSeries.description;
  }


  protected submit(): void {
    this.formValidationMsg = '';
    this.saveSeries();
  }

  private saveSeries(): void {
    let slugChanged = this.currentSeries.slugName !== this.tmpSeries.slugName;

    this.currentSeries.name = this.tmpSeries.name;
    this.currentSeries.slugName = this.tmpSeries.slugName;
    if (this.tmpSeries.password) {
      this.currentSeries.password = this.tmpSeries.password;
    }
    this.currentSeries.isPublic = this.tmpSeries.isPublic;
    this.currentSeries.description = this.tmpSeries.description;

    this.seriesService.postSeries(this.currentSeries).subscribe(
      data => {
        super.submit();
        if (slugChanged) {
          this.router.navigate(["/s", data.slugName]);
        }
      },
      err => {
        this.formValidationMsg = this.errorRenderer.getFromError(err);
      }
    );
  }

  private updateSlugNameBased(newName: string) {
    this.tmpSeries.slugName = this.tmpSeries.name.replace(/[^a-zA-Z0-9_-]/g, "_");
  }

  private setMd5Password(newPassword: string) {
    this.tmpSeries.password = this.passwordHashService.hashPassword(newPassword);
  }
}

