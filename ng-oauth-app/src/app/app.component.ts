import {Component, OnInit, ViewChild} from '@angular/core';
import {NavigationEnd, Router, RouterEvent, RouterOutlet} from '@angular/router';
import {MenuComponent} from "./components/menu/menu.component";
import {filter} from "rxjs/operators";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, MenuComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit{
  title = 'ng-oauth-app';
  @ViewChild('menu') menu!: MenuComponent;

  constructor(
      private router: Router
  ) {
  }

  ngOnInit(): void {
    this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd)
        this.menu.getLogged()
    })
  }


}
