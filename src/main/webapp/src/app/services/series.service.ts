import {Injectable} from '@angular/core';
import { Headers, Http } from '@angular/http';

import 'rxjs/add/operator/toPromise';

import {SeriesDO} from '../model/SeriesDO'

@Injectable()
export class SeriesService {
  private getUrl = 'series?slugName=';

  constructor(private http: Http) {

  }

  getAllSeries() : Promise<SeriesDO[]> {
    return Promise.resolve([
      new SeriesDO("Test", "GT3")
    ]);
  }

  findSeries(slug: String) : Promise<SeriesDO> {
    const url = `${this.getUrl}/${slug}`;
    return this.http.get(url)
      .toPromise()
      .then(response => response.json() as SeriesDO)
      .catch(this.handleError);
  }

  private handleError(error: any): Promise<any> {
    console.error('An error occurred', error);
    return Promise.reject(error.message || error);
  }

  createSeries() : SeriesDO {
    return null;
  }
}
