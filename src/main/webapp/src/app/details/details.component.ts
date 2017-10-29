import { Component, OnInit, OnDestroy } from '@angular/core';
import { Http } from '@angular/http';
import { ActivatedRoute } from '@angular/router'

import {SeriesService } from '../services/series.service'
import {SeriesDO} from '../model/SeriesDO'

@Component({
  selector: 'seriesDetails',
  templateUrl: './details.component.html',
  styleUrls: ['./details.component.css']
})


export class DetailsComponent implements OnInit, OnDestroy {
  series: SeriesDO
  private sub: any;

  constructor(private route: ActivatedRoute, private seriesService: SeriesService) {

  }

  ngOnInit() {
    this.sub = this.route.params.subscribe(params => {
       this.seriesService.findSeries(params['name']).then(series => this.series = series);
    });
  }

  ngOnDestroy() {
    this.sub.unsubscribe();
  }
}
