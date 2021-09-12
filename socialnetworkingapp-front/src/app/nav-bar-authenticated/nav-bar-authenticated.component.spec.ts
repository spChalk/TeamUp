import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NavBarAuthenticatedComponent } from './nav-bar-authenticated.component';

describe('NavBarAuthenticatedComponent', () => {
  let component: NavBarAuthenticatedComponent;
  let fixture: ComponentFixture<NavBarAuthenticatedComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NavBarAuthenticatedComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NavBarAuthenticatedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
