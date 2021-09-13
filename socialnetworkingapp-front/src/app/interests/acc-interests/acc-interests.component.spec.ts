import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AccInterestsComponent } from './acc-interests.component';

describe('AccInterestsComponent', () => {
  let component: AccInterestsComponent;
  let fixture: ComponentFixture<AccInterestsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AccInterestsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AccInterestsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
