import { Component, OnInit } from '@angular/core';
import {environment} from "../../../environments/environment";
import { PaypalService } from 'src/app/service/paypal.service';

@Component({
  selector: 'app-payment-way-page',
  templateUrl: './payment-way-page.component.html',
  styleUrls: ['./payment-way-page.component.css']
})
export class PaymentWayPageComponent implements OnInit {

  constructor(private paypalService: PaypalService) { }

  ngOnInit(): void {
  }

  redirect(){
    console.log('radi')
  }

  redirectToBank() {
    window.location.href = environment.bank_front_url;
  }

  createPaypalPayment(){
    this.paypalService
      .createPayment("20")
      .subscribe(
        (data) => {
          console.log(data)
          alert('OK');
          window.location.href = data.redirectUrl
        },
        (error) => {
          console.log(error);
          alert('Greska');
        }
      );
  }

}
