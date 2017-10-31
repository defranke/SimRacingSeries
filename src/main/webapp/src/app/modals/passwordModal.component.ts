
import {Component} from "@angular/core";
import {BaseModal} from "./BaseModal";
import {BsModalRef} from "ngx-bootstrap/modal";

@Component({
  selector: 'modal-content',
  templateUrl: './passwordModal.component.html'
})
export class PasswordModalComponent extends BaseModal {
  public password: string;

  constructor(public bsModalRef: BsModalRef) {
    super(bsModalRef);
  }
}

