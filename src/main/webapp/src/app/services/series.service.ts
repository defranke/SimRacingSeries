import {Injectable} from "@angular/core";
import {Headers, Http} from "@angular/http";

import "rxjs/add/operator/toPromise";

import {SeriesDO} from "../model/SeriesDO";

@Injectable()
export class SeriesService {
  private server = "http://localhost:8080/";
  private getUrl = this.server + 'series?slugName=';
  private putUrl = this.server + 'series';

  constructor(private http: Http) {

  }

  putSeries(series: SeriesDO): Promise<SeriesDO> {
    return this.http.put(this.putUrl, JSON.stringify(series), {
      headers: new Headers({'Content-Type': 'application/json'})
    })
      .toPromise()
      .then(response => response.json() as SeriesDO)
      .catch(error => this.handleError(error))
  }

  findSeries(slug: String): Promise<SeriesDO> {
    const url = '${this.getUrl}/${slug}';
    return this.http.get(url)
      .toPromise()
      .then(response => response.json() as SeriesDO)
      .catch(this.handleError);
  }

  private handleError(error: any): Promise<any> {
    console.error('An error occurred', error);
    return Promise.reject(error.message || error);
  }

  createSeries(): SeriesDO {
    return null;
  }
}
