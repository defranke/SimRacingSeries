import {Component, EventEmitter, Input, OnInit, Output} from "@angular/core";
import {CompleteSeriesTO} from "../../model/CompleteSeriesTO";
import {EventDO} from "../../model/EventDO";
import {ResultDO} from "../../model/ResultDO";
import {DriverDO} from "../../model/DriverDO";
import {BehaviorSubject} from "rxjs/BehaviorSubject";
import {DurationService} from "../../services/duration.service";

@Component({
  selector: 'event-race',
  templateUrl: './eventRace.component.html',
  styleUrls: ['./eventRace.component.css']
})

export class EventRaceComponent implements OnInit {

  @Input() data: CompleteSeriesTO;
  @Input() editing: boolean;
  @Input() event: BehaviorSubject<EventDO>;

  @Output() onSaveEvent = new EventEmitter<EventDO>();

  driverResult: {};
  totalTimes: {};
  fastestTimes: {};

  constructor(private durationService: DurationService) {
  }

  ngOnInit() {
    this.event.subscribe(
      _ => {
        this.driverResult = {};
        this.totalTimes = {};
        this.fastestTimes = {};
      }
    );
  }

  private getResultForDriver(driverId: string): ResultDO {
    if (!this.driverResult[driverId]) {
      if (!this.event || !this.event.getValue().results) {
        this.driverResult[driverId] = new ResultDO();
      } else {
        const res = this.event.getValue().results.filter(r => r.driverId === driverId);
        if (res.length >= 1) {
          this.driverResult[driverId] = res[0];
        } else {
          this.driverResult[driverId] = new ResultDO();
        }
      }
    }
    if(this.totalTimes[driverId] === undefined) {
      this.totalTimes[driverId] = this.durationService.format(this.driverResult[driverId].totalTime);
    }
    if(this.fastestTimes[driverId] === undefined) {
      this.fastestTimes[driverId] = this.durationService.format(this.driverResult[driverId].fastestTime);
    }
    return this.driverResult[driverId];
  }


  private getPositionDiffString(result: ResultDO): string {
    const diff = this.getPositionDiff(result);
    return String((diff < 0 ? "" : "+") + diff);
  }

  private getPositionDiffColor(result: ResultDO): string {
    const diff = this.getPositionDiff(result);
    return diff < 0 ? "red" : "green";
  }

  private getPositionDiff(result: ResultDO): number {
    if (result.gridPosition !== 0 && result.gridPosition !== result.finishPosition) {
      const diff = result.gridPosition - result.finishPosition;
      return diff;
    }
    return 0;
  }

  private getTotalTimeDiff(result: ResultDO): number {
    const fastestTotalTime = this.event.getValue().results
      .map(r => r.totalTime)
      .filter(r => r !== 0)
      .reduce((a, c) => Math.min(a, c), Number.MAX_VALUE);
    if(result.totalTime === 0 || result.totalTime === fastestTotalTime) {
      return 0;
    }
    return result.totalTime - fastestTotalTime;
  }


  private getDriverNameForId(driverId: string): string {
    const foundDriver = this.findDriver(driverId);
    return foundDriver !== undefined ? foundDriver.name : "";
  }

  private getTeamColor(driverId: string): string {
    const foundDriver = this.findDriver(driverId);
    if (foundDriver !== undefined) {
      const foundTeam = this.data.teams.filter(t => t.id === foundDriver.teamId);
      if (foundTeam.length > 0) {
        return foundTeam[0].color;
      }
    }
    return "transparent";
  }

  private findDriver(driverId: string): DriverDO {
    const foundDriver = this.data.drivers.filter(d => d.id === driverId);
    if (foundDriver.length > 0) {
      return foundDriver[0];
    }
    return undefined;
  }

  private getPointsFor(result: ResultDO) {
    let points = 0;
    if (this.data.series.points && this.data.series.points[result.finishPosition - 1]) {
      points += this.data.series.points[result.finishPosition - 1];
    }
    if (this.data.series.pointsForFastestLap > 0 && this.isFastestLap(result)) {
      points += this.data.series.pointsForFastestLap
    }
    return points;
  }

  private isFastestLap(result: ResultDO): boolean {
    if (!this.event.getValue().results) {
      return false;
    }
    const globalFastestTime = this.event.getValue().results
      .filter(r => r.fastestTime > 0)
      .reduce((a, c) => Math.min(a, c.fastestTime), Number.MAX_VALUE);
    return globalFastestTime === result.fastestTime;
  }


  private handleTotalTimeChanged(newValue, driverId) {
    this.getResultForDriver(driverId).totalTime = this.durationService.parse(newValue);
  }

  private handleFastestTimeChanged(newValue, driverId) {
    const inMs = this.durationService.parse(newValue);
    this.getResultForDriver(driverId).fastestTime = inMs;
    console.log(this.getResultForDriver(driverId));
  }

  private saveEvent() {
    this.event.getValue().results = [];
    for (let driverId in this.driverResult) {
      let result = this.driverResult[driverId];
      result.driverId = driverId;
      this.event.getValue().results.push(result);
    }

    this.onSaveEvent.emit(this.event.getValue());
  }
}
