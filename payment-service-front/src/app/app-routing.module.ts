import { NgModule } from '@angular/core';
import {RouterModule, Routes} from "@angular/router";
import {PaymentWayPageComponent} from "./pages/payment-way-page/payment-way-page.component";
import {ConfirmPaymentPageComponent} from "./pages/confirm-payment-page/confirm-payment-page.component";
import {PaymentSuccessComponent} from "./components/payment-success/payment-success.component";
import {PaymentCanceledComponent} from "./components/payment-canceled/payment-canceled.component";
import {CryptoCanceledPageComponent} from "./pages/crypto-canceled-page/crypto-canceled-page.component";
import {CryptoSuccessPageComponent} from "./pages/crypto-success-page/crypto-success-page.component";
import {SubscriptionComponent} from "./pages/subscription/subscription.component";


const routes: Routes = [
  { path: '', component: PaymentWayPageComponent },
  { path: 'confirm-payment', component: ConfirmPaymentPageComponent },
  { path: 'success', component: PaymentSuccessComponent },
  { path: 'canceled', component: PaymentCanceledComponent },
  { path: 'cancel-crypto', component: CryptoCanceledPageComponent },
  { path: 'successful-crypto', component: CryptoSuccessPageComponent },
  { path: 'subscription', component: SubscriptionComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
