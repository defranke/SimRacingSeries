import { Component } from '@angular/core';
import { Http } from '@angular/http';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent {
  title = 'app';
  result = '';

  constructor(private http: Http){

  }

  private sayHello(): void {
    this.result = 'Loading...';
    this.http.get('series?name=Test1').subscribe(response => this.result = response.text());
  }
}
