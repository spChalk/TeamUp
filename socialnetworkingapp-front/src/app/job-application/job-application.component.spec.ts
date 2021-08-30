import { ComponentFixture, TestBed } from '@angular/core/testing';

import { JobApplicationComponent } from './job-application.component';

describe('JobApplicationComponent', () => {
  let component: JobApplicationComponent;
  let fixture: ComponentFixture<JobApplicationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ JobApplicationComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(JobApplicationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
