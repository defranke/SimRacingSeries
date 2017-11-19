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
}
