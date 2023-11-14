import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import { environment } from "src/environments/environment"

@Injectable({
  providedIn: 'root'
})
export class PaypalService {

  constructor(private _http: HttpClient) {}

  url = environment.paypal_service_url;

  createPayment(amount:string){
    // sve podatke proslijedi sa agencija fronta
     let body = {
       amount:amount,
       transactionId:"uzmiIdOdAgencijaFronta",
       agencyId: "shop"
     }
    return this._http.post<any>(`${this.url}/create-payment`,body, {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*',
      }),
    });

  }

  confirm(){
    return this._http.post<any>(`${this.url}/confirm`,"54");
  }
}
