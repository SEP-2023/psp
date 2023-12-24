import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConfirmPaymentPageComponent } from './confirm-payment-page.component';

describe('ConfirmPaymentPageComponent', () => {
  let component: ConfirmPaymentPageComponent;
  let fixture: ComponentFixture<ConfirmPaymentPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ConfirmPaymentPageComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ConfirmPaymentPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
