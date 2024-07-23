import { Injectable } from '@angular/core';

const ACCESS_TOKEN = "access_token";
const REFRESH_TOKEN = "refresh_token";

@Injectable({
  providedIn: 'root'
})
export class TokenService {

  constructor() { }

  setToken(accessToken: string, refreshToken: string) {
    localStorage.setItem(ACCESS_TOKEN, accessToken);
    localStorage.setItem(REFRESH_TOKEN, refreshToken);
  }
}

