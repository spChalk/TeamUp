import { Component, OnInit } from '@angular/core';
import {NgForm} from "@angular/forms";
import {AccountService} from "../account/account.service";
import {HttpErrorResponse, HttpEvent, HttpEventType, HttpResponse} from "@angular/common/http";
import {Account} from "../account/account";
import { Router } from '@angular/router';
import {environment} from "../../environments/environment";
import {Observable} from "rxjs";
import {UploadFileService} from "../upload-files/upload-files.service";
import {repeatGroups} from "@angular/compiler/src/shadow_css";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  selectedFiles: FileList;
  currentFile: File;
  progress = 0;
  message = '';

  fileInfos: Observable<any>;

  constructor(
    private accountService: AccountService,
    public router: Router,
    private uploadService: UploadFileService
    ){}

  ngOnInit(): void {
  }

  selectFile(event: any) {
    this.selectedFiles = event.target.files;
  }

  upload(email: string) {
   this.progress = 0;
   this.currentFile = this.selectedFiles.item(0);
   this.uploadService.upload(this.currentFile, email).subscribe(
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
   this.router.navigateByUrl("/");
  }

  public onRegister(regForm: NgForm): void {

    if (regForm.value.password !== regForm.value.conf_password) {
      alert("Passwords do not match!");
      return;
    }

    this.accountService.registerAccount(regForm.value).subscribe(
      (response: Account) => {
        console.log(response);
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public onClickModal(mode: string): void {

    const container = document.getElementById('main-container');

    const button = document.createElement('button');
    button.type = 'button';
    button.style.display = 'none';
    button.setAttribute('data-toggle', 'modal');

    if(mode === 'addPhoto') {
      button.setAttribute('data-target', '#addPhoto');
    }
    if(container != null) {
      container.appendChild(button);
      button.click();
    }
  }

}
