import {Injectable} from "@angular/core";

import "rxjs/add/operator/toPromise";

import {SeriesDO} from "../model/SeriesDO";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs/Observable";

@Injectable()
export class SeriesService {
  private getUrl = 'api/series?slugName=';
  private putUrl = 'api/series';

  constructor(private http: HttpClient) {

  }

  putSeries(series: SeriesDO): Observable<SeriesDO> {
    return this.http.put(this.putUrl,
      JSON.stringify(series),
      {headers: new HttpHeaders().set("Content-Type", "application/json")});
  }

  findSeries(slug: String): Observable<SeriesDO> {
    const url = this.getUrl + slug;
    return this.http.get(url);
  }
}
