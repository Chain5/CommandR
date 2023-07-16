import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { TournamentService } from './service/tournament.service';
import { Tournament } from './model/tournament.model';
import { NewTournamentComponent } from './new-tournament/new-tournament.component';
import { NewPlayerComponent } from '../players/new-player/new-player.component';
import { relative } from 'path';

@Component({
  selector: 'app-tournaments',
  templateUrl: './tournaments.component.html',
  styleUrls: ['./tournaments.component.scss']
})
export class TournamentsComponent implements OnInit {

  tournaments: Tournament[] = [];

  constructor(private router: Router, private tournamentService: TournamentService, private matDialog: MatDialog) { }

  ngOnInit(): void {
    this.tournamentService.getTournaments()
      .subscribe( ( data : Tournament[] ) => {
        this.tournaments = data
      } );
    // var t1 = new Tournament(1, 'Torneo di prova 1', new Date(), false, false, 0);
    // var t2 = new Tournament(2, 'Torneo di prova 2', new Date(), true, false, 12);
    // var t3 = new Tournament(3, 'Torneo di prova 3', new Date(), true, true, 4);
    // this.tournaments = [t1, t2, t3];
  }

  public goToCreateTournament() {

    const dialogRef =
    this.matDialog.open( NewTournamentComponent, {
      height: '400px',
      width: '400px'
    })
    .afterClosed()
    .subscribe(() => {
      //TODO: better solution -> pass a variable from dialog after his call to service
        window.location.reload()
    });
  }

  public goToCreatePlayer() {
    this.matDialog.open( NewPlayerComponent, {
      height: '400px',
      width: '400px'
    });
  }

  sortByDate() {
    if (this.tournaments == null || this.tournaments.length < 1)
      return null;
    else
    return this.tournaments.sort((a, b) => a['startDate'] > b['startDate'] ? 1 : a['startDate'] === b['startDate'] ? 0 : -1);
  }


}
