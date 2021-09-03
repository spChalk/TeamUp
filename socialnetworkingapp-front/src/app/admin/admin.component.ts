import { Component, OnInit } from '@angular/core';
import {Account} from "../account/account";
import {AccountService} from "../account/account.service";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {

  public accounts: Account[] = [];

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

    if(mode === 'info') {
      button.setAttribute('data-target', '#info');
    }
    if(mode === 'edit') {
      button.setAttribute('data-target', '#edit');
    }
    if(mode === 'remove') {
      button.setAttribute('data-target', '#remove');
    }
    if(container != null) {
      container.appendChild(button);
      button.click();
    }
  }
}
