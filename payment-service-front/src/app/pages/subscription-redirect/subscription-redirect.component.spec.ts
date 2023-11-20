import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SubscriptionRedirectComponent } from './subscription-redirect.component';

describe('SubscriptionRedirectComponent', () => {
  let component: SubscriptionRedirectComponent;
  let fixture: ComponentFixture<SubscriptionRedirectComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SubscriptionRedirectComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SubscriptionRedirectComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
