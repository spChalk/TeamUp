import { Component, OnInit } from '@angular/core';
import {Account} from "../account/account";
import {AccountService} from "../account/account.service";
import {BioService} from "../bio/bio.service";
import {ActivatedRoute} from "@angular/router";
import {HttpErrorResponse} from "@angular/common/http";
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
  public similarAccs: Account[] = [];

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

    this.accountService.getAllAccounts().subscribe(
      (response: Account[]) => {
        this.similarAccs = response;
      }, (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public onSearchSimilar(form: NgForm) {
    this.accounts = [];
    for(let account of this.similarAccs) {
      if(account.firstName.toLowerCase().includes(form.value.keyword.toLowerCase()) ||
        account.lastName.toLowerCase().includes(form.value.keyword.toLowerCase())) {

        let net: NetworkEntity = new NetworkEntity(account.firstName, account.lastName, account.email,
          account.imageUrl, null, null);

        for(let xp of account.experience) {
          if (xp.endDate === null && xp.visible === true) {
            net.position = xp.headline;
            net.company = xp.company;
            break;
          }
        }
        this.accounts.push(net);
      }
    }
  }

  refresh(): void {
    window.location.reload();
  }
}
