import { Component, OnInit } from '@angular/core';
import {PaypalService} from "../../service/paypal.service";
import {SubscriptionService} from "../../service/subscription.service";
import {environment} from "../../../environments/environment";

@Component({
  selector: 'app-subscription',
  templateUrl: './subscription.component.html',
  styleUrls: ['./subscription.component.css']
})
export class SubscriptionComponent implements OnInit {

  paypalSubscribed!: boolean;
  bitcoinSubscribed!: boolean;
  cardSubscribed!: boolean;
  qrSubscribed!: boolean;
  constructor(private subscriptionService: SubscriptionService) {
    this.paypalSubscribed = false;
    this.qrSubscribed = false;
    this.bitcoinSubscribed = false;
    this.cardSubscribed = false;
  }

  ngOnInit(): void {
    this.subscriptionService
      .getPaymentMethods()
      .subscribe(
        (data) => {
          console.log(data)
          this.qrSubscribed = data.some((item: { paymentMethod: string; }) => item.paymentMethod === 'qr');
          this.paypalSubscribed = data.some((item: { paymentMethod: string; }) => item.paymentMethod === 'paypal');
          this.cardSubscribed = data.some((item: { paymentMethod: string; }) => item.paymentMethod === 'card');
          this.bitcoinSubscribed = data.some((item: { paymentMethod: string; }) => item.paymentMethod === 'bitcoin');
          console.log(this.bitcoinSubscribed)
        },
        (error) => {
          console.log(error);
          alert('Greska');
        }
      );
  }

  redirectToPayment() {
    window.location.href = environment.psp_front_url;
  }

  subscribe(method: string) {
    this.subscriptionService
      .subscribe(method)
      .subscribe(
        (data) => {
          console.log(data)
        },
        (error) => {
          console.log(error);
          alert('Greska');
        }
      );
    if (method == "card"){
      this.cardSubscribed = true;
    }else if (method == "qr"){
      this.qrSubscribed = true;
    }else if (method == "bitcoin"){
      this.bitcoinSubscribed = true;
    }else if (method == "paypal"){
      this.paypalSubscribed = true;
    }
  }
}
