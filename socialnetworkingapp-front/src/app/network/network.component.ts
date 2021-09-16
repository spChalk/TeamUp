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

@Component({
  selector: 'app-network',
  templateUrl: './network.component.html',
  styleUrls: ['./network.component.css']
})
export class NetworkComponent implements OnInit {

  public accounts: Account[];
  private email: string;

  constructor(private accountService: AccountService,
              private bioService: BioService,
              private route: ActivatedRoute,
              private authenticationService : AuthenticationService ) {
  }

  ngOnInit(): void {
    this.email = this.authenticationService.getCurrentUser();
    this.getNetwork(this.email);
  }

  public getNetwork(email: string): void {
    this.accountService.getNetworkByEmail(email).subscribe(
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

    this.accountService.getAccountsBySimilarName(form.value.keyword).subscribe(
      (response: Account[]) => {
        console.log(response);
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
