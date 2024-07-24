import { Injectable } from '@angular/core';

const ACCESS_TOKEN = "access_token";
const REFRESH_TOKEN = "refresh_token";

@Injectable({
  providedIn: 'root'
})
export class TokenService {

  constructor() { }

  setToken(accessToken: string, refreshToken: string) {
    localStorage.removeItem(ACCESS_TOKEN);
    localStorage.removeItem(REFRESH_TOKEN);
    localStorage.setItem(ACCESS_TOKEN, accessToken);
    localStorage.setItem(REFRESH_TOKEN, refreshToken);
  }

  getAccessToken(): string | null {
    return localStorage.getItem(ACCESS_TOKEN);
  }

  getRefreshToken(): string | null {
    return localStorage.getItem(REFRESH_TOKEN);
  }

  clear() {
    localStorage.removeItem(ACCESS_TOKEN);
    localStorage.removeItem(REFRESH_TOKEN);
  }

  isLoggedIn(): boolean {
    return localStorage.getItem(ACCESS_TOKEN) != null;
  }

  isAdmin(): boolean {
    if(!this.isLoggedIn())
      return false;
    const token = this.getAccessToken();
    const payload = token?.split(".")[1]; //// AAA.vvvv.cccc
    const decoded = atob(payload! );
    const values = JSON.parse(decoded);
    const roles = values["roles"];
    return roles.indexOf('ROLE_ADMIN') >= 0;
  }

  isUser(): boolean {
    if(!this.isLoggedIn())
      return false;
    const token = this.getAccessToken();
    const payload = token?.split(".")[1]; //// AAA.vvvv.cccc
    const decoded = atob(payload! );
    const values = JSON.parse(decoded);
    const roles = values["roles"];
    return roles.indexOf('ROLE_USER') >= 0;
  }
}

