import { NgModule } from '@angular/core';
import {RouterModule, Routes} from "@angular/router";
import {PaymentWayPageComponent} from "./pages/payment-way-page/payment-way-page.component";
import {ConfirmPaymentPageComponent} from "./pages/confirm-payment-page/confirm-payment-page.component";
import {PaymentSuccessComponent} from "./components/payment-success/payment-success.component";
import {PaymentCanceledComponent} from "./components/payment-canceled/payment-canceled.component";
import {PaymentErrorComponent} from "./components/payment-error/payment-error.component";
import {PaymentFailedComponent} from "./components/payment-failed/payment-failed.component";


const routes: Routes = [
  { path: '', component: PaymentWayPageComponent },
  { path: 'confirm-payment', component: ConfirmPaymentPageComponent },
  { path: 'success', component: PaymentSuccessComponent },
  { path: 'canceled', component: PaymentCanceledComponent },
  { path: 'error', component: PaymentErrorComponent },
  { path: 'failed', component: PaymentFailedComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
