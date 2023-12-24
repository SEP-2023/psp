import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import { environment } from "src/environments/environment"

@Injectable({
  providedIn: 'root'
})
export class SubscriptionService {

  constructor(private _http: HttpClient) {}

  url = environment.psp_backend + "auth-service";

  subscribe(paymentMethod :string){
    let body = {
      paymentMethod:paymentMethod,
      agencyId: "nekiId"
    }
    return this._http.post<any>(`${this.url}/payment-methods`,body, {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*',
      }),
    });

  }

  getPaymentMethods(){
    return this._http.get<any>(`${this.url}/payment-methods/nekiId`, {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*',
      }),
    });

  }
}
