import { ComponentFixture, TestBed } from '@angular/core/testing';

import { JobInterestsComponent } from './job-interests.component';

describe('JobInterestsComponent', () => {
  let component: JobInterestsComponent;
  let fixture: ComponentFixture<JobInterestsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ JobInterestsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(JobInterestsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
