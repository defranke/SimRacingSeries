import { Component } from '@angular/core';
import { Http } from '@angular/http';

@Component({
  selector: 'createSeries',
  templateUrl: './createSeries.component.html',
  styleUrls: ['./createSeries.component.css']
})


export class CreateSeriesComponent {
  result = '';

  constructor(private http: Http){

  }

  private createSeries(name: string): void {
    alert(name);
    this.result = 'Loading...';
    this.http.get('series?name=Test1').subscribe(response => this.result = response.text());
  }
}
