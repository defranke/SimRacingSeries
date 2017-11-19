import {Injectable} from "@angular/core";

import "rxjs/add/operator/toPromise";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {AuthenticationService} from "./authentication.service";
import {EventDO} from "../model/EventDO";

@Injectable()
export class EventService {

  private apiUrl = '/api/event/';

  constructor(private http: HttpClient, private auth: AuthenticationService) {

  }

  getEvents(raceId: string): Observable<EventDO[]> {
    return this.http.get(this.apiUrl + raceId);
  }

  putEvent(event: EventDO): Observable<EventDO> {
    return this.http.put(this.apiUrl + event.seriesId,
      JSON.stringify(event),
      {headers: new HttpHeaders().append("Content-Type", "application/json")
        .append("Authorization", this.auth.getAuthenticationString())});
  }

  postEvent(event: EventDO): Observable<EventDO> {
    return this.http.post(this.apiUrl + event.seriesId,
      JSON.stringify(event),
      {headers: new HttpHeaders().append("Content-Type", "application/json")
        .append("Authorization", this.auth.getAuthenticationString())});
  }
}
