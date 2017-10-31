import {Subject} from "rxjs/Subject";
import {BsModalRef} from "ngx-bootstrap/modal";

export class BaseModal {
  public submitted: Subject<Boolean> = new Subject();
  public active: boolean = false;

  constructor(public bsModalRef: BsModalRef) {
  }

  public show() {
    this.active = true;
  }

  private submit(): void {
    this.active = false;
    this.submitted.next(true)
    this.bsModalRef.hide()
  }

  private cancel(): void {
    this.active = false;
    this.submitted.next(false)
    this.bsModalRef.hide()
  }
}
