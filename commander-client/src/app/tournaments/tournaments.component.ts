import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TournamentService } from './service/tournament.service';
import { Tournament } from './model/tournament.model';

@Component({
  selector: 'app-tournaments',
  templateUrl: './tournaments.component.html',
  styleUrls: ['./tournaments.component.scss']
})
export class TournamentsComponent implements OnInit {

  tournaments: Tournament[] = [];

  constructor(private router: Router, private tournamentService: TournamentService) { }

  ngOnInit(): void {
    //TODO: use this when BE is ready
    // this.tournamentService.getTournaments().subscribe( ( data : Tournament[] ) => {
    //   this.tournaments = data;
    // } );
    var t = new Tournament(1, 'Torneo di prova', new Date(), false, false);
    this.tournaments = [t];
  }

  public goToCreate() {
    const navigationDetails: string[] = ['/create-torunament/'];
    //this.router.navigate(navigationDetails); TODO: to implement
  }

  sortByDate() {
    if (this.tournaments == null || this.tournaments.length < 1)
      return null;
    else
    return this.tournaments.sort((a, b) => a['creationDate'] > b['creationDate'] ? 1 : a['creationDate'] === b['creationDate'] ? 0 : -1);
  }


}
