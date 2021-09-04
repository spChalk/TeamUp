import { Component, OnInit } from '@angular/core';
import {Account} from "./account";
import {AccountService} from "./account.service";
import {first} from "rxjs/operators";

@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.css']
})
export class AccountComponent implements OnInit {

  loading = false;
  users: Account[];

  constructor(private userService: AccountService) { }

  ngOnInit() {
    this.loading = true;
    this.userService.getAllAccounts().pipe(first()).subscribe(users => {
      this.loading = false;
      this.users = users;
    });
  }
}
