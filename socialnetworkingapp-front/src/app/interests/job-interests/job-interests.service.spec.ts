import { TestBed } from '@angular/core/testing';

import { JobInterestsService } from './job-interests.service';

describe('JobInterestsService', () => {
  let service: JobInterestsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(JobInterestsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
