import {Component} from "@angular/core";
import {BaseModal} from "../BaseModal";
import {BsModalRef} from "ngx-bootstrap/modal";
import {ErrorService} from "../../services/error.service";
import {TeamDO} from "../../model/TeamDO";
import {DriverDO} from "../../model/DriverDO";
import {DriverService} from "../../services/driver.service";

@Component({
  selector: 'modal-content',
  templateUrl: './driverModal.component.html'
})
export class DriverModalComponent extends BaseModal {
  public currentDriver: DriverDO;
  public tmpDriver: DriverDO;
  public resultingDriver: DriverDO;

  private teams: TeamDO[];

  title = 'Fahrer bearbeiten';
  submitText = 'Speichern';
  formValidationMsg = '';

  constructor(public bsModalRef: BsModalRef, private driverService: DriverService,
              private errorRenderer: ErrorService) {
    super(bsModalRef);
  }


  public showFor(driver: DriverDO, teams: TeamDO[]): void {
    super.show();
    this.title = 'Fahrer bearbeiten';
    this.submitText = 'Speichern';
    this.teams = teams;

    this.currentDriver = driver;
    this.tmpDriver = new DriverDO();
    this.tmpDriver.name = this.currentDriver.name;
    this.tmpDriver.car = this.currentDriver.car;
    this.tmpDriver.number = this.currentDriver.number;
    this.tmpDriver.gamertag = this.currentDriver.gamertag;
    this.tmpDriver.teamId = this.currentDriver.teamId;
  }

  public showForNewTeam(seriesId: string, teams: TeamDO[]): void {
    super.show();
    this.title = 'Fahrer erstellen';
    this.submitText = 'Erstellen';
    this.teams = teams;

    this.tmpDriver = new DriverDO();

    this.currentDriver = new DriverDO();
    this.currentDriver.seriesId = seriesId;
  }

  protected submit(): void {
    this.formValidationMsg = '';

    this.currentDriver.teamId = this.tmpDriver.teamId;
    this.currentDriver.name = this.tmpDriver.name;
    this.currentDriver.car = this.tmpDriver.car;
    this.currentDriver.number = this.tmpDriver.number;
    this.currentDriver.gamertag = this.tmpDriver.gamertag;

    if(!this.currentDriver.id) {
      this.createDriver();
    }else{
      this.saveDriver();
    }
  }

  private saveDriver(): void {
    this.driverService.postDriver(this.currentDriver).subscribe(
      data => {
        this.resultingDriver = data;
        super.submit()
      },
      err => {
        this.formValidationMsg = this.errorRenderer.getFromError(err);
      }
    );
  }

  private createDriver(): void {
    this.driverService.putDriver(this.currentDriver).subscribe(
      data => {
        this.resultingDriver = data;
        super.submit()
      },
      err => {
        this.formValidationMsg = this.errorRenderer.getFromError(err);
      }
    );
  }
}

