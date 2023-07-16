import { DatePipe } from "@angular/common";
import { HttpClient, HttpParams } from "@angular/common/http";
import { EventEmitter, Injectable } from "@angular/core";
import { environment } from "src/environments/environment";
import { Player } from "src/app/players/model/player.model";

@Injectable()
export class PlayerService {

  private mainUrl: String = environment.serverUrl + '/player';

  constructor(private http: HttpClient, private datePipe: DatePipe) {}

  playerSelected = new EventEmitter<Player>();

  newPlayer(player: Player) {
    let url = this.mainUrl+"/new";

    return this.http.post<any>(
      url, player, {
        responseType: 'json'
      });
  }

}
