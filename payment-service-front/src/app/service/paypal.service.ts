import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import { environment } from "src/environments/environment"

@Injectable({
  providedIn: 'root'
})
export class PaypalService {

  constructor(private _http: HttpClient) {}

  url = environment.paypal_service_url;

  createPayment(amount:string, transactionId: string, agencyId: string){
     let body = {
       amount:amount,
       transactionId:transactionId,
       agencyId: agencyId
     }
    return this._http.post<any>(`${this.url}/create-payment`,body, {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*',
      }),
    });

  }

  confirm(price: string, transactionId: string, agencyId: string, paymentId: string, token: string, PayerID:string){
    let body = {
      amount:price,
      transactionId:transactionId,
      agencyId: agencyId,
      paymentId: paymentId,
      token: token,
      payerId: PayerID
    }
    return this._http.post<any>(`${this.url}/confirm`,body, {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*',
      }),
    });
  }
}
