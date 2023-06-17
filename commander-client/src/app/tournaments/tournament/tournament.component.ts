import { Component, Input, OnInit } from '@angular/core';
import { Tournament } from '../model/tournament.model';
import { DomSanitizer } from '@angular/platform-browser';
import { Router } from '@angular/router';

@Component({
  selector: 'app-tournament',
  templateUrl: './tournament.component.html',
  styleUrls: ['./tournament.component.scss']
})
export class TournamentComponent implements OnInit {

  @Input()
  tournament!: Tournament;
  @Input()
  index!: number;

  constructor( private router: Router, private sanitizer: DomSanitizer ) { }

  ngOnInit(): void {
  }

  public tournamentDetail(id:number) {
    // const navigationDetails: string[] = ['/events/'];
    // if(id != null && id.length < 0) {
    //   navigationDetails.push(id);
    // }
    // this.router.navigate(navigationDetails);
    alert("TO IMPLEMENT")
  }

}
