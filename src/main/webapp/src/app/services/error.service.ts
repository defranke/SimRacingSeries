import {Injectable} from "@angular/core";

import "rxjs/add/operator/toPromise";
import {HttpErrorResponse} from "@angular/common/http";

@Injectable()
export class ErrorService {
  private errorMessages: { [key: string]: string } = {
    "NameIsEmpty": "Es muss ein Name für die Rennserie angegeben werden.",
    "InvalidSlugName": "Die Kennung darf nur aus Buchstaben, Zahlen und folgenden Sonderzeichen bestehen: - und _",
    "SeriesNotCreatedYet": "Die Serie wurde noch nicht erstellt.",
    "SlugAlreadyUsed": "Die Kennung wird bereits von einer anderen Rennserie verwendet",

    "TeamAlreadySaved":"Die Serie wurde bereits erstellt",
    "SeriesIsMissing":"Das Team muss einer Serie zugeordnet sein.",
    "TeamNameIsMissing":"Es muss ein Name für das Team angegeben werden.",
    "TeamNotSavedYet":"Das Team wurde noch nicht gespeichert"
  };

  constructor() {}

  getFromMsg(error: string): string {
    if (this.errorMessages[error]) {
      return this.errorMessages[error];
    } else {
      return error;
    }
  }

  getFromError(error: HttpErrorResponse): string {
    if (this.errorMessages[error.error.error]) {
      return this.errorMessages[error.error.error];
    } else {
      return error.error.message;
    }
  }

}
