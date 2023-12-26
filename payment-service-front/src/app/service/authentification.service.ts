import { Injectable } from '@angular/core';
import {environment} from "../../environments/environment";
import {HttpClient, HttpHeaders, HttpStatusCode} from "@angular/common/http";
import {Agency, LoginInfo} from "../model/agency";

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  constructor(private _http: HttpClient) {}

  url = environment.psp_backend + 'auth-service';

  registerUser(agency: Agency) {
    const newUrl = this.url + '/register';
    return this._http.post<any>(newUrl, agency,{
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*',
      }),
    });
  }

  loginUser(loginInfo: LoginInfo) {
    const newUrl = this.url + '/login';
    return this._http.post<any>(newUrl, loginInfo,{
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*',
      }),
    });
  }

  logout(token: string) {
    const newUrl = this.url + '/logout';
    return this._http.post<HttpStatusCode>(newUrl, token);
  }
}
