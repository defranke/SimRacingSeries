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

    "TeamAlreadySaved": "Die Serie wurde bereits erstellt",
    "SeriesIsMissing": "Das Team muss einer Serie zugeordnet sein.",
    "TeamNameIsMissing": "Es muss ein Name für das Team angegeben werden.",
    "TeamNotSavedYet": "Das Team wurde noch nicht gespeichert",

    "DriverAlreadyCreated": "Der Fahrer wurde bereits erstellt.",
    "DriverNotCreatedYet": "Der Fahrer wurde noch nicht gespeichert.",
    "DriverSeriesIsMissing": "Der Fahrer muss einer Serie zugeordnet sein.",
    "DriverNameIsMissing": "Es muss ein Name für den Fahrer angegeben werden.",
    "DriverTeamIsMissing": "Es muss ein Team für den Fahrer angegeben werden.",

    "RaceAlreadySaved":"Das Rennen wurde bereits erstellt.",
    "RaceSeriesIsMissing":"Das Rennen muss einer Serie zugeordnet sein.",
    "RaceTrackIsMissing":"Es muss eine Strecke für das Rennen angegeben werden.",

    "EventAlreadyCreated":"Das Event wurde bereits erstellt..",
    "EventNotYetCreated":"Das Rennen wurde noch nicht erstellt.",
    "EventSeriesIsMissing":"Es muss eine Serie für das Event angegeben werden.",
    "EventRaceIdIsMissing":"Es muss ein Renne für das Event angegeben werden.",
    "EventTypeIsMissing":"Es muss ein Typ für die Veranstaltung angegeben werden.",
    "EventTitleIsMissing":"Es muss ein Titel für die Veranstaltung angegeben werden."
  };

  constructor() {
  }

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
