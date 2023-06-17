import { DatePipe } from "@angular/common";
import { HttpClient, HttpParams } from "@angular/common/http";
import { EventEmitter, Injectable } from "@angular/core";
import { environment } from "src/environments/environment";
import { Tournament } from "../model/tournament.model";

@Injectable()
export class TournamentService {

  private mainUrl: String = environment.serverUrl + 'tournament/';

  constructor(private http: HttpClient, private datePipe: DatePipe) {}

  tournamentSelected = new EventEmitter<Tournament>();

  getTournaments() {
    let url = this.mainUrl+"get";

    return this.http.get<Tournament[]>(url, {
      headers: {
        'Content-Type': 'application/json'
      },
      responseType: 'json'
    });
  }

}
