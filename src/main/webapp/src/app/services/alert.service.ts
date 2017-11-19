import {Injectable} from "@angular/core";
import {Subject} from "rxjs/Subject";

@Injectable()
export class AlertService {

  alertSubject = new Subject<any>()

  constructor() {

  }

  public addAlert(msg: string, type: string, timeout: number) {
    this.alertSubject.next({
      type: type,
      msg: msg,
      timeout: timeout
    });
  }
}
