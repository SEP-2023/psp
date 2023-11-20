import { Component, OnInit } from '@angular/core';
import {environment} from "../../../environments/environment";
import { PaypalService } from 'src/app/service/paypal.service';
import {ActivatedRoute} from "@angular/router";
import {BankService} from "../../service/bank.service";

@Component({
  selector: 'app-payment-way-page',
  templateUrl: './payment-way-page.component.html',
  styleUrls: ['./payment-way-page.component.css']
})
export class PaymentWayPageComponent implements OnInit {

  constructor(private paypalService: PaypalService, private bankService: BankService, private route: ActivatedRoute) { }

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
    this.bankService
      .getPaymentUrl(this.price, this.transactionId, this.agencyId)
      .subscribe(
        (data) => {
          console.log(data)
          window.location.href = data.paymentUrl;
        },
        (error) => {
          console.log(error);
          alert('Greska');
        }
      );
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
