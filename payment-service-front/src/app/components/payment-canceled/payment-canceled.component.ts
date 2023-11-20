import { Component, OnInit } from '@angular/core';
import {AgencyService} from "../../service/agency.service";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-payment-canceled',
  templateUrl: './payment-canceled.component.html',
  styleUrls: ['./payment-canceled.component.css']
})
export class PaymentCanceledComponent implements OnInit {

  constructor(private agencyService: AgencyService, private route: ActivatedRoute) { }

  private transactionId!: string;

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.transactionId = params['transactionId'];

      console.log('Received parameters:', this.transactionId);
      this.agencyService
        .cancel(this.transactionId)
        .subscribe(
          (data) => {
            console.log(data)
            setTimeout(() => {
              window.location.href = "http://localhost:4201";
            }, 2000);          },
          (error) => {
            console.log(error)
          }
        );
    });

  }
}
