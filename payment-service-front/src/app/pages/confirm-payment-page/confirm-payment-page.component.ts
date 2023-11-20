import { Component, OnInit } from '@angular/core';
import {PaypalService} from "../../service/paypal.service";
import {environment} from "../../../environments/environment";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-confirm-payment-page',
  templateUrl: './confirm-payment-page.component.html',
  styleUrls: ['./confirm-payment-page.component.css']
})
export class ConfirmPaymentPageComponent implements OnInit {

  constructor(private paypalService: PaypalService, private route: ActivatedRoute) { }
  private price!: string;
  private transactionId!: string;
  private agencyId!: string;
  private paymentId!: string;
  private token!: string;
  private PayerID!: string;

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.price = params['price'];
      this.transactionId = params['transactionId'];
      this.agencyId = params['agencyId'];
      this.paymentId = params['paymentId'];
      this.token = params['token'];
      this.PayerID = params['PayerID'];
      console.log(this.PayerID)
    });
  }

  agencyFrontURL = environment.agency_front_url;

  confirmPayment(){
    this.paypalService
      .confirm(this.price, this.transactionId, this.agencyId, this.paymentId, this.token, this.PayerID)
      .subscribe(
        (data) => {
          console.log(data)
          if (data.executed){
            window.location.href = "/success?transactionId="+ this.transactionId;
          }else {
            window.location.href = "/canceled?transactionId="+ this.transactionId;
          }
        },
        (error) => {
          window.location.href = "/canceled?transactionId="+ this.transactionId;
        }
      );
  }

  cancelPayment(){
    window.location.href = this.agencyFrontURL + "/payment-canceled"
  }

}
