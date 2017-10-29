import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import {SeriesService} from './services/series.service'

import { AppComponent } from './app.component';
import { SplashComponent } from './splash/splash.component';
import { DetailsComponent } from './details/details.component';

import { HttpModule } from '@angular/http';
import { RouterModule } from '@angular/router';

@NgModule({
  declarations: [
    AppComponent,
    SplashComponent,
    DetailsComponent
  ],
  imports: [
    BrowserModule,
    HttpModule,
    RouterModule.forRoot([
      {
        path: 'details/:name',
        component: DetailsComponent
      },
      {
        path: 'splash',
        component: SplashComponent
      },
      {
        path: '',
        redirectTo: 'splash',
        pathMatch: 'full'
      }
    ])
  ],
  providers: [
    SeriesService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
