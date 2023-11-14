import { NgModule } from '@angular/core';
import {RouterModule, Routes} from "@angular/router";
import {PaymentWayPageComponent} from "./pages/payment-way-page/payment-way-page.component";
import {ConfirmPaymentPageComponent} from "./pages/confirm-payment-page/confirm-payment-page.component";


const routes: Routes = [
  { path: '', component: PaymentWayPageComponent },
  { path: 'confirm-payment', component: ConfirmPaymentPageComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
