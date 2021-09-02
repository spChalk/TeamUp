import { Component, OnInit } from '@angular/core';
import {NgForm} from "@angular/forms";
import {AccountService} from "../account/account.service";
import {HttpErrorResponse} from "@angular/common/http";
import {Account} from "../account/account";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  constructor(private accountService: AccountService){}

  ngOnInit(): void {
  }

  public onRegister(regForm: NgForm): void {

    if (regForm.value.password !== regForm.value.conf_password) {
      alert("Passwords do not match!");
      return;
    }

    this.accountService.registerAccount(regForm.value).subscribe(
      (response: Account) => {
        console.log(response);
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

}
