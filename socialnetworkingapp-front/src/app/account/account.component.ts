import { Component, OnInit } from '@angular/core';
import { Account } from "./account";
import { AccountService } from "./account.service";
import { HttpErrorResponse } from "@angular/common/http";
import { ActivatedRoute, Router } from "@angular/router";
import { Bio } from "../bio/bio";
import { BioService } from "../bio/bio.service";
import { AuthenticationService } from '../authentication';

/* https:odecraft.tv/courses/angular/routing/parameterised-routes/ */

@Component({
    selector: 'app-account',
    templateUrl: './account.component.html',
    styleUrls: ['./account.component.css']
})
export class AccountComponent implements OnInit {

    public account: Account;

    constructor(private accountService: AccountService,
        private route: ActivatedRoute,
        private authenticationService : AuthenticationService ) {
    }

    ngOnInit(): void {
       let email = this.authenticationService.getCurrentUser();
       this.accountService.fetchUser(email).subscribe(
           (response: Account)=> {
               this.account = new Account(response);
               if(this.account.bio === null) {
                 this.account.bio = new Bio("No available bio.", null);
               }
           }
       );
/*
       this.getAccountDetails(this.account.id);
*/
    }

/*    public getAccountDetails(uid: number): void {
        this.accountService.getAccountById(uid).subscribe(
            (response: Account) => {
                this.account = response;
                console.log(this.account);

                this.bioService.getBioByAccountId(response.id).subscribe(
                    (responseBio: Bio) => {
                        if (responseBio === null) {
                            this.bio = "No available bio";
                        } else {
                            this.bio = responseBio.description;
                        }
                        console.log(this.bio);
                    },
                    (error: HttpErrorResponse) => {
                        alert(error.message);
                    }
                );
            },
            (error: HttpErrorResponse) => {
                alert(error.message);
            }
        );
    }*/
}
