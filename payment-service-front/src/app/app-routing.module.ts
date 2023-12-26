import { NgModule } from '@angular/core';
import {RouterModule, Routes} from "@angular/router";
import {PaymentWayPageComponent} from "./pages/payment-way-page/payment-way-page.component";
import {ConfirmPaymentPageComponent} from "./pages/confirm-payment-page/confirm-payment-page.component";
import {PaymentSuccessComponent} from "./components/payment-success/payment-success.component";
import {PaymentCanceledComponent} from "./components/payment-canceled/payment-canceled.component";
import {SubscriptionRedirectComponent} from "./pages/subscription-redirect/subscription-redirect.component";
import {ConfirmSubscriptionComponent} from "./pages/confirm-subscription/confirm-subscription.component";
import {CryptoCanceledPageComponent} from "./pages/crypto-canceled-page/crypto-canceled-page.component";
import {CryptoSuccessPageComponent} from "./pages/crypto-success-page/crypto-success-page.component";
import {SubscriptionComponent} from "./pages/subscription/subscription.component";
import {PaymentErrorComponent} from "./components/payment-error/payment-error.component";
import {PaymentFailedComponent} from "./components/payment-failed/payment-failed.component";
import {ListSubscriptionsComponent} from "./pages/list-subscriptions/list-subscriptions.component";
import {LoginRegistrationComponent} from "./pages/login-registration/login-registration.component";


const routes: Routes = [
  { path: '', component: PaymentWayPageComponent },
  { path: 'confirm-payment', component: ConfirmPaymentPageComponent },
  { path: 'success', component: PaymentSuccessComponent },
  { path: 'canceled', component: PaymentCanceledComponent },
  { path: 'subscription-redirect', component: SubscriptionRedirectComponent },
  { path: 'confirm-subscription', component: ConfirmSubscriptionComponent },
  { path: 'cancel-crypto', component: CryptoCanceledPageComponent },
  { path: 'successful-crypto', component: CryptoSuccessPageComponent },
  { path: 'subscription', component: SubscriptionComponent },
  { path: 'error', component: PaymentErrorComponent },
  { path: 'failed', component: PaymentFailedComponent },
  { path: 'login', component: LoginRegistrationComponent },
  { path: 'list-subscriptions', component: ListSubscriptionsComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
