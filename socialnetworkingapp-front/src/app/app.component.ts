import {Component, OnInit} from '@angular/core';
import {HttpErrorResponse} from '@angular/common/http';
import {Account} from "./account/account";
import {AccountService} from "./account/account.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent /*implements OnInit*/ {

 /* public accounts: Account[];

  constructor(private accountService: AccountService) {
    this.accounts = [];
  }

  ngOnInit() {
    this.getAccounts();
  }*/

  /*public getAccounts(): void {
    this.accountService.getAllAccounts().subscribe(
      (response: Account[]) => {
        this.accounts = response;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }
*/
  /*public onOpenModal(account: Account, mode: string): void {
    const container = document.getElementById('main-container')
    const button = document.createElement('button');
    button.type = 'button';
    button.style.display = 'none';
    button.setAttribute('data-toggle', 'modal');

    if(mode === 'add') {
      button.setAttribute('data-target', '#addAccountModal');
    }
    if(mode === 'edit') {
      button.setAttribute('data-target', '#updateAccountModal');
    }
    if(mode === 'delete') {
      button.setAttribute('data-target', '#deleteAccountModal');
    }
    container.appendChild(button);
    button.click();
  }*/
}
