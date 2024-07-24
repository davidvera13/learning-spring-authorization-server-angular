import {Component, OnInit} from '@angular/core';
import {environment} from "../../../environments/environment.development";
import {Router, RouterLink} from "@angular/router";
import {HttpParams} from "@angular/common/http";
import {DomSanitizer} from "@angular/platform-browser";
import {TokenService} from "../../services/token.service";
import {NgIf} from "@angular/common";

@Component({
  selector: 'app-menu',
  standalone: true,
    imports: [
        RouterLink,
        NgIf
    ],
  templateUrl: './menu.component.html',
  styleUrl: './menu.component.css'
})
export class MenuComponent implements OnInit {
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
  logoutUrl: string;
  isLogged!: boolean;
  isAdmin!: boolean;
  isUser!: boolean;

  constructor(
    private tokenService: TokenService
  ) {
    const httpParams = new HttpParams({
      fromObject: this.params
    });
    this.loginUrl = this.authorizeUri + httpParams.toString();
    this.logoutUrl = environment.logoutUrl;
  }

  ngOnInit(): void {
    this.getLogged();
  }



  getLogged(): void {
    this.isLogged = this.tokenService.isLoggedIn();
    this.isAdmin = this.tokenService.isAdmin();
    this.isUser = this.tokenService.isUser();

      console.log(this.isAdmin);
  }
}
