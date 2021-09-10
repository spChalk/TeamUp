import { Component, OnInit } from '@angular/core';
import {Account} from "../account/account";
import {AccountService} from "../account/account.service";
import {BioService} from "../bio/bio.service";
import {ActivatedRoute} from "@angular/router";
import {Bio} from "../bio/bio";
import {HttpErrorResponse} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {Observable, Subject} from "rxjs";
import {debounceTime, distinctUntilChanged, repeat, switchMap} from "rxjs/operators";
import {NgForm} from "@angular/forms";

@Component({
  selector: 'app-network',
  templateUrl: './network.component.html',
  styleUrls: ['./network.component.css']
})
export class NetworkComponent implements OnInit {

  public accounts: Account[];

  constructor(private accountService: AccountService,
              private bioService: BioService,
              private route: ActivatedRoute) {
    this.route.params.subscribe(params => {
      console.log(params);
      if (params['uid']) {
        this.getNetwork(params['uid']);
      }
    });
  }

  ngOnInit(): void {}

  public getNetwork(uid: number): void {
    this.accountService.getNetworkById(uid).subscribe(
      (response: Account[]) => {
        this.accounts = response;
        console.log(this.accounts);
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
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

  refresh(): void {
    window.location.reload();
  }
}
