import {Component, EventEmitter, Input, OnInit, Output} from "@angular/core";
import {CompleteSeriesTO} from "../../model/CompleteSeriesTO";
import {EventDO} from "../../model/EventDO";
import {ResultDO} from "../../model/ResultDO";
import {DriverDO} from "../../model/DriverDO";
import {BehaviorSubject} from "rxjs/BehaviorSubject";

@Component({
  selector: 'event-race',
  templateUrl: './eventRace.component.html',
  styleUrls: ['./eventRace.component.css']
})

export class EventRaceComponent implements OnInit{

  @Input() data: CompleteSeriesTO;
  @Input() editing: boolean;
  @Input() event: BehaviorSubject<EventDO>;

  @Output() onSaveEvent = new EventEmitter<EventDO>();

  driverResult: {};

  constructor() {
  }

  ngOnInit() {
    this.event.subscribe(
      _ => {
        this.driverResult = {};
      }
    );
  }

  private getResultForDriver(driverId: string): ResultDO {
    if(!this.driverResult[driverId]) {
      if (!this.event || !this.event.getValue().results) {
        this.driverResult[driverId] = new ResultDO();
      }else{
        const res = this.event.getValue().results.filter(r => r.driverId === driverId);
        if (res.length >= 1) {
          this.driverResult[driverId] = res[0];
        }else{
          this.driverResult[driverId] = new ResultDO();
        }
      }
    }
    return this.driverResult[driverId];



    /*
    const res = this.event.getValue().results.filter(r => r.driverId === driverId);
    if (res.length >= 1) {
      return res[0];
    }
    return new ResultDO();*/
  }



  private getPositionDiff(result: ResultDO): string {
    if (result.gridPosition !== 0 && result.gridPosition !== result.finishPosition) {
      const diff = result.gridPosition - result.finishPosition;
      return String((diff < 0 ? "" : "+") + diff);
    }
    return undefined;
  }

  private getPositionDiffColor(result: ResultDO): string {
    if (result.gridPosition !== 0 && result.gridPosition !== result.finishPosition) {
      const diff = result.gridPosition - result.finishPosition;
      return diff < 0 ? "red" : "green";
    }
    return undefined;
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

  private handleTotalTimeChanged(newValue, driverId) {
    // Parse time
    // HH:MM:SS.sss
    // (([0-9]{1,2}:)*([0-9]{1,2}:)*
    alert(newValue);
    alert(driverId);
  }

  private handleFastestTimeChanged() {

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
