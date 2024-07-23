import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment.development";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ResourcesService {
  resourcesUrl: string;
  constructor(private httpClient: HttpClient) {
    this.resourcesUrl = environment.resourceServerUrl;
  }

  public getUserMessage(): Observable<any> {
    return this.httpClient.get<any>(this.resourcesUrl + '/users')
  }

  public getAdminMessage(): Observable<any> {
    return this.httpClient.get<any>(this.resourcesUrl + '/admins')
  }
}
