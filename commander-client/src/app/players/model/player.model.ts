
export class Player {
  public id;
  public firstName: string = '';
  public lastName: string = '';
  public nickname: string = '';
  public password: string = '';

  constructor( id: number | undefined, firstName: string, lastName: string, nickname: string, password: string ) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.nickname = nickname;
    this.password = password;
  }

}
