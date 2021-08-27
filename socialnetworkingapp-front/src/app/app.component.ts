import {Component, OnInit} from '@angular/core';
import {HttpErrorResponse} from '@angular/common/http';
import {Account} from "./account";
import {AccountService} from "./account.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  public accounts: Account[] | undefined;

  constructor(private accountService: AccountService) {}

  ngOnInit() {
    this.getAccounts();
  }

  public getAccounts(): void {
    this.accountService.getAllAccounts().subscribe(
      (response: Account[]) => {
        this.accounts = response;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

}
