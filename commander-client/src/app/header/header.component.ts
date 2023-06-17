import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

  public goToAds() {
    alert("Funzione non ancora disponibile");
    // this.router.navigate(['/ads']);
  }

  public goToLogin() {
    alert("Funzione non ancora disponibile");
    // this.router.navigate(['/login']);
  }

}
