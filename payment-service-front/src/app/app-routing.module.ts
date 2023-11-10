import { NgModule } from '@angular/core';
import {RouterModule, Routes} from "@angular/router";
import {PaymentWayPageComponent} from "./pages/payment-way-page/payment-way-page.component";


const routes: Routes = [
  { path: '', component: PaymentWayPageComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
