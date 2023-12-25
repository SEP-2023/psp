import { Component, OnInit } from '@angular/core';
import { environment } from '../../../environments/environment';
import { PaypalService } from 'src/app/service/paypal.service';
import { ActivatedRoute } from '@angular/router';
import { CryptoService } from '../../service/crypto.service';
import { SubscriptionService } from '../../service/subscription.service';
import { BankService } from '../../service/bank.service';

@Component({
  selector: 'app-payment-way-page',
  templateUrl: './payment-way-page.component.html',
  styleUrls: ['./payment-way-page.component.css'],
})
export class PaymentWayPageComponent implements OnInit {
  constructor(
    private paypalService: PaypalService,
    private cryptoService: CryptoService,
    private subscriptionService: SubscriptionService,
    private route: ActivatedRoute,
    private bankService: BankService
  ) {
    this.paypalSubscribed = false;
    this.qrSubscribed = false;
    this.bitcoinSubscribed = false;
    this.cardSubscribed = false;
  }

  private price!: string;
  private transactionId!: string;
  private agencyId!: string;
  paypalSubscribed!: boolean;
  bitcoinSubscribed!: boolean;
  cardSubscribed!: boolean;
  qrSubscribed!: boolean;

  ngOnInit(): void {
    this.route.queryParams.subscribe((params) => {
      this.price = params['price'];
      this.transactionId = params['transactionId'];
      this.agencyId = params['agencyId'];
      console.log('Received parameters:', this.price);
    });
    this.subscriptionService.getPaymentMethods(this.agencyId).subscribe(
      (data) => {
        console.log(data);
        this.qrSubscribed = data.some(
          (item: { paymentMethod: string }) => item.paymentMethod === 'qr'
        );
        this.paypalSubscribed = data.some(
          (item: { paymentMethod: string }) => item.paymentMethod === 'paypal'
        );
        this.cardSubscribed = data.some(
          (item: { paymentMethod: string }) => item.paymentMethod === 'card'
        );
        this.bitcoinSubscribed = data.some(
          (item: { paymentMethod: string }) => item.paymentMethod === 'bitcoin'
        );
        console.log(this.bitcoinSubscribed);
      },
      (error) => {
        console.log(error);
        alert('Greska');
      }
    );
  }

  redirect() {
    console.log('radi');
  }

  redirectToBank(qr: boolean) {
    this.bankService
      .getPaymentUrl(this.price, this.transactionId, this.agencyId, qr)
      .subscribe(
        (data) => {
          console.log(data);
          window.location.href = data.paymentUrl;
        },
        (error) => {
          console.log(error);
          alert('Greska');
        }
      );
  }
  redirectToSubscription() {
    window.location.href = `${environment.psp_front_url}/subscription?price=${this.price}&transactionId=${this.transactionId}&agencyId=${this.agencyId}`;
  }

  createPaypalPayment() {
    this.paypalService
      .createPayment(this.price, this.transactionId, this.agencyId)
      .subscribe(
        (data) => {
          console.log(data);
          window.location.href = data.redirectUrl;
        },
        (error) => {
          console.log(error);
          alert('Greska');
        }
      );
  }

  createCryptoOrder() {
    this.cryptoService
      .createPayment(this.price, this.transactionId, this.agencyId)
      .subscribe(
        (data) => {
          console.log(data);
          window.open(data.payment_url);
        },
        (error) => {
          console.log(error);
          alert('Greska');
        }
      );
  }
}
