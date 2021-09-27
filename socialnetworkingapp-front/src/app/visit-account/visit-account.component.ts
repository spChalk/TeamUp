import { Component, OnInit } from '@angular/core';
import {AuthenticationService} from "../authentication";
import {Account} from "../account/account";
import {AccountService} from "../account/account.service";
import {ActivatedRoute, Router} from "@angular/router";
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {ConnectionRequestService} from "../connection-request/connection-request.service";
import {concat} from "rxjs";
import {ConnectionRequest} from "../connection-request/ConnectionRequest";

@Component({
  selector: 'app-visit-account',
  templateUrl: './visit-account.component.html',
  styleUrls: ['./visit-account.component.css']
})
export class VisitAccountComponent implements OnInit {

  public myAccount: Account;
  public vAccount: Account;
  public isPresentInNetwork: boolean = false;
  public pendingRequestId: number = -1;
  public receivedRequestId: number = -1;

  constructor(public authenticationService : AuthenticationService,
              private accountService: AccountService,
              private connectionRequestService: ConnectionRequestService,
              private router: Router,
              private route: ActivatedRoute) { }

  public getRequestedUser(email: string) {
    this.accountService.getAccountByEmail(email).subscribe(
      (resp: Account) => {
        this.vAccount = new Account(resp);
        /* If requested user is in the current user's network, all info is public */
        for(let entity of this.vAccount.network) {
          if (entity.firstName === this.myAccount.firstName &&
            entity.lastName === this.myAccount.lastName &&
            entity.email === this.myAccount.email &&
            entity.imageUrl === this.myAccount.imageUrl) {
            this.isPresentInNetwork = true;
            break;
          }
        }
        /* If the current auth user is not in visited user's network,
        *  check if there's a pending request */
        if(this.isPresentInNetwork === false) {
          this.connectionRequestService.checkForPendingRequest(this.vAccount.email).subscribe(
            (requestId: number) => {
              if(requestId !== null) {
                this.pendingRequestId = requestId;
              }},
            (fail: HttpErrorResponse) => {
              alert(fail.message);
            });

          this.connectionRequestService.checkIfHasReceivedRequest(this.vAccount.email).subscribe(
            (requestId: number) => {
              if(requestId !== null) {
                this.receivedRequestId = requestId;
              }},
            (fail: HttpErrorResponse) => {
              alert(fail.message);
            });
        }
      }, (err: HttpErrorResponse) => {
        alert(err.message);
      });
  }

  public fetchUser() {
    /* Fetch the email of current user */
    let email = this.authenticationService.getCurrentUser();
    this.accountService.fetchUser(email).subscribe(
      (response: Account)=> {
        this.myAccount = new Account(response);
        /* If the url's email is equal to current user's, redirect to '/account' */
        this.route.params.subscribe(params => {
          if (params['email']) {
            if(params['email'] === email) {
              this.router.navigateByUrl("/account");
            } else {
              /* Else, get requested user */
              this.getRequestedUser(params['email']);
            }}
        });
      });
  }

  ngOnInit(): void {
    this.fetchUser();
  }

  public onConnect() {
    this.connectionRequestService.addRequest(this.vAccount.email).subscribe(
      (response: ConnectionRequest) => {
        this.fetchUser();
      }, (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public onDisconnect() {
    this.connectionRequestService.deleteFromNetwork(this.vAccount.email).subscribe(
      (status: any) => {
        window.location.reload();
      }, (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public onRemoveRequest(reqId: number) {
    this.connectionRequestService.removeRequest(reqId).subscribe(
      (status: any) => {
        window.location.reload();
      }, (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public onAcceptRequest(reqId: number) {
    this.connectionRequestService.acceptRequest(reqId).subscribe(
      (status: any) => {
        window.location.reload();
      }, (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }
}
