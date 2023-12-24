import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CryptoSuccessPageComponent } from './crypto-success-page.component';

describe('CryptoSuccessPageComponent', () => {
  let component: CryptoSuccessPageComponent;
  let fixture: ComponentFixture<CryptoSuccessPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CryptoSuccessPageComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CryptoSuccessPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
