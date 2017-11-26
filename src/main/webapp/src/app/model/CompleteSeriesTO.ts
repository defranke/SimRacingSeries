import {SeriesDO} from "./SeriesDO";
import {TeamDO} from "./TeamDO";
import {DriverDO} from "./DriverDO";
import {RaceDO} from "./RaceDO";

export class CompleteSeriesTO {
  series: SeriesDO;
  teams: TeamDO[];
  drivers: DriverDO[];
  races: RaceDO[];


  public addTeam(team: TeamDO) {
    this.teams.push(team);
    this.sortTeams();
  }

  public deleteTeam(team: TeamDO) {
    let i = this.teams.indexOf(team);
    this.teams.splice(i, 1);
  }

  public sortTeams() {
    this.teams.sort((a, b) => a.name.localeCompare(b.name));
  }

  public addDriver(driver: DriverDO) {
    this.drivers.push(driver);
    this.sortDriver();
  }

  public deleteDriver(driver: DriverDO) {
    let i = this.drivers.indexOf(driver);
    this.drivers.splice(i, 1);
  }

  public sortDriver() {
    this.drivers.sort((a, b) => a.name.localeCompare(b.name));
  }

  public addRace(race: RaceDO) {
    this.races.push(race);
    this.sortRaces();
  }

  public deleteRace(race: RaceDO) {
    let i = this.races.indexOf(race);
    this.races.splice(i, 1);
  }

  public sortRaces() {
    this.races.sort((a, b) => a.timestamp - b.timestamp);
  }

  public sort() {
    this.sortTeams();
    this.sortDriver();
    this.sortRaces();
  }

  private getDriverNameForId(driverId: string): string {
    const foundDriver = this.findDriver(driverId);
    return foundDriver !== undefined ? foundDriver.name : "";
  }

  private getDriverNumberForId(driverId: string): string {
    const foundDriver = this.findDriver(driverId);
    return foundDriver !== undefined ? foundDriver.number : "";
  }
  private getDriverCarForId(driverId: string): string {
    const foundDriver = this.findDriver(driverId);
    return foundDriver !== undefined ? foundDriver.car : "-";
  }

  private getTeamColor(driverId: string): string {
    const team = this.findTeam(driverId);
    return team !== undefined ? team.color : "transparent";
  }

  private getTeamName(driverId: string): string {
    const team = this.findTeam(driverId);
    return team !== undefined ? team.name : undefined;
  }

  private findTeam(driverId: string): TeamDO {
    const driver = this.findDriver(driverId);
    if(driver !== undefined) {
      const foundTeam = this.teams.filter(t => t.id === driver.teamId);
      if (foundTeam.length > 0) {
        return foundTeam[0];
      }
    }
    return undefined;
  }

  private findDriver(driverId: string): DriverDO {
    const foundDriver = this.drivers.filter(d => d.id === driverId);
    if (foundDriver.length > 0) {
      return foundDriver[0];
    }
    return undefined;
  }
}
