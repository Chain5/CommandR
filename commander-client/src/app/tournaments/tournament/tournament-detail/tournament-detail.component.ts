import { Component, OnInit } from '@angular/core';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { ActivatedRoute, Params } from '@angular/router';

import { TournamentService } from '../../service/tournament.service';
import { Tournament } from '../../model/tournament.model';

@Component({
  selector: 'app-tournament-detail',
  templateUrl: './tournament-detail.component.html',
  styleUrls: ['./tournament-detail.component.scss']
})
export class TournamentDetailComponent implements OnInit {

  selectedTournament: Tournament = Tournament.getEmptyTournament();
  imagePath: SafeResourceUrl = "";
  setImage = false;

  constructor(private route: ActivatedRoute,
    private service: TournamentService, private sanitizer: DomSanitizer) { }

  ngOnInit(): void {
    this.route.params.subscribe(
      (params: Params) => {
        this.service.getTournament(params['id']) //
          .subscribe((data : Tournament) => {
            this.selectedTournament = data;
          });
      }
    )
  }

}
