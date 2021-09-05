import { Component, OnInit } from '@angular/core';
import {Account} from "../account/account";
import {AccountService} from "../account/account.service";
import {HttpErrorResponse} from "@angular/common/http";
import {NgForm} from "@angular/forms";

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {

  public accounts: Account[];
  public deleteAccount: Account;
  public editAccount: Account;
  public infoAccount: Account;

  constructor(private accountService: AccountService) {}

  ngOnInit() {
    this.getAccounts();
  }

  public getAccounts(): void {
    this.accountService.getAllAccounts().subscribe(
      (response: Account[]) => {
        this.accounts = response;
        console.log(this.accounts);
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public onClickModal(account: Account, mode: string): void {

    const container = document.getElementById('main-container');

    const button = document.createElement('button');
    button.type = 'button';
    button.style.display = 'none';
    button.setAttribute('data-toggle', 'modal');

    if(mode === 'add') {
      button.setAttribute('data-target', '#addAccount');
    }
    if(mode === 'info') {
      this.infoAccount = account;
      button.setAttribute('data-target', '#info');
    }
    if(mode === 'edit') {
      this.editAccount = account;
      button.setAttribute('data-target', '#edit');
    }
    if(mode === 'remove') {
      this.deleteAccount = account;
      button.setAttribute('data-target', '#remove');
    }
    if(container != null) {
      container.appendChild(button);
      button.click();
    }
  }

  public onSearch(form: NgForm): void {

    this.accountService.getAccountByEmail(form.value.email).subscribe(
      (response: Account) => {
        console.log(response);
        this.accounts = [response];
      },
      (error: HttpErrorResponse) => {
        if(error.status === 500) {
          alert("No user found!");
        }
      }
    );
  }

  public onInfo(accountId: number): void {

    this.accountService.getAccountById(accountId).subscribe(
      (response: Account) => {
        console.log(response);
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public onUpdateAccount(account: Account): void {

    this.accountService.updateAccount(account).subscribe(
      (response: Account) => {
        console.log(response);
        this.getAccounts();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public onDeleteAccount(accountId: number): void {

    this.accountService.deleteAccountById(accountId).subscribe(
      (response: void) => {
        this.getAccounts();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public onAddAccount(account: Account): void {

    this.accountService.registerAccount(account).subscribe(
      (response: Account) => {
        console.log(response);
        this.getAccounts();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }
}
