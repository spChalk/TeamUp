import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, FormBuilder, Validators } from "@angular/forms";
import { AccountService } from "../account/account.service";
import { Router } from '@angular/router';
import { AuthenticationService } from '../authentication';

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

    loginForm: FormGroup;
    correctCredentials = true ;

    constructor(private accountService: AccountService,
        private router: Router,
        private fb: FormBuilder,
        private authenticationService: AuthenticationService) {
    };


    ngOnInit(): void {

        if (this.authenticationService.isLoggedIn()) {
            this.router.navigate(['/home']);
        }

        this.loginForm = this.fb.group({
            username: new FormControl('', [Validators.required, Validators.pattern("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$")]),
            password: new FormControl('', [Validators.required, Validators.minLength(8), Validators.maxLength(16)])
        });
    }

    public onLogin(loginForm: FormGroup): void {
        this.authenticationService.logIn(loginForm.value).subscribe(
            (response: boolean) => {
                this.router.navigateByUrl('/home');
            },
            (error) => {
                this.correctCredentials = false;
                this.loginForm.reset();
                console.log(error);
            }
        );
    }
}
