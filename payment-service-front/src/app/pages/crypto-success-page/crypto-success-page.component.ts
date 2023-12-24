import { Component, OnInit } from '@angular/core';
import {AgencyService} from "../../service/agency.service";
import {ActivatedRoute} from "@angular/router";
import {CryptoService} from "../../service/crypto.service";

@Component({
  selector: 'app-crypto-success-page',
  templateUrl: './crypto-success-page.component.html',
  styleUrls: ['./crypto-success-page.component.css']
})
export class CryptoSuccessPageComponent implements OnInit {

  constructor(private agencyService: AgencyService,private cryptoService: CryptoService, private route: ActivatedRoute) { }

  public transactionId: string = '';

  ngOnInit(): void {

    this.route.queryParams.subscribe(params => {
      this.transactionId = params['transactionId'];

      console.log('Received parameters:', this.transactionId);
      this.cryptoService.confirm(this.transactionId).subscribe(
        (data) => {
          console.log(data)
        },
        (error) => {
          console.log(error)
        }
      );
      // this.agencyService
      //   .confirm(this.transactionId)
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
