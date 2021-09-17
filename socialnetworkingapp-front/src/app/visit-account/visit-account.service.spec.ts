import { TestBed } from '@angular/core/testing';

import { VisitAccountService } from './visit-account.service';

describe('VisitAccountService', () => {
  let service: VisitAccountService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(VisitAccountService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
