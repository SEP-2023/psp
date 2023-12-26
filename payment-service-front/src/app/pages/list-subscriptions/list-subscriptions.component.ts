import { Component, OnInit } from '@angular/core';
import {PaypalService} from "../../service/paypal.service";
import {CryptoService} from "../../service/crypto.service";
import {SubscriptionService} from "../../service/subscription.service";
import {ActivatedRoute} from "@angular/router";
import {environment} from "../../../environments/environment";

@Component({
  selector: 'app-list-subscriptions',
  templateUrl: './list-subscriptions.component.html',
  styleUrls: ['./list-subscriptions.component.css']
})
export class ListSubscriptionsComponent implements OnInit {

  constructor(private paypalService: PaypalService,private cryptoService: CryptoService, private subscriptionService: SubscriptionService, private route: ActivatedRoute) {
    this.paypalSubscribed = false;
    this.qrSubscribed = false;
    this.bitcoinSubscribed = false;
    this.cardSubscribed = false;
  }

  private agencyId!: string;
  private token!: string;
  paypalSubscribed!: boolean;
  bitcoinSubscribed!: boolean;
  cardSubscribed!: boolean;
  qrSubscribed!: boolean;

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.token = params['token'];
      this.agencyId = params['agencyId'];
      console.log(this.token)
      console.log(this.agencyId)
    });
    this.subscriptionService
      .getPaymentMethods(this.agencyId)
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

  redirect(){
    console.log('radi')
  }

  redirectToSubscription() {
    window.location.href = `${environment.psp_front_url}/subscription?agencyId=${this.agencyId}&token=${this.token}`;
  }

  unsubscribe(method: string) {
    this.subscriptionService
      .unsubscribe(method, this.agencyId)
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
      this.cardSubscribed = false;
    }else if (method == "qr"){
      this.qrSubscribed = false;
    }else if (method == "bitcoin"){
      this.bitcoinSubscribed = false;
    }else if (method == "paypal"){
      this.paypalSubscribed = false;
    }
  }
}
