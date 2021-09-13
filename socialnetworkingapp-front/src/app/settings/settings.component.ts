import { Component, OnInit } from '@angular/core';
import {AccountService} from "../account/account.service";
import {BioService} from "../bio/bio.service";
import {ActivatedRoute} from "@angular/router";
import {Account} from "../account/account";
import {Bio} from "../bio/bio";
import {HttpErrorResponse} from "@angular/common/http";
import {NgForm} from "@angular/forms";
import {readSpanComment} from "@angular/compiler-cli/src/ngtsc/typecheck/src/comments";

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.css']
})
export class SettingsComponent implements OnInit {

  public account: Account;
  public bio: string;

  constructor(private accountService: AccountService,
              private bioService: BioService,
              private route: ActivatedRoute) {
    this.route.params.subscribe(params => {
      console.log(params);
      if (params['id']) {
        this.getAccountDetails(params['id'])
      }
    });
  }

  ngOnInit(): void {
  }

  public getAccountDetails(uid: number): void {
    this.accountService.getAccountById(uid).subscribe(
      (response: Account) => {
        this.account = response;
        console.log(this.account);

        this.bioService.getBioByAccountId(response.id).subscribe(
          (responseBio: Bio) => {
            if(responseBio === null) {
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
  }

  /**
   *
   *
   * TODO: Να μην στελνει κενη φορμα και το disable να γινεται μονο για κενα πεδια.
   *        Na βαζω φωτο
   *        Να μπορω να κανω validation κατα την αλλαγη του email και password
   */
  public updateProfileInformation(settingsForm: NgForm): void {

    if(settingsForm.value.bio !== this.bio) {
      this.bio = settingsForm.value.bio;

      this.bioService.getBioByAccountId(this.account.id).subscribe(
        (response: Bio) => {
          response.description = this.bio;
          this.bioService.updateBio(response).subscribe(
            (responseBio: Bio) => {
              console.log(responseBio);
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
    }

    if(settingsForm.value.firstName !== this.account.firstName) {
      this.account.firstName = settingsForm.value.firstName;
    }
    if(settingsForm.value.lastName !== this.account.lastName) {
      this.account.lastName = settingsForm.value.lastName;
    }
    if(settingsForm.value.phone !== this.account.phone) {
      this.account.phone = settingsForm.value.phone;
    }

    this.accountService.updateAccount(this.account).subscribe(
      (response: Account) => {
        console.log(response);
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
    window.location.reload();
  }

  public updateEmail(emailForm: NgForm): void {

    if(emailForm.value.email !== this.account.email) {
      this.account.email = emailForm.value.email;
    }

    this.accountService.updateAccount(this.account).subscribe(
      (response: Account) => {
        console.log(response);
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
    window.location.reload();
  }

  public updatePassword(passwordForm: NgForm): void {

    if(passwordForm.value.password !== this.account.password) {
      this.account.password = passwordForm.value.password;
    }

    this.accountService.updateAccount(this.account).subscribe(
      (response: Account) => {
        console.log(response);
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
    window.location.reload();
  }
}
