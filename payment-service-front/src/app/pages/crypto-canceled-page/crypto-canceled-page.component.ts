import { Component, OnInit } from '@angular/core';
import {AgencyService} from "../../service/agency.service";
import {ActivatedRoute} from "@angular/router";
import {CryptoService} from "../../service/crypto.service";

@Component({
  selector: 'app-crypto-canceled-page',
  templateUrl: './crypto-canceled-page.component.html',
  styleUrls: ['./crypto-canceled-page.component.css']
})
export class CryptoCanceledPageComponent implements OnInit {

  constructor(private agencyService: AgencyService,private cryptoService: CryptoService, private route: ActivatedRoute) { }

  public transactionId: string = '';

  ngOnInit(): void {

    this.route.queryParams.subscribe(params => {
      this.transactionId = params['transactionId'];

      console.log('Received parameters:', this.transactionId);
      this.cryptoService.cancel(this.transactionId).subscribe(
        (data) => {
                console.log(data)
              },
              (error) => {
                console.log(error)
              }
      );
      // this.agencyService
      //   .cancel(this.transactionId)
      //   .subscribe(
      //     (data) => {
      //       console.log(data)
      //     },
      //     (error) => {
      //       console.log(error)
      //     }
      //   );
    });

  }

}
