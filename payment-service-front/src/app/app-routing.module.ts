import { NgModule } from '@angular/core';
import {RouterModule, Routes} from "@angular/router";
import {PaymentWayPageComponent} from "./pages/payment-way-page/payment-way-page.component";
import {ConfirmPaymentPageComponent} from "./pages/confirm-payment-page/confirm-payment-page.component";
<<<<<<< HEAD
=======
import {PaymentSuccessComponent} from "./components/payment-success/payment-success.component";
import {PaymentCanceledComponent} from "./components/payment-canceled/payment-canceled.component";
>>>>>>> 8a3d6bf8d2117dea04e4ebc52f02db3427758930


const routes: Routes = [
  { path: '', component: PaymentWayPageComponent },
  { path: 'confirm-payment', component: ConfirmPaymentPageComponent },
<<<<<<< HEAD
=======
  { path: 'success', component: PaymentSuccessComponent },
  { path: 'canceled', component: PaymentCanceledComponent },
>>>>>>> 8a3d6bf8d2117dea04e4ebc52f02db3427758930
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
