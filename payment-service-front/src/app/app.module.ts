import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { PaymentWayPageComponent } from './pages/payment-way-page/payment-way-page.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import {RouterModule} from "@angular/router";
import {MatIconModule} from "@angular/material/icon";
import {MatButtonModule} from "@angular/material/button";
import {MatCardModule} from "@angular/material/card";
<<<<<<< HEAD
import {HttpClient, HttpClientModule} from "@angular/common/http";
import { ConfirmPaymentPageComponent } from './pages/confirm-payment-page/confirm-payment-page.component';
=======
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {FormsModule} from "@angular/forms";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {HttpClient, HttpClientModule} from "@angular/common/http";
import { ConfirmPaymentPageComponent } from './pages/confirm-payment-page/confirm-payment-page.component';
import { PaymentSuccessComponent } from './components/payment-success/payment-success.component';
import { PaymentCanceledComponent } from './components/payment-canceled/payment-canceled.component';
>>>>>>> 8a3d6bf8d2117dea04e4ebc52f02db3427758930

@NgModule({
  declarations: [
    AppComponent,
    PaymentWayPageComponent,
    NavbarComponent,
<<<<<<< HEAD
    ConfirmPaymentPageComponent
=======
    ConfirmPaymentPageComponent,
    PaymentSuccessComponent,
    PaymentCanceledComponent
>>>>>>> 8a3d6bf8d2117dea04e4ebc52f02db3427758930
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    RouterModule,
    MatIconModule,
    MatButtonModule,
    MatCardModule,
<<<<<<< HEAD
=======
    MatFormFieldModule,
    MatInputModule,
    FormsModule,
    BrowserAnimationsModule,
>>>>>>> 8a3d6bf8d2117dea04e4ebc52f02db3427758930
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
