
export class Tournament {
  public id;
  public tournamentName: string = '';
  public startDate: Date = new Date();
  public started: boolean = false;
  public finished: boolean = false;
  public subscribedPlayerCounter: number = 0;


  constructor( id: number | undefined, tournamentName: string, startDate: Date, started: boolean, finished: boolean, subscribedPlayerCounter: number ) {
    this.id = id;
    this.tournamentName = tournamentName;
    this.startDate = startDate;
    this.started = started;
    this.finished = finished;
    this.subscribedPlayerCounter = subscribedPlayerCounter;
  }


  public static getEmptyTournament() {

    return new Tournament(
      undefined,
      '',
      new Date(),
      false,
      false,
      0
    )
  }


}
