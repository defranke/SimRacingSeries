import {Component} from "@angular/core";
import {BaseModal} from "../BaseModal";
import {BsModalRef} from "ngx-bootstrap";
import {EventDO, EVENTTYPES} from "../../model/EventDO";
import {EventService} from "../../services/event.service";
import {ErrorService} from "../../services/error.service";

@Component({
  selector: 'modal-content',
  templateUrl: './eventModal.component.html'
})

export class EventModalComponent extends BaseModal {
  private title: string = '';
  private submitText: string = '';
  private formValidationMsg: string = '';
  private possibleTypes: Object[];

  private tmpEvent: EventDO;
  private originalEvent: EventDO;
  private resultingEvent: EventDO;


  constructor(public bsModalRef: BsModalRef, private errorRenderer: ErrorService, private eventService: EventService) {
    super(bsModalRef);
    this.possibleTypes = EVENTTYPES;
  }

  public showFor(event: EventDO): void {
    super.show();
    this.title = 'Veranstaltung bearbeiten';
    this.submitText = 'Speichern';

    this.originalEvent = event;
    this.tmpEvent = {...this.originalEvent};
  }

  public showForNewEvent(seriesId: string, raceId: string): void {
    super.show();
    this.title = 'Veranstaltung erstellen';
    this.submitText = 'Erstellen';

    this.originalEvent = new EventDO();
    this.originalEvent.seriesId = seriesId;
    this.originalEvent.raceId = raceId;

    this.tmpEvent = {...this.originalEvent};
  }

  protected submit(): void {
    this.formValidationMsg = '';

    Object.assign(this.originalEvent, this.tmpEvent);

    if (!this.originalEvent.id) {
      this.createEvent();
    } else {
      this.saveEvent();
    }
  }

  private saveEvent(): void {
    this.eventService.postEvent(this.originalEvent).subscribe(
      data => {
        this.resultingEvent = data;
        super.submit();
      },
      err => {
        this.formValidationMsg = this.errorRenderer.getFromError(err);
      }
    );
  }

  private createEvent(): void {
    this.eventService.putEvent(this.originalEvent).subscribe(
      data => {
        this.resultingEvent = data;
        super.submit()
      },
      err => {
        this.formValidationMsg = this.errorRenderer.getFromError(err);
      }
    );
  }
}
