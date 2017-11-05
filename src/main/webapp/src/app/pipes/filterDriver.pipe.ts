import {Pipe, PipeTransform} from "@angular/core";
import {DriverDO} from "../model/DriverDO";
@Pipe({
  name: 'filterDriver',
  pure: false
})

export class FilterDriverPipe implements PipeTransform {

  transform(items: DriverDO[], forTeam: string) {
    if (!items || !forTeam) {
      return items;
    }
    return items.filter(driver => driver.teamId === forTeam);
  }
}
