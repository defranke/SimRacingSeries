import {BrowserModule} from "@angular/platform-browser";
import {NgModule} from "@angular/core";

import {SeriesService} from "./services/series.service";

import {AppComponent} from "./app.component";
import {CreateSeriesComponent} from "./createSeries/createSeries.component";
import {DetailsComponent} from "./details/details.component";
import {RouterModule} from "@angular/router";
import {FormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import { ModalModule } from 'ngx-bootstrap/modal';
import {PasswordModalComponent} from "./modals/passwordModal.component";
import {PasswordHashService} from "./services/passwordHash.service";
import { MarkdownModule } from 'angular2-markdown';
import {SeriesModalComponent} from "./modals/seriesModal.component";
import {ReglementModalComponent} from "./modals/reglementModal.component";
import {PointsModalComponent} from "./modals/pointsModal.component";
import {TabsModule} from "ngx-bootstrap";
import {SeriesInformationComponent} from "./details/seriesInformation.component";

@NgModule({
  declarations: [
    AppComponent,
    CreateSeriesComponent,
    DetailsComponent,
    SeriesInformationComponent,

    PasswordModalComponent,
    SeriesModalComponent,
    ReglementModalComponent,
    PointsModalComponent
  ],
  entryComponents: [
    PasswordModalComponent,
    SeriesModalComponent,
    ReglementModalComponent,
    PointsModalComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    ModalModule.forRoot(),
    TabsModule.forRoot(),
    MarkdownModule.forRoot(),
    RouterModule.forRoot([
      {
        path: 's/:slugName',
        component: DetailsComponent
      },
      {
        path: 'series/:slugName',
        component: DetailsComponent
      },
      {
        path: 'createSeries',
        component: CreateSeriesComponent
      },
      {
        path: '',
        component: CreateSeriesComponent
      },
      {
        path: '**',
        component: CreateSeriesComponent
      }
    ])
  ],
  providers: [
    SeriesService,
    PasswordHashService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
