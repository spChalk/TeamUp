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
import {AuthenticationService} from "../authentication";
import {NetworkEntity} from "../account/network-entity";

@Component({
  selector: 'app-network',
  templateUrl: './network.component.html',
  styleUrls: ['./network.component.css']
})
export class NetworkComponent implements OnInit {

  public account: Account;
  public accounts: NetworkEntity[];

  constructor(private accountService: AccountService,
              private bioService: BioService,
              private route: ActivatedRoute,
              private authenticationService : AuthenticationService ) {
  }

  ngOnInit(): void {
    let email = this.authenticationService.getCurrentUser();
    this.accountService.fetchUser(email).subscribe(
      (response: Account)=> {
        this.account = new Account(response);
        this.accounts = this.account.network;
      }
    );
  }

  public onSearch(form: NgForm): void {

    this.accountService.getAccountsBySimilarName(form.value.keyword).subscribe(
      (response: Account[]) => {
        this.accounts = response;
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
