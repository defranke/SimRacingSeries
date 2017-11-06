import {Injectable} from "@angular/core";

import "rxjs/add/operator/toPromise";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {TeamDO} from "../model/TeamDO";
import {AuthenticationService} from "./authentication.service";

@Injectable()
export class TeamService {
  private deleteUrl = 'api/teams/';
  private putUrl = 'api/teams/';
  private postUrl = 'api/teams/';

  constructor(private http: HttpClient, private auth: AuthenticationService) {

  }

  putTeam(team: TeamDO): Observable<TeamDO> {
    return this.http.put(this.putUrl + team.seriesId,
      JSON.stringify(team),
      {headers: new HttpHeaders().append("Content-Type", "application/json")
        .append("Authorization", this.auth.getAuthenticationString())});
  }

  postTeam(team: TeamDO): Observable<TeamDO> {
    return this.http.post(this.postUrl + team.seriesId,
      JSON.stringify(team),
      {headers: new HttpHeaders().append("Content-Type", "application/json")
        .append("Authorization", this.auth.getAuthenticationString())});
  }

  deleteTeam(seriesId: string, teamId: string): Observable<TeamDO> {
    return this.http.delete(this.deleteUrl + seriesId + "?teamId=" + teamId,
      {headers: new HttpHeaders().append("Content-Type", "application/json")
        .append("Authorization", this.auth.getAuthenticationString())});
  }
}
