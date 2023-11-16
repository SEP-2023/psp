import { Component, OnInit } from '@angular/core';
import {environment} from "../../../environments/environment";
import { PaypalService } from 'src/app/service/paypal.service';
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-payment-way-page',
  templateUrl: './payment-way-page.component.html',
  styleUrls: ['./payment-way-page.component.css']
})
export class PaymentWayPageComponent implements OnInit {

  constructor(private paypalService: PaypalService, private route: ActivatedRoute) { }

  private price!: string;
  private transactionId!: string;
  private agencyId!: string;

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.price = params['price'];
      this.transactionId = params['transactionId'];
      this.agencyId = params['agencyId'];

      console.log('Received parameters:', this.price);
    });
  }

  redirect(){
    console.log('radi')
  }

  redirectToBank() {
    window.location.href = environment.bank_front_url;
  }

  createPaypalPayment(){
    this.paypalService
      .createPayment(this.price, this.transactionId, this.agencyId)
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
