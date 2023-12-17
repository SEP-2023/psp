import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CryptoCanceledPageComponent } from './crypto-canceled-page.component';

describe('CryptoCanceledPageComponent', () => {
  let component: CryptoCanceledPageComponent;
  let fixture: ComponentFixture<CryptoCanceledPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CryptoCanceledPageComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CryptoCanceledPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
