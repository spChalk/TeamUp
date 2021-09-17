import { Component, OnInit } from '@angular/core';
import {AuthenticationService} from "../authentication";
import {Account} from "../account/account";
import {Bio} from "../bio/bio";
import {AccountService} from "../account/account.service";
import {ActivatedRoute, Router} from "@angular/router";
import {HttpClient, HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'app-visit-account',
  templateUrl: './visit-account.component.html',
  styleUrls: ['./visit-account.component.css']
})
export class VisitAccountComponent implements OnInit {

  public myAccount: Account;
  public vAccount: Account;
  public isPresentInNetwork: boolean;

  constructor(private authenticationService : AuthenticationService,
              private accountService: AccountService,
              private router: Router,
              private route: ActivatedRoute) { }

  ngOnInit(): void {
    /* Fetch the email of current user */
    let email = this.authenticationService.getCurrentUser();
    this.accountService.fetchUser(email).subscribe(
      (response: Account)=> {
        this.myAccount = new Account(response);
        /* The url's email is equal to current user's, redirect to '/account' */
        this.route.params.subscribe(params => {
          if (params['email']) {
            if(params['email'] === email) {
              this.router.navigateByUrl("/account");
            } else {
              /* Get requested user */
              this.accountService.getAccountByEmail(params['email']).subscribe(
                (resp: Account) => {
                  this.vAccount = new Account(resp);
                    /* If requested user is in the current user's network, all info is public */
                    if (this.vAccount.network.includes(this.myAccount)) {
                      this.isPresentInNetwork = true;
                    /* else, the hidden info remains hidden */
                    } else {
                      this.isPresentInNetwork = false;
                    }
                }, (err: HttpErrorResponse) => {
                  alert(err.message);
                }
              );
            }
          }
        });
      }
    );
  }
}
