import { TestBed } from '@angular/core/testing';

import { PostViewService } from './post-view.service';

describe('PostViewService', () => {
  let service: PostViewService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PostViewService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
