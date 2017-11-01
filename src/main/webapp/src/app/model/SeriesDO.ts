
export class SeriesDO {
  id: string;
  name: string;
  slugName: string;
  description: string;
  reglement: string;
  password: string;
  isPublic: boolean;
  points: { [key: number]: number}

  constructor() {
  }
}
