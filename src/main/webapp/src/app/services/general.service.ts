import {Injectable} from "@angular/core";

import "rxjs/add/operator/toPromise";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {CompleteSeriesTO} from "../model/CompleteSeriesTO";
import {AuthenticationService} from "./authentication.service";

@Injectable()
export class GeneralService {
  private getCompleteUrl = '/api/general/completeSeries?slugName=';

  constructor(private http: HttpClient, private auth: AuthenticationService) {

  }

  getCompleteSeries(slug: String): Observable<CompleteSeriesTO> {
    const url = this.getCompleteUrl + slug;
    return new Observable((observer) => {
      this.http.get(url).subscribe(
        data => observer.next(Object.assign(new CompleteSeriesTO(), data)),
        err => observer.error(err)
      );
    });
  }
}
