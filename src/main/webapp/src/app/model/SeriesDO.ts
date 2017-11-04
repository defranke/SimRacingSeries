
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
  points: number[];

  constructor() {
  }
}
