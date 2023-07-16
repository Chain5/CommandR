import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatDialogModule } from '@angular/material/dialog';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { DatePipe, HashLocationStrategy, LocationStrategy } from '@angular/common';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { TournamentsComponent } from './tournaments/tournaments.component';
import { TournamentService } from './tournaments/service/tournament.service';
import { HeaderComponent } from './header/header.component';
import { SpinnerComponent } from './spinner/spinner.component';
import { LoadingInterceptor } from './loading/loading.interceptor';
import { TournamentComponent } from './tournaments/tournament/tournament.component';
import { TournamentDetailComponent } from './tournaments/tournament/tournament-detail/tournament-detail.component';
import { NewTournamentComponent } from './tournaments/new-tournament/new-tournament.component';
import { NewPlayerComponent } from './players/new-player/new-player.component';
import { PlayerService } from './tournaments/service/player.service';


@NgModule({
  declarations: [
    AppComponent,
    TournamentsComponent,
    HeaderComponent,
    SpinnerComponent,
    TournamentComponent,
    TournamentDetailComponent,
    NewTournamentComponent,
    NewPlayerComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatDialogModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [
    TournamentService,
    PlayerService,
    { provide: LocationStrategy, useClass: HashLocationStrategy },
    { provide: HTTP_INTERCEPTORS, useClass: LoadingInterceptor, multi:true },
    DatePipe
  ],
  entryComponents:[AppComponent],
  bootstrap: [AppComponent]
})
export class AppModule { }
