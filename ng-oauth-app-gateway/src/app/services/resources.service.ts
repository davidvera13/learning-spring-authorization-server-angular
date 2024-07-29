import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ResourcesService {

  constructor(private http: HttpClient) { }

  user(): Observable<any> {
    return this.http.get<any>(
      "/v1/api/resources/users"
    )
  }

  admin(): Observable<any> {
    return this.http.get<any>(
      "/v1/api/resources/admins"
    )
  }
}
