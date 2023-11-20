import { Component, OnInit } from '@angular/core';
import {PaypalService} from "../../service/paypal.service";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-confirm-subscription',
  templateUrl: './confirm-subscription.component.html',
  styleUrls: ['./confirm-subscription.component.css']
})
export class ConfirmSubscriptionComponent implements OnInit {

  token!: string;
  private price!: string;
  private transactionId!: string;
  private agencyId!: string;

  constructor(private paypalService: PaypalService, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.token = params['token'];
      this.price = params['amount'];
      this.transactionId = params['transactionId'];
      this.agencyId = params['agencyId'];
    });
  }

  confirmSubscription(){
    this.paypalService
      .confirmSubscription(this.token)
      .subscribe(
        (data) => {
          console.log(data)
          if (data) {
            window.location.href = "http://localhost:4200/success?transactionId="+ this.transactionId
          }
        },
        (error) => {
          console.log(error);
          alert('Greska');
        }
      );
  }

}
