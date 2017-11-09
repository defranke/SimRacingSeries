import {Injectable} from "@angular/core";

import "rxjs/add/operator/toPromise";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {AuthenticationService} from "./authentication.service";
import {RaceDO} from "../model/RaceDO";

@Injectable()
export class RaceService {
  private putUrl = 'api/race/';
  //private postUrl = 'api/teams/';

  constructor(private http: HttpClient, private auth: AuthenticationService) {

  }

  putTeam(race: RaceDO): Observable<RaceDO> {
    return this.http.put(this.putUrl + race.seriesId,
      JSON.stringify(race),
      {headers: new HttpHeaders().append("Content-Type", "application/json")
        .append("Authorization", this.auth.getAuthenticationString())});
  }
}
