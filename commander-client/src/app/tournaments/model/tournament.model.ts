
export class Tournament {
  public id;
  public title: string = '';
  public creationDate: Date = new Date();
  public isStarted: boolean = false;
  public isFinished: boolean = false;


  constructor( id: number | undefined, title: string, creationDate: Date, isStarted: boolean, isFinished: boolean ) {
    this.id = id;
    this.title = title;
    this.creationDate = creationDate;
    this.isStarted = isStarted;
    this.isFinished = isFinished;
  }


  public static getEmptyTournament() {

    return new Tournament(
      undefined,
      '',
      new Date(),
      false,
      false
    )
  }


}
