import { Component } from '@angular/core';
import { Http } from '@angular/http';

@Component({
  selector: 'splash',
  templateUrl: './splash.component.html',
  styleUrls: ['./splash.component.css']
})


export class SplashComponent {
  result = '';

  constructor(private http: Http){

  }

  private sayHello(): void {
    this.result = 'Loading...';
    this.http.get('series?name=Test1').subscribe(response => this.result = response.text());
  }
}
