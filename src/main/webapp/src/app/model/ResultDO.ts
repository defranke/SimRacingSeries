
export class ResultDO {
  driverId: string;
  replacementDriver: string;
  ignoreForStanding: boolean;
  participated: boolean;

  // Both
  fastestTime: number; // Duration

  // Quali
  penaltyPositions: number;

  // Race
  gridPosition: number;
  finishPosition: number;
  totalTime: number; // Duration
  lapsCompleted: number;
  penaltySeconds: number;
  penaltyPoints: number;
  dnf: boolean;

  constructor() {
    this.participated = true;
    this.penaltyPoints = 0;
    this.penaltyPositions = 0;
    this.penaltySeconds = 0;
  }

}
