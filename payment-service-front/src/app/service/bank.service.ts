import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class BankService {
  constructor(private _http: HttpClient) {}

  url = environment.bank_url;

  getPaymentUrl(amount:string, transactionId: string, agencyId: string){
    let body = {
      amount: amount,
      merchantOrderId: transactionId,
      merchantId: agencyId,
      merchantTimestamp: new Date(),
      successUrl: "http://localhost:4200/success",
      errorUrl: "http://localhost:4200/error",
      failedUrl: "http://localhost:4200/failed"
    }
    return this._http.post<any>(`${this.url}getPaymentUrl`,body, {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*',
      }),
    });

  }

}
