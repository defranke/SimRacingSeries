import {BrowserModule} from "@angular/platform-browser";
import {NgModule} from "@angular/core";

import {SeriesService} from "./services/series.service";

import {AppComponent} from "./app.component";
import {CreateSeriesComponent} from "./createSeries/createSeries.component";
import {DetailsComponent} from "./details/details.component";
import {RouterModule} from "@angular/router";
import {FormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import {ModalModule} from "ngx-bootstrap/modal";
import {PasswordModalComponent} from "./modals/passwordModal.component";
import {PasswordHashService} from "./services/passwordHash.service";
import {MarkdownModule} from "angular2-markdown";
import {ColorPickerModule} from "ngx-color-picker";
import {SeriesModalComponent} from "./modals/series/seriesModal.component";
import {ReglementModalComponent} from "./modals/series/reglementModal.component";
import {PointsModalComponent} from "./modals/series/pointsModal.component";
import {BsDatepickerModule, CollapseModule, TabsModule} from "ngx-bootstrap";
import {SeriesInformationComponent} from "./details/information/seriesInformation.component";
import {ErrorService} from "./services/error.service";
import {TeamsComponent} from "./details/teams/teams.component";
import {GeneralService} from "./services/general.service";
import {TeamService} from "./services/team.service";
import {TeamModalComponent} from "./modals/team/teamModal.component";
import {DriverModalComponent} from "./modals/team/driverModal.component";
import {DriverService} from "./services/driver.service";
import {FilterDriverPipe} from "./pipes/filterDriver.pipe";
import {AuthenticationService} from "./services/authentication.service";
import {RaceListComponent} from "./details/races/raceList.component";
import {RaceModalComponent} from "./modals/race/raceModal.component";
import {RaceService} from "./services/race.service";
import {RaceComponent} from "./details/races/race.component";
import {RaceDescriptionModalComponent} from "./modals/race/raceDescriptionModal.component";

@NgModule({
  declarations: [
    AppComponent,
    CreateSeriesComponent,
    DetailsComponent,
    SeriesInformationComponent,
    TeamsComponent,
    RaceListComponent,
    RaceComponent,

    PasswordModalComponent,
    SeriesModalComponent,
    ReglementModalComponent,
    PointsModalComponent,
    TeamModalComponent,
    DriverModalComponent,
    RaceModalComponent,
    RaceDescriptionModalComponent,

    FilterDriverPipe
  ],
  entryComponents: [
    PasswordModalComponent,
    SeriesModalComponent,
    ReglementModalComponent,
    PointsModalComponent,
    TeamModalComponent,
    DriverModalComponent,
    RaceModalComponent,
    RaceDescriptionModalComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    ModalModule.forRoot(),
    TabsModule.forRoot(),
    MarkdownModule.forRoot(),
    CollapseModule.forRoot(),
    BsDatepickerModule.forRoot(),
    ColorPickerModule,
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
    GeneralService,
    TeamService,
    DriverService,
    PasswordHashService,
    ErrorService,
    AuthenticationService,
    RaceService
  ],
  bootstrap: [AppComponent]
})
export class AppModule {

  constructor() {
    //defineLocale('de', de);
  }
}
