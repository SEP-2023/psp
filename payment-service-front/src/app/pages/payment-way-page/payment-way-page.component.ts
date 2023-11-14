import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-payment-way-page',
  templateUrl: './payment-way-page.component.html',
  styleUrls: ['./payment-way-page.component.css']
})
export class PaymentWayPageComponent implements OnInit {
  chosen:string='Card'

  constructor() { }

  ngOnInit(): void {
  }

  redirect(){
    console.log('radi')
  }

}
