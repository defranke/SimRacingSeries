import {Injectable} from "@angular/core";

import "rxjs/add/operator/toPromise";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {TeamDO} from "../model/TeamDO";

@Injectable()
export class TeamService {
  private deleteUrl = 'api/teams?teamId=';
  private putUrl = 'api/teams';
  private postUrl = 'api/teams';

  constructor(private http: HttpClient) {

  }

  putTeam(team: TeamDO): Observable<TeamDO> {
    return this.http.put(this.putUrl,
      JSON.stringify(team),
      {headers: new HttpHeaders().set("Content-Type", "application/json")});
  }

  postTeam(team: TeamDO): Observable<TeamDO> {
    return this.http.post(this.postUrl,
      JSON.stringify(team),
      {headers: new HttpHeaders().set("Content-Type", "application/json")});
  }

  deleteTeam(teamId: string): Observable<TeamDO> {
    return this.http.delete(this.deleteUrl + teamId);
  }
}
