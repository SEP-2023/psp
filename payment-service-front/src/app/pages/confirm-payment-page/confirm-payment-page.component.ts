import { Component, OnInit } from '@angular/core';
import {PaypalService} from "../../service/paypal.service";
import {environment} from "../../../environments/environment";

@Component({
  selector: 'app-confirm-payment-page',
  templateUrl: './confirm-payment-page.component.html',
  styleUrls: ['./confirm-payment-page.component.css']
})
export class ConfirmPaymentPageComponent implements OnInit {

  constructor(private paypalService: PaypalService) { }

  ngOnInit(): void {
  }

  agencyFrontURL = environment.agency_front_url;

  confirmPayment(){
    this.paypalService
      .confirm()
      .subscribe(
        () => {
          window.location.href = this.agencyFrontURL + "/payment-successful";
        },
        (error) => {
          console.log(error);
          alert('Greska');
        }
      );
  }

  cancelPayment(){
    window.location.href = this.agencyFrontURL + "/payment-canceled"
  }

}
