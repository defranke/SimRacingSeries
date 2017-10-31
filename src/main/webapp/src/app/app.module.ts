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

@NgModule({
  declarations: [
    AppComponent,
    CreateSeriesComponent,
    DetailsComponent,
    PasswordModalComponent
  ],
  entryComponents: [
    PasswordModalComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    ModalModule.forRoot(),
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
    SeriesService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
