import { Component, OnInit } from '@angular/core';
import {Account} from "./account";
import {AccountService} from "./account.service";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.css']
})
export class AccountComponent implements OnInit {

  private account: Account;

  constructor(private accountService: AccountService) { }

  ngOnInit(): void {}

  public getAccountDetails(accountId: number): void {
    this.accountService.getAccountById(accountId).subscribe(
      (response: Account) => {
        this.account = response;
        console.log(this.account);
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }
}
