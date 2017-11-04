import {Injectable} from "@angular/core";

import "rxjs/add/operator/toPromise";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {CompleteSeriesTO} from "../model/CompleteSeriesTO";

@Injectable()
export class GeneralService {
  private getCompleteUrl = 'api/general/completeSeries?slugName=';

  constructor(private http: HttpClient) {

  }

  getCompleteSeries(slug: String): Observable<CompleteSeriesTO> {
    const url = this.getCompleteUrl + slug;
    return this.http.get(url);
  }
}
