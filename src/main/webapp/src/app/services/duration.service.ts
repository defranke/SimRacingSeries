import {Injectable} from "@angular/core";

@Injectable()
export class DurationService {

  public format(milliseconds: number): string {
    if(!milliseconds) {
      return "";
    }
    const hours = Math.floor(milliseconds / (1000 * 60 * 60));
    let remaining = milliseconds - (1000 * 60 * 60) * hours;
    const minutes = Math.floor(remaining / (1000 * 60));
    remaining -= minutes * 1000 * 60;
    const seconds = Math.floor(remaining / 1000);
    const ms = remaining - seconds * 1000;

    let result = "";
    if(hours > 0) {
      result = result.concat(String(hours).padStart(2, "0"), ":");
    }
    result = result.concat(String(minutes).padStart(2, "0"), ":");
    result = result.concat(String(seconds).padStart(2, "0")).concat(".");
    result = result.concat(String(ms).padStart(3, "0"));
    return result;
  }

  public parse(input: string): number {
    // ((\d{0,2}):(?=\d{0,2}:))*((\d{0,2}):(?=\d{0,2}.))*((\d{0,2}).)(\d{0,3})
    const regex =  /((\d{0,2}):(?=\d{0,2}:))*((\d{0,2}):(?=\d{0,2}.))*((\d{0,2}).)(\d{0,3})/g;
    const groups = regex.exec(input);
    console.log(groups);
    if(!groups) {
      return 0;
    }

    let res = 0;
    if(groups[2]) {
      res += Number(groups[2]) * (1000 * 60 * 60); // hours
    }
    if(groups[4]) {
      res += Number(groups[4]) * (1000 * 60); // minutes
    }
    if(groups[6]) {
      res += Number(groups[6]) * (1000); // seconds
    }
    if(groups[7]) {
      res += Number(groups[7]); // ms
    }
    return res;
  }
}
