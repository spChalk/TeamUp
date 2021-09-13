import { TestBed } from '@angular/core/testing';

import { JobViewService } from './job-view.service';

describe('JobViewService', () => {
  let service: JobViewService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(JobViewService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
