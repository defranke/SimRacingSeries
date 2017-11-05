import {Component} from "@angular/core";
import {BaseModal} from "./BaseModal";
import {BsModalRef} from "ngx-bootstrap/modal";
import {ErrorService} from "../services/error.service";
import {TeamDO} from "../model/TeamDO";
import {TeamService} from "../services/team.service";

@Component({
  selector: 'modal-content',
  templateUrl: './teamModal.component.html'
})
export class TeamModalComponent extends BaseModal {
  public currentTeam: TeamDO;
  public tmpTeam: TeamDO;
  public resultingTeam: TeamDO;

  title = 'Team bearbeiten';
  submitText = 'Speichern';
  formValidationMsg = '';

  constructor(public bsModalRef: BsModalRef, private teamService: TeamService,
              private errorRenderer: ErrorService) {
    super(bsModalRef);
  }


  public showFor(team: TeamDO): void {
    super.show();
    this.title = 'Team bearbeiten';
    this.submitText = 'Speichern';

    this.currentTeam = team;
    this.tmpTeam = new TeamDO();
    this.tmpTeam.name = this.currentTeam.name;
    this.tmpTeam.color = this.currentTeam.color;
  }

  public showForNewTeam(seriesId: string): void {
    super.show();
    this.title = 'Team erstellen';
    this.submitText = 'Erstellen';

    this.tmpTeam = new TeamDO();

    this.currentTeam = new TeamDO();
    this.currentTeam.seriesId = seriesId;
  }

  protected submit(): void {
    this.formValidationMsg = '';

    this.currentTeam.name = this.tmpTeam.name;
    this.currentTeam.color = this.tmpTeam.color;
    if(!this.currentTeam.id) {
      this.createSeries();
    }else{
      this.saveSeries();
    }
  }

  private saveSeries(): void {
    this.teamService.postTeam(this.currentTeam).subscribe(
      data => {
        this.resultingTeam = data;
        super.submit()
      },
      err => {
        this.formValidationMsg = this.errorRenderer.getFromError(err);
      }
    );
  }

  private createSeries(): void {
    this.teamService.putTeam(this.currentTeam).subscribe(
      data => {
        this.resultingTeam = data;
        super.submit()
      },
      err => {
        this.formValidationMsg = this.errorRenderer.getFromError(err);
      }
    );
  }
}

