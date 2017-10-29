import {Injectable} from '@angular/core';
import { Headers, Http } from '@angular/http';

import 'rxjs/add/operator/toPromise';

import {SeriesDO} from '../model/SeriesDO'

@Injectable()
export class SeriesService {
  private getUrl = 'series?name=';

  constructor(private http: Http) {

  }

  getAllSeries() : Promise<SeriesDO[]> {
    return Promise.resolve([
      new SeriesDO("Test", "GT3")
    ]);
  }

  findSeries(slug: String) : Promise<SeriesDO> {
    return this.http.get(this.getUrl + slug)
      .toPromise()
      .then(response => response.json().data as SeriesDO)
      .catch(this.handleError);
    //return Promise.resolve(new SeriesDO("test", "GT3"));
  }

  private handleError(error: any): Promise<any> {
    console.error('An error occurred', error); // for demo purposes only
    return Promise.reject(error.message || error);
  }

  createSeries() : SeriesDO {
    return null;
  }
}
