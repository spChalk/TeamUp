import {Component, OnInit} from '@angular/core';
import {HttpErrorResponse} from '@angular/common/http';
import {Account} from "./account/account";
import {AccountService} from "./account/account.service";
import {Router} from "@angular/router";
import {AuthService} from "./auth/auth.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  currentUser: Account;

  constructor(
    private router: Router,
    private authenticationService: AuthService
  ) {
    this.authenticationService.currentUser.subscribe(x => this.currentUser = x);
  }

  logout() {
    this.authenticationService.logout();
    this.router.navigate(['/welcome']);
  }
}
