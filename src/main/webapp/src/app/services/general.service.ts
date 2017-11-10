import {Injectable} from "@angular/core";

import "rxjs/add/operator/toPromise";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {CompleteSeriesTO} from "../model/CompleteSeriesTO";
import {AuthenticationService} from "./authentication.service";

@Injectable()
export class GeneralService {
  private getCompleteUrl = 'http://localhost:8080/api/general/completeSeries?slugName=';

  constructor(private http: HttpClient, private auth: AuthenticationService) {

  }

  getCompleteSeries(slug: String): Observable<CompleteSeriesTO> {
    const url = this.getCompleteUrl + slug;
    return this.http.get(url);
  }
}
