import {Component, Input} from "@angular/core";
import {RaceDO} from "../../model/RaceDO";
import {BsModalService} from "ngx-bootstrap";
import {RaceDescriptionModalComponent} from "../../modals/race/raceDescriptionModal.component";

@Component({
  selector: 'race',
  templateUrl: './race.component.html'
})

export class RaceComponent {

  @Input() race: RaceDO;
  @Input() editing: boolean;

  constructor(private modalService: BsModalService) {

  }

  editRaceDescription() {
    if(!this.editing) {
      return;
    }
    let bsModalRef = this.modalService.show(RaceDescriptionModalComponent, {class: 'modal-lg'});
    bsModalRef.content.showFor(this.race);
    bsModalRef.content.submitted.subscribe(res => {
      if(res) {
        // Done
      }
    });
  }
}
