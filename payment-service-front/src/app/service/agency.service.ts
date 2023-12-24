import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import { environment } from "src/environments/environment"

@Injectable({
  providedIn: 'root'
})
export class AgencyService {

  constructor(private _http: HttpClient) {}

  url = environment.agency_backend;

  confirm(transactionId: string){
    return this._http.post<any>(`${this.url}confirm-transaction`,transactionId, {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*',
      }),
    });

  }

  cancel(transactionId: string){
    return this._http.post<any>(`${this.url}cancel-transaction`,transactionId, {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*',
      }),
    });

  }

}
