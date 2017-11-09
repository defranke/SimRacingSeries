import {SeriesDO} from "./SeriesDO";
import {TeamDO} from "./TeamDO";
import {DriverDO} from "./DriverDO";
import {RaceDO} from "./RaceDO";

export class CompleteSeriesTO {
  series: SeriesDO;
  teams: TeamDO[];
  drivers: DriverDO[];
  races: RaceDO[]
}
