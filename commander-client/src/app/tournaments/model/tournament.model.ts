
export class Tournament {
  public id;
  public title: string = '';
  public creationDate: Date = new Date();
  public isStarted: boolean = false;


  constructor( id: number | undefined, title: string, creationDate: Date, isStarted: boolean ) {
    this.id = id;
    this.title = title;
    this.creationDate = creationDate;
    this.isStarted = isStarted
  }


  public static getEmptyTournament() {

    return new Tournament(
      undefined,
      '',
      new Date(),
      false
    )
  }


}
