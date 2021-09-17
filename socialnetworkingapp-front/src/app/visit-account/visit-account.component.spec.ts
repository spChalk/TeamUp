import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VisitAccountComponent } from './visit-account.component';

describe('VisitAccountComponent', () => {
  let component: VisitAccountComponent;
  let fixture: ComponentFixture<VisitAccountComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ VisitAccountComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(VisitAccountComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
