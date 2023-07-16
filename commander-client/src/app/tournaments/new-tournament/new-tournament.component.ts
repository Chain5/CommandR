import { Component, Inject } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from  '@angular/material/dialog';
import { TournamentService } from '../service/tournament.service';
import { Tournament } from '../model/tournament.model';

@Component({
  selector: 'app-new-tournament',
  templateUrl: './new-tournament.component.html',
  styleUrls: ['./new-tournament.component.scss']
})
export class NewTournamentComponent {

  // Tournament Object to be created
  newTournament: Tournament | undefined;

  creationForm = this.formBuilder.group({
    tournamentName: '',
    startDate: new Date
  })

  constructor(
    private dialogRef: MatDialogRef<NewTournamentComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private formBuilder: FormBuilder,
    private tournamentService: TournamentService ) { }

  ngOnInit(): void {
  }

  public  closeMe() {
    this.dialogRef.close();
  }

  onSend(){

    this.newTournament = new Tournament(
      undefined,
      this.creationForm.controls['tournamentName'].value,
      this.creationForm.controls['startDate'].value,
      false,
      false,
      0
    );

    this.tournamentService.newTournament(this.newTournament).subscribe(
      res => {
        alert("Torneo salvato con successo!");
        this.closeMe();
      },
      err => {
        alert("Errore durante il salvataggio.\nMotivo: "+err.error.message);
      }
    );
  }

}
