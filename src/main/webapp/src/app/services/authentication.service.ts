import {Injectable} from "@angular/core";

import "rxjs/add/operator/toPromise";

@Injectable()
export class AuthenticationService {
  private auth = '';

  constructor() {

  }

  public getAuthenticationString(): string {
    return this.auth;
  }

  public setAuthentication(username: string, password: string) {
    this.auth = "Basic " + btoa(username + ":" + password);
  }

  public clearAuthentication() {
    this.auth = "";
  }
}
