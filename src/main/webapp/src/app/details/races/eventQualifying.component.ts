import {Component, EventEmitter, Input, OnInit, Output} from "@angular/core";
import {CompleteSeriesTO} from "../../model/CompleteSeriesTO";
import {BehaviorSubject} from "rxjs/BehaviorSubject";
import {EventDO} from "../../model/EventDO";
import {ResultDO} from "../../model/ResultDO";
import {DurationService} from "../../services/duration.service";

@Component({
  selector: 'event-qualifying',
  templateUrl: './eventQualifying.component.html',
  styleUrls: ['./eventQualifying.component.css']
})


export class EventQualifyingComponent implements OnInit {

  @Input() data: CompleteSeriesTO;
  @Input() editing: boolean;
  @Input() event: BehaviorSubject<EventDO>;

  @Output() onSaveEvent = new EventEmitter<EventDO>();

  results = {}
  fastestTimes = {}

  constructor(private durationService: DurationService) {

  }

  ngOnInit(): void {
    this.event.subscribe(() => this.reset())
  }

  reset() {
    if(this.event.getValue() && this.event.getValue().results) {
      this.event.getValue().results.sort((a, b) => a.finishPosition - b.finishPosition);
    }
    this.results = {}
    this.fastestTimes = {}
  }

  getResult(driverId: string): ResultDO {
    if(this.results[driverId] === undefined) {
      this.results[driverId] = new ResultDO();
      if(this.event.getValue().results) {
        const res = this.event.getValue().results.find(r => r.driverId === driverId)
        if(res !== undefined) {
          this.results[driverId] = {...res};
        }
      }
    }
    return this.results[driverId];
  }

  private getTmpFastestTime(driverId: string): number {
    if(this.fastestTimes[driverId] === undefined) {
      this.fastestTimes[driverId] = this.durationService.format(this.getResult(driverId).fastestTime);
    }
    return this.fastestTimes[driverId];
  }

  getFastestTimeDiff(res: ResultDO): number {
    if(this.isFastestLap(res)) {
      return 0;
    }
    return res.fastestTime - this.getGlobalFastestTime()
  }

  isFastestLap(res: ResultDO) {
    if (!this.event.getValue().results) {
      return false;
    }
    return res.fastestTime === this.getGlobalFastestTime();
  }

  private getGlobalFastestTime(): number {
    return this.event.getValue().results
      .map(r => r.fastestTime)
      .filter(t => t > 0)
      .reduce((acc, val) => Math.min(acc, val), Number.MAX_VALUE)
  }

  getPointsFor(res: ResultDO): number {
    let points = 0;
    if(this.data.series.pointsForQualifying > 0 && this.isFastestLap(res)) {
      points += this.data.series.pointsForQualifying;
    }
    return points;
  }

  hasIgnoreForStandingResult() {
    if (!this.event.getValue().results) {
      return false;
    }
    return this.event.getValue().results.filter(r => r.ignoreForStanding).length > 0;
  }

  private handleFastestTimeChanged(newValue, driverId) {
    this.getResult(driverId).fastestTime = this.durationService.parse(newValue);
  }

  saveEvent() {
    this.event.getValue().results = []
    for (let driverId in this.results) {
      let result = this.results[driverId];
      result.driverId = driverId;
      this.event.getValue().results.push(result);
    }

    this.onSaveEvent.emit(this.event.getValue());
  }


}
