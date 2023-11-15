import { Component, OnInit } from '@angular/core';
import {environment} from "../../../environments/environment";

@Component({
  selector: 'app-payment-way-page',
  templateUrl: './payment-way-page.component.html',
  styleUrls: ['./payment-way-page.component.css']
})
export class PaymentWayPageComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

  redirect(){
    console.log('radi')
  }

  redirectToBank(){
    window.location.href = environment.bank_front_url;
  }

}
