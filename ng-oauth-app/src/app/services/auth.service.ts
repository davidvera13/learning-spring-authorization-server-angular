import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {environment} from "../../environments/environment.development";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  tokenUrl: string;

  constructor(private http: HttpClient) {
    this.tokenUrl = environment.tokenEndpoint;
  }

  getToken(code: string, codeVerifier: string): Observable<any> {
    let body = new URLSearchParams();
    body.set("grant_type", environment.grantType);
    body.set("client_id", environment.clientId);
    body.set("redirect_uri", environment.redirectUri);
    body.set("scope", environment.scope);
    body.set("code_verifier", codeVerifier);
    body.set("code", code);
    const basicAuth = 'Basic ' + btoa('ng-oidc-client:secret')
    const headers = new HttpHeaders({
      'Content-Type':'application/x-www-form-urlencoded',
      'Accept': '*/*',
      'Authorization': basicAuth
      })
    const httpOptions = { headers: headers}
    return this.http.post<any>(this.tokenUrl, body, httpOptions)
  }
}
