import {Component, Input, OnInit} from "@angular/core";
import {RaceDO} from "../../model/RaceDO";
import {BsModalService} from "ngx-bootstrap";
import {RaceDescriptionModalComponent} from "../../modals/race/raceDescriptionModal.component";
import {BehaviorSubject} from "rxjs/BehaviorSubject";
import {EventService} from "../../services/event.service";
import {EventDO} from "../../model/EventDO";
import {EventModalComponent} from "../../modals/race/eventModal.component";
import {AlertService} from "../../services/alert.service";
import {CompleteSeriesTO} from "../../model/CompleteSeriesTO";

@Component({
  selector: 'race',
  templateUrl: './race.component.html'
})

export class RaceComponent implements OnInit {

  @Input() data: CompleteSeriesTO;
  @Input() raceSubject: BehaviorSubject<RaceDO>;
  @Input() eventSubject = new BehaviorSubject<EventDO>(undefined);
  @Input() editing: boolean;

  private race: RaceDO;
  private events: EventDO[];


  constructor(private modalService: BsModalService, private eventService: EventService,
              private alertService: AlertService) {

  }

  ngOnInit() {
    this.raceSubject.subscribe(
      race => {
        this.race = race;
        this.loadEvents()
      }
    );
  }

  private loadEvents() {
    this.eventService.getEvents(this.race.id).subscribe(
      events => {
        this.events = events;
        if (events && events.length > 0) {
          this.selectEvent(events[0]);
        }
      },
      err => {
        alert("Error: " + err);
      }
    );
  }

  private selectEvent(event: EventDO) {
    if(event.results) {
      event.results.sort((a, b) => a.finishPosition - b.finishPosition);
    }
    this.eventSubject.next(event);
  }

  private saveEvent(event: EventDO) {
    this.eventService.postEvent(event).subscribe(
      res => this.replaceWithSavedEvent(res),
      err => {
        alert(err)
      }
    );
  }

  private replaceWithSavedEvent(savedEvent: EventDO) {
    this.selectEvent(savedEvent);

    this.events = this.events
      .map(e => {
        return e.id === savedEvent.id ? savedEvent : e;
      });
    this.alertService.addAlert("Das Ergebnis wurde erfolgreich gespeichert.", "success", 5000);
  }


  createNewEvent() {
    if (!this.editing) {
      return;
    }
    let bsModalRef = this.modalService.show(EventModalComponent, {class: 'modal-lg'});
    bsModalRef.content.showForNewEvent(this.race.seriesId, this.race.id);
    bsModalRef.content.submitted.subscribe(res => {
      if (res) {
        // Done
        this.events.push(bsModalRef.content.resultingEvent);
      }
    });
  }

  editEvent(event: EventDO) {
    if (!this.editing) {
      return;
    }
    let bsModalRef = this.modalService.show(EventModalComponent, {class: 'modal-lg'});
    bsModalRef.content.showFor(event);
    bsModalRef.content.submitted.subscribe(res => {
      if (res) {
        // Done
      }
    });
  }


  editRaceDescription() {
    if (!this.editing) {
      return;
    }
    let bsModalRef = this.modalService.show(RaceDescriptionModalComponent, {class: 'modal-lg'});
    bsModalRef.content.showFor(this.race);
    bsModalRef.content.submitted.subscribe(res => {
      if (res) {
        // Done
      }
    });
  }
}
