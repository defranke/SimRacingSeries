
export class SeriesDO {
  id: string;
  name: string;
  slugName: string;
  description: string;
  reglement: string;
  password: string;
  isPublic: boolean;
  pointsForQualifying: number;
  pointsForFastestLap: number;
  points: { [key: number]: number};

  constructor() {
  }

  public validate(passwordRequired: boolean): string {
    if (this.isEmpty(this.name)) {
      return 'Es muss ein Name f&uuml;r die Rennserie angegeben werden.';
    } else if (this.isEmpty(this.slugName) || !this.slugName.match("^[a-zA-Z0-9_-]+$")) {
      return 'Die Kennung darf nur aus Buchstaben, Zahlen und folgenden Sonderzeichen bestehen: - und _';
    } else if (passwordRequired && this.isEmpty(this.password)) {
      return 'Es muss ein Passwort zum Bearbeiten der Rennserie festgelegt werden.';
    } else {
      return null;
    }
  }

  private isEmpty(str: string): boolean {
    return typeof str == 'undefined' || !str || str.length == 0;
  }
}
