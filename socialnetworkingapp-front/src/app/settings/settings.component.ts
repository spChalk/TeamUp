import { Component, OnInit } from '@angular/core';
import {AccountService} from "../account/account.service";
import {BioService} from "../bio/bio.service";
import {ActivatedRoute} from "@angular/router";
import {Account} from "../account/account";
import {Bio} from "../bio/bio";
import {HttpErrorResponse, HttpEventType, HttpResponse} from "@angular/common/http";
import {NgForm} from "@angular/forms";
import {readSpanComment} from "@angular/compiler-cli/src/ngtsc/typecheck/src/comments";
import {UploadFileService} from "../upload-files/upload-files.service";
import {environment} from "../../environments/environment";

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.css']
})
export class SettingsComponent implements OnInit {

  public account: Account;
  public bio: string;

  selectedFiles: FileList;
  currentFile: File;
  progress = 0;
  message = '';

  constructor(private accountService: AccountService,
              private bioService: BioService,
              private route: ActivatedRoute,
              private uploadService: UploadFileService) {
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
   * TODO: Nα μπορω να κανω validation κατα την αλλαγη του email και password
   */
  public updateProfileInformation(settingsForm: NgForm): void {

    if(settingsForm.value.bio !== this.bio) {
      this.bio = settingsForm.value.bio;
      this.bioService.getBioByAccountId(this.account.id).subscribe(
        (response: Bio) => {

          if(response === null) {
              this.bioService.addBio(new Bio(this.bio, this.account)).subscribe(
                (responseBio: Bio) => {
                  console.log(responseBio);
                },
                (error: HttpErrorResponse) => {
                  alert(error.message);
                }
              );
          }
          else {
            response.description = this.bio;
            this.bioService.updateBio(response).subscribe(
              (responseBio: Bio) => {
                console.log(responseBio);
              },
              (error: HttpErrorResponse) => {
                alert(error.message);
              }
            );
          }

        },
        (error: HttpErrorResponse) => {
          alert(error.message);
        }
      );
    }

    if(settingsForm.value.firstName && (settingsForm.value.firstName !== this.account.firstName)) {
      this.account.firstName = settingsForm.value.firstName;
    }
    if(settingsForm.value.lastName && (settingsForm.value.lastName !== this.account.lastName)) {
      this.account.lastName = settingsForm.value.lastName;
    }
    if(settingsForm.value.phone && (settingsForm.value.phone !== this.account.phone)) {
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

  upload() {

    if(this.selectedFiles  === undefined) {
      return;
    }

    this.progress = 0;
    this.currentFile = this.selectedFiles.item(0);

    if(this.account.imageUrl !== null) {
      this.uploadService.delete(this.account.imageUrl.replace(environment.apiBaseUrl + "/files/", "")).subscribe(
        event => {
          console.log(event);
        },
        (error: HttpErrorResponse) => {
          alert(error.message);
        }
      );
    }
    this.uploadService.upload(this.currentFile, this.account.email).subscribe(
      event => {
        if (event.type === HttpEventType.UploadProgress) {
          this.progress = Math.round(100 * event.loaded / event.total);
        } else if (event instanceof HttpResponse) {
          this.message = event.body.message;
        }
      },
      err => {
        this.progress = 0;
        this.message = 'Could not upload the file!';
        this.currentFile = undefined;
      });
    this.selectedFiles = undefined;
  }

  selectFile(event: any) {
    this.selectedFiles = event.target.files;
  }

  public onClickModal(mode: string): void {
    const container = document.getElementById('main-container');

    const button = document.createElement('button');
    button.type = 'button';
    button.style.display = 'none';
    button.setAttribute('data-toggle', 'modal');

    if(mode === 'updateEmail') {
      button.setAttribute('data-target', '#updateEmail');
    }
    if(mode === 'updatePass') {
      button.setAttribute('data-target', '#updatePass');
    }
    if(container != null) {
      container.appendChild(button);
      button.click();
    }
  }

  public updateEmail(emailForm: NgForm, confirmation: string): void {

/*
    this.accountService.confirmPassword(confirmation, this.account.password).subscribe(
      (response: boolean) => {
        if (!response) {
          alert("Error, user was not authenticated. Update failed.");
          return;
        }
      },
        (error: HttpErrorResponse) => {
          alert(error.message);
        }
    );
*/

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

  public updatePassword(passwordForm: NgForm, confirmation: string): void {

 /*   this.accountService.confirmPassword(confirmation, this.account.password).subscribe(
      (response: boolean) => {
        if (!response) {
          alert("Error, user was not authenticated. Update failed.");
          return;
        }
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
*/
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
