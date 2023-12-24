import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import { environment } from "src/environments/environment"

@Injectable({
  providedIn: 'root'
})
export class CryptoService {

  constructor(private _http: HttpClient) {}

  url = environment.crypto_service_url;

  createPayment(amount:string, transactionId: string, agencyId: string){
    let body = {
      amount:"0.2",
      transactionId:"1",
      agencyId: "1"
    }
    return this._http.post<any>(`${this.url}/create-payment`,body, {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*',
      }),
    });

  }

  confirm(transactionId: string){
    return this._http.get<any>(`${this.url}/confirm/${transactionId}`, {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*',
      }),
    });
  }

  cancel(transactionId: string){
    return this._http.get<any>(`${this.url}/cancel/${transactionId}`, {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*',
      }),
    });
  }
}
