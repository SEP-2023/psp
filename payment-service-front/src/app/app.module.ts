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
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {FormsModule} from "@angular/forms";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {HttpClient, HttpClientModule} from "@angular/common/http";
import { ConfirmPaymentPageComponent } from './pages/confirm-payment-page/confirm-payment-page.component';
import { PaymentSuccessComponent } from './components/payment-success/payment-success.component';
import { PaymentCanceledComponent } from './components/payment-canceled/payment-canceled.component';

@NgModule({
  declarations: [
    AppComponent,
    PaymentWayPageComponent,
    NavbarComponent,
    ConfirmPaymentPageComponent,
    PaymentSuccessComponent,
    PaymentCanceledComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    RouterModule,
    MatIconModule,
    MatButtonModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    FormsModule,
    BrowserAnimationsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
