import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, FormBuilder, Validators, FormArray } from "@angular/forms";
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
import { TagsService } from "../tags/tags.service";
import {Tag} from '../tags/Tag'

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
  TagsArray : Tag[];


    private account: Account;

    constructor(
        private accountService: AccountService,
        private authService: AuthenticationService,
        public router: Router,
        private uploadService: UploadFileService,
        private bioService: BioService,
        private fb: FormBuilder,
        private tagsService: TagsService,
        private authenticationService : AuthenticationService

    ) { }

    ngOnInit(): void {
        if (this.authService.isLoggedIn()) {
            this.router.navigate(['/home']);
        }

        this.registerForm = this.fb.group({
            email: new FormControl('', [Validators.required, Validators.pattern("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$")]),
            password: new FormControl('', [Validators.required, Validators.minLength(8), Validators.maxLength(16)]),
            conf_password: new FormControl('', [Validators.required, Validators.minLength(8), Validators.maxLength(16)]),
            firstName: new FormControl('', [Validators.required, Validators.maxLength(20), Validators.pattern('[a-zA-Z ]*')]),
            lastName: new FormControl('', [Validators.required, Validators.maxLength(20), Validators.pattern('[a-zA-Z ]*')]),
            phone: new FormControl('', [Validators.required, Validators.pattern('[0-9]*'), Validators.maxLength(15)]),
            interests: this.fb.array([],[Validators.required , Validators.minLength(2)])
        },
            { validator: this.checkPasswords }
        );

        this.tagsService.getAllTags().subscribe(
        (response: Tag[]) => {
            this.TagsArray= response;
            }
        );

    }

    onCbChange(e : any) {
        const interests : FormArray = this.registerForm.get('interests') as FormArray;
        if (e.target.checked) {
           interests.push(new FormControl(e.target.value));
        } else {
            let i: number = 0;
            interests.controls.forEach((item: any) => {
                if (item.value == e.target.value) {
                    interests.removeAt(i);
                    return;
                }
                i++;
            });
        }
    }

    selectFile(event: any) {
        this.selectedFiles = event.target.files;
    }

    checkPasswords(group: FormGroup) {
        const password = group.controls.password.value;
        const confirm_password = group.controls.conf_password.value;
        return password === confirm_password ? null : { notSame: true };
    }

    upload() {
        this.progress = 0;
        this.currentFile = this.selectedFiles.item(0);
        this.uploadService.uploadUser(this.currentFile, this.authenticationService.getJWT()).subscribe(
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

    public onRegister(registerForm: FormGroup, mode: string): void {

        this.accountService.registerAccount(registerForm.value).subscribe(
            (response: Account) => {
                console.log(response);
                for (let interest of registerForm.value.interests) {
                    this.tagsService.addAccountTag(response.email, interest).subscribe(
                        (resp: Account) => {
                            console.log(resp);
                        },
                        (err: HttpErrorResponse) => {
                            alert(err.message);
                        }
                    );
                }

                this.authService.logIn({ 'username': registerForm.get('email')?.value, 'password': registerForm.get('password')?.value }).subscribe(
                    (newResponse: boolean) => {
                        this.router.navigateByUrl('/home');
                        this.onClickModal(mode);
                    },
                    (error) => {
                        console.log(error);
                    }
                )
            },
            (error: any) => {
                this.correctCredentials = false;
                this.registerForm.reset();
                console.log(error);
            }
        );



    }

    public onClickModal(mode: string): void {

        const container = document.getElementById('main-container');

        const button = document.createElement('button');
        button.type = 'button';
        button.style.display = 'none';
        button.setAttribute('data-toggle', 'modal');

        if (mode === 'addPhoto') {
            button.setAttribute('data-target', '#addPhoto');
        }
        if (container != null) {
            container.appendChild(button);
            button.click();
        }

    }
}
