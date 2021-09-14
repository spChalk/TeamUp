import { TestBed } from '@angular/core/testing';

import { AccInterestsService } from './acc-interests.service';

describe('AccInterestsService', () => {
  let service: AccInterestsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AccInterestsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
