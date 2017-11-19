import {Component} from "@angular/core";
import {AlertService} from "./services/alert.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent {
  title = 'app';
  alerts: any = [];

  constructor(private alertService: AlertService) {
    alertService.alertSubject.subscribe(a => this.alerts.push(a));
  }
}
