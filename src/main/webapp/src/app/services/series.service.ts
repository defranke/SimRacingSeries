import {Injectable} from "@angular/core";

import "rxjs/add/operator/toPromise";

import {SeriesDO} from "../model/SeriesDO";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {AuthenticationService} from "./authentication.service";

@Injectable()
export class SeriesService {
  private getUrl = 'api/series?slugName=';
  private putUrl = 'api/series';
  private postUrl = 'api/series/';

  constructor(private http: HttpClient, private auth: AuthenticationService) {

  }

  putSeries(series: SeriesDO): Observable<SeriesDO> {
    return this.http.put(this.putUrl,
      JSON.stringify(series),
      {headers: new HttpHeaders().set("Content-Type", "application/json")});
  }

  postSeries(series: SeriesDO): Observable<SeriesDO> {
    return this.http.post(this.postUrl + series.id,
      JSON.stringify(series),
      {headers: new HttpHeaders().append("Content-Type", "application/json")
        .append("Authorization", this.auth.getAuthenticationString())});
  }

  findSeries(slug: String): Observable<SeriesDO> {
    const url = this.getUrl + slug;
    return this.http.get(url);
  }
}
