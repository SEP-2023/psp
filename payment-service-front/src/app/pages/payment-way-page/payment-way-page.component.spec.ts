import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PaymentWayPageComponent } from './payment-way-page.component';

describe('PaymentWayPageComponent', () => {
  let component: PaymentWayPageComponent;
  let fixture: ComponentFixture<PaymentWayPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PaymentWayPageComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PaymentWayPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
