import {Injectable} from "@angular/core";

import "rxjs/add/operator/toPromise";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {AuthenticationService} from "./authentication.service";
import {RaceDO} from "../model/RaceDO";

@Injectable()
export class RaceService {
  private putUrl = 'api/race/';
  private postUrl = 'api/race/';
  private deleteUrl = 'api/race/';

  constructor(private http: HttpClient, private auth: AuthenticationService) {

  }

  putRace(race: RaceDO): Observable<RaceDO> {
    return this.http.put(this.putUrl + race.seriesId,
      JSON.stringify(race),
      {headers: new HttpHeaders().append("Content-Type", "application/json")
        .append("Authorization", this.auth.getAuthenticationString())});
  }

  postRace(race: RaceDO): Observable<RaceDO> {
    return this.http.post(this.postUrl + race.seriesId,
      JSON.stringify(race),
      {headers: new HttpHeaders().append("Content-Type", "application/json")
        .append("Authorization", this.auth.getAuthenticationString())});
  }

  deleteRace(race: RaceDO): Observable<RaceDO> {
    return this.http.delete(this.deleteUrl + race.seriesId + "?raceId="+race.id,
      {headers: new HttpHeaders().append("Content-Type", "application/json")
        .append("Authorization", this.auth.getAuthenticationString())});
  }
}
