import { Component } from '@angular/core';
import {environment} from "../../../environments/environment.development";
import {Router, RouterLink} from "@angular/router";
import {HttpParams} from "@angular/common/http";
import {DomSanitizer} from "@angular/platform-browser";

@Component({
  selector: 'app-menu',
  standalone: true,
  imports: [
    RouterLink
  ],
  templateUrl: './menu.component.html',
  styleUrl: './menu.component.css'
})
export class MenuComponent {
  authorizeUri = environment.authorizeUri;
  params: any = {
    redirect_uri : environment.redirectUri,
    client_id: environment.clientId ,
    scope: environment.scope,
    response_type: environment.responseType,
    response_mode: environment.responseMode,
    code_challenge_method: environment.codeChallengeMethod,
    code_challenge: environment.codeChallenge
  }
  loginUrl: string;

  constructor() {
    const httpParams = new HttpParams({
      fromObject: this.params
    });
    this.loginUrl = this.authorizeUri + httpParams.toString();
  }

  onLogout(): void {

  }
}
