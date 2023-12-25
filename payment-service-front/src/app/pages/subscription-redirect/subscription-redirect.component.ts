import { Component, OnInit } from '@angular/core';
import {PaypalService} from "../../service/paypal.service";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-subscription-redirect',
  templateUrl: './subscription-redirect.component.html',
  styleUrls: ['./subscription-redirect.component.css']
})
export class SubscriptionRedirectComponent implements OnInit {

  private price!: string;
  private frequency!: string;
  private transactionId!: string;
  private agencyId!: string;

  constructor(private paypalService: PaypalService, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.price = params['price'];
      this.frequency = params['frequency'];
      this.transactionId = params['transactionId'];
      this.agencyId = params['agencyId'];
      console.log("tuu")
      this.createSubscription();
    });
  }

  createSubscription(){
    this.paypalService
      .createSubscription(this.price, this.frequency, this.transactionId, this.agencyId)
      .subscribe(
        (data) => {
          console.log(data)
          window.location.href = data.redirectUrl;
        },
        (error) => {
          console.log(error);
          alert('Greska');
        }
      );
  }



}
