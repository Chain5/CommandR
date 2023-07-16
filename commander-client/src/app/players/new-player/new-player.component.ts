import { Component, Inject } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from  '@angular/material/dialog';
import { PlayerService } from 'src/app/tournaments/service/player.service';
import { Player } from '../model/player.model';

@Component({
  selector: 'app-new-player',
  templateUrl: './new-player.component.html',
  styleUrls: ['./new-player.component.scss']
})
export class NewPlayerComponent {

  // Tournament Object to be created
  newPlayer: Player | undefined;

  creationForm = this.formBuilder.group({
    firstName: '',
    lastName: '',
    nickname: '',
    password: '',
    autogeneratePassword: false
  })

  constructor(
    private  dialogRef:  MatDialogRef<NewPlayerComponent>,
    @Inject(MAT_DIALOG_DATA) public  data:  any,
    private formBuilder: FormBuilder,
    private playerService: PlayerService
    ) { }

  ngOnInit(): void {
  }

  public closeMe() {
    this.dialogRef.close();
  }

  onSend(){

    let password = this.creationForm.controls['password'].value

    if(this.creationForm.controls['autogeneratePassword'].value) {
      password = 'n0tS3cur3P455w0rd!'
    }

    this.newPlayer = new Player(
      undefined,
      this.creationForm.controls['firstName'].value,
      this.creationForm.controls['lastName'].value,
      this.creationForm.controls['nickname'].value,
      password
    );

    this.playerService.newPlayer(this.newPlayer).subscribe(
      res => {
        alert("Giocatore salvato con successo!");
        this.closeMe();
      },
      err => {
        alert("Errore durante il salvataggio.\nMotivo: "+err.error.message);
      }
    );
  }


}
