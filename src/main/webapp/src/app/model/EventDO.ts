import {ResultDO} from "./ResultDO";

export class EventDO {
  id: string;
  raceId: string;
  seriesId: string;
  type: string;
  title: string;
  description: string;

  results: ResultDO[];
}

export const EVENTTYPES: Object[] = [
  {type: "QUALIFYING", name: "Qualifying"},
  {type: "RACE", name: "Rennen"},
]
