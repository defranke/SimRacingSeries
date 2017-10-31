import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import {SeriesService} from './services/series.service'

import { AppComponent } from './app.component';
import { CreateSeriesComponent } from './createSeries/createSeries.component';
import { DetailsComponent } from './details/details.component';

import { HttpModule } from '@angular/http';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';

@NgModule({
  declarations: [
    AppComponent,
    CreateSeriesComponent,
    DetailsComponent
  ],
  imports: [
    BrowserModule,
    HttpModule,
    FormsModule,
    RouterModule.forRoot([
      {
        path: 'details/:name',
        component: DetailsComponent
      },
      {
        path: 'createSeries',
        component: CreateSeriesComponent
      },
      {
        path: '',
        component: CreateSeriesComponent
      }
    ])
  ],
  providers: [
    SeriesService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
