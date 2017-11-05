import {Injectable} from "@angular/core";

import "rxjs/add/operator/toPromise";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs/Observable";
import {DriverDO} from "../model/DriverDO";

@Injectable()
export class DriverService {
  private putUrl = 'api/drivers';
  private postUrl = 'api/drivers';
  private deleteUrl = 'api/drivers?driverId=';

  constructor(private http: HttpClient) {

  }

  putDriver(driver: DriverDO): Observable<DriverDO> {
    return this.http.put(this.putUrl,
      JSON.stringify(driver),
      {headers: new HttpHeaders().set("Content-Type", "application/json")});
  }

  postDriver(driver: DriverDO): Observable<DriverDO> {
    return this.http.post(this.postUrl,
      JSON.stringify(driver),
      {headers: new HttpHeaders().set("Content-Type", "application/json")});
  }

  deleteDriver(driverId: string): Observable<DriverDO> {
    return this.http.delete(this.deleteUrl + driverId);
  }
}
