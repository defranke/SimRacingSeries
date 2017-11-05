import {SeriesDO} from "./SeriesDO";
import {TeamDO} from "./TeamDO";
import {DriverDO} from "./DriverDO";

export class CompleteSeriesTO {
  series: SeriesDO;
  teams: TeamDO[];
  drivers: DriverDO[];
}
