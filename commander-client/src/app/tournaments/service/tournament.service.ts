import { DatePipe } from "@angular/common";
import { HttpClient, HttpParams } from "@angular/common/http";
import { EventEmitter, Injectable } from "@angular/core";
import { environment } from "src/environments/environment";
import { Tournament } from "../model/tournament.model";

@Injectable()
export class TournamentService {

  private mainUrl: String = environment.serverUrl + '/tournament';

  constructor(private http: HttpClient, private datePipe: DatePipe) {}

  tournamentSelected = new EventEmitter<Tournament>();

  getTournaments() {
    let url = this.mainUrl+"/get";
    let params = new HttpParams().set("getOnlyNewTournaments", false);

    return this.http.get<Tournament[]>(url,
    {
      headers: {
        'Content-Type': 'application/json'
      },
      responseType: 'json',
      params: params
    });
  }

  getTournament(index: string) {
    let url = this.mainUrl+"/get/"+index;

    return this.http.get<Tournament>(url, {
      headers: {
        'Content-Type': 'application/json'
      },
      responseType: 'json'
    });
  }

  newTournament(tournament: Tournament) {
    let url = this.mainUrl+"/new";

    return this.http.post<any>(
      url, tournament, {
        responseType: 'json'
      });
  }

}
