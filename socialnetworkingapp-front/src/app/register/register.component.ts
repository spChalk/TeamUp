import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, FormBuilder, Validators } from "@angular/forms";
import { AccountService } from "../account/account.service";
import { HttpErrorResponse, HttpEvent, HttpEventType, HttpResponse } from "@angular/common/http";
import { Account } from "../account/account";
import { Bio } from "../bio/bio";
import { Router } from '@angular/router';
import { environment } from "../../environments/environment";
import { Observable } from "rxjs";
import { UploadFileService } from "../upload-files/upload-files.service";
import { BioComponent } from "../bio/bio.component";
import { BioService } from "../bio/bio.service";
import { AuthenticationService } from '../authentication';
import {repeatGroups} from "@angular/compiler/src/shadow_css";
import {r3JitTypeSourceSpan} from "@angular/compiler";
import {TagsService} from "../tags/tags.service";

@Component({
    selector: 'app-register',
    templateUrl: './register.component.html',
    styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

    registerForm: FormGroup;
    correctCredentials = true;
    selectedFiles: FileList;
    currentFile: File;
    progress = 0;
    message = '';

    private account: Account;

    constructor(
        private accountService: AccountService,
        private authService: AuthenticationService,
        public router: Router,
        private uploadService: UploadFileService,
        private bioService: BioService,
        private fb: FormBuilder,
        private tagsService: TagsService

    ) { }

    ngOnInit(): void {
        if (this.authService.isLoggedIn()) {
            this.router.navigate(['/home']);
        }

        this.registerForm = this.fb.group({
            email: new FormControl('', [Validators.required, Validators.pattern("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$")]),
            password: new FormControl('', [Validators.required, Validators.minLength(8), Validators.maxLength(16)]),
            // conf_password : new FormControl('',[Validators.required, Validators.minLength(8), Validators.maxLength(16)]),
            firstName: new FormControl('', [Validators.required, Validators.maxLength(20), Validators.pattern('[a-zA-Z ]*')]),
            lastName: new FormControl('', [Validators.required, Validators.maxLength(20), Validators.pattern('[a-zA-Z ]*')]),
            phone: new FormControl('', [Validators.required, Validators.pattern('[0-9]*'), Validators.maxLength(15)]),
            interests: new FormControl('')
        });

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
    }

    public onRegister(registerForm: FormGroup): void {
        //   if(registerForm.get('password').value !== registerForm.get('conf_password').value){
        //     console.log("passwords do not match");
        //     registerForm.reset();
        //     return;
        //   }
        this.accountService.registerAccount(registerForm.value).subscribe(
            (response: Account) => {
                console.log(response);

                for(let interest of registerForm. value.interests) {
                  this.tagsService.addAccountTag(response.email, interest).subscribe(
                    (resp: Account) => {
                      console.log(resp);
                    },
                    (err: HttpErrorResponse) => {
                      alert(err.message);
                    }
                  );
                }

            },
            (error) => {
                this.correctCredentials = false;
                this.registerForm.reset();
                console.log(error);
            }
        );
    }

    //   public onClickModal(mode: string): void {

    //     const container = document.getElementById('main-container');

    //     const button = document.createElement('button');
    //     button.type = 'button';
    //     button.style.display = 'none';
    //     button.setAttribute('data-toggle', 'modal');

    //     if(mode === 'addPhoto') {
    //       button.setAttribute('data-target', '#addPhoto');
    //     }
    //     if(mode === 'bio') {
    //       button.setAttribute('data-target', '#addBio');
    //     }
    //     if(container != null) {
    //       container.appendChild(button);
    //       button.click();
    //     }
    //   }

    //   uploadBio(description: string, email: string) {
    //     this.accountService.getAccountByEmail(email).subscribe((response) => {
    //       this.bioService.addBio( new Bio(description, response) ).subscribe(
    //         (responseBio: Bio) => {
    //           console.log(responseBio);
    //         },
    //         (error: HttpErrorResponse) => {
    //           alert(error.message);
    //         }
    //       );
    //     });
    //   }
}
