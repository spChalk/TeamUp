import { Component, OnInit } from '@angular/core';
import {NgForm} from "@angular/forms";
import {Account} from "../account/account";
import {HttpErrorResponse} from "@angular/common/http";
import {AccountService} from "../account/account.service";
import {Login} from "../login/login";
import {Router} from "@angular/router";

@Component({
  selector: 'app-welcome',
  templateUrl: './welcome.component.html',
  styleUrls: ['./welcome.component.css']
})
export class WelcomeComponent implements OnInit {

  constructor(private accountService: AccountService,
              private router: Router){}

  ngOnInit(): void {
  }

  public onLogin(regForm: NgForm): void {
    this.accountService.logIn(regForm.value).subscribe(
      (response: Login) => {
        console.log(response);
        this.router.navigateByUrl('/home');
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }
}
