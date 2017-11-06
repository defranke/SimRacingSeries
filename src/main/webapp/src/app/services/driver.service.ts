import {Injectable} from "@angular/core";

import "rxjs/add/operator/toPromise";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {DriverDO} from "../model/DriverDO";
import {AuthenticationService} from "./authentication.service";

@Injectable()
export class DriverService {
  private putUrl = 'api/drivers/';
  private postUrl = 'api/drivers/';
  private deleteUrl = 'api/drivers/';

  constructor(private http: HttpClient, private auth: AuthenticationService) {

  }

  putDriver(driver: DriverDO): Observable<DriverDO> {
    return this.http.put(this.putUrl + driver.seriesId,
      JSON.stringify(driver),
      {headers: new HttpHeaders().append("Content-Type", "application/json")
        .append("Authorization", this.auth.getAuthenticationString())});
  }

  postDriver(driver: DriverDO): Observable<DriverDO> {
    return this.http.post(this.postUrl + driver.seriesId,
      JSON.stringify(driver),
      {headers: new HttpHeaders().append("Content-Type", "application/json")
        .append("Authorization", this.auth.getAuthenticationString())});
  }

  deleteDriver(seriesId, driverId: string): Observable<DriverDO> {
    return this.http.delete(this.deleteUrl + seriesId + "?driverId=" + driverId);
  }
}
