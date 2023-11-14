import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import { environment } from "src/environments/environment"

@Injectable({
  providedIn: 'root'
})
export class PaypalService {

  constructor(private _http: HttpClient) {}

  url = environment.paypal_service_url;

  createPayment(amount:string){
    // let body:CreatePaypalPayment={
    //   amount:amount,
    //   transactionId:transactionId,
    //   shopId:shopId
    // }

    return this._http.post<any>(`${this.url}/create-payment`,amount);
  }

  confirm(){
    return this._http.post<any>(`${this.url}/confirm`,"54");
  }
}
