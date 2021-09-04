import { Component, OnInit } from '@angular/core';
import {NgForm} from "@angular/forms";
import {Account} from "../account/account";
import {HttpErrorResponse} from "@angular/common/http";
import {AccountService} from "../account/account.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor() {
  }

  ngOnInit(): void {
  }
}
