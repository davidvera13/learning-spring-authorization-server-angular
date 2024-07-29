import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {ResourcesService} from "./services/resources.service";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  messageUser!: string;
  messageAdmin!: string

  constructor(
    private service: ResourcesService) {
  }

  onLogin(): void {
    window.location.href = "/oauth2/authorization/gateway";
  }

  user(): void {
    this.service.user().subscribe({
      next: res => this.messageUser = res.message,
      error: err => this.messageUser = err.statusText + ": " + err.status,
      complete: () => console.log('Completed')
    })
  }

  admin(): void {
    this.service.admin().subscribe({
      next: res => this.messageAdmin = res.message,
      error: err => this.messageAdmin = err.statusText + ": " + err.status,
      complete: () => console.log('Completed')
    })
  }
}

