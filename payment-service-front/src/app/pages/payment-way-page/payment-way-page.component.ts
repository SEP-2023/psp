import { Component, OnInit } from '@angular/core';
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

  createPaypalPayment(){
    this.paypalService
      .createPayment("20")
      .subscribe(
        (data) => {
          alert('OK'), (window.location.href = data.url);
        },
        (error) => {
          alert('Greska');
        }
      );
  }

  confirm(){
    this.paypalService
      .confirm()
      .subscribe(
        (data) => {
          alert('OK'), (window.location.href = data.url);
        },
        (error) => {
          alert('Greska');
        }
      );
  }

}
