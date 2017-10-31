import {Component, OnDestroy, OnInit} from "@angular/core";
import {ActivatedRoute} from "@angular/router";

import {SeriesService} from "../services/series.service";
import {SeriesDO} from "../model/SeriesDO";

import 'rxjs/add/operator/map';
import 'rxjs/add/operator/switchMap';

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
    this.route.paramMap
      .map(params => params.get("slugName"))
      .switchMap(slugName => this.seriesService.findSeries(slugName))
      .subscribe(
        data => {
          this.series = data
        },
        _ => {
          this.series = null;
        });
  }

  ngOnDestroy() {
    this.sub.unsubscribe();
  }
}
