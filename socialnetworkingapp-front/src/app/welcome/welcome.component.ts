import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, NgForm, Validators} from "@angular/forms";
import {Account} from "../account/account";
import {HttpErrorResponse} from "@angular/common/http";
import {AccountService} from "../account/account.service";
import {Login} from "../login/login";
import {ActivatedRoute, Router} from "@angular/router";
import {AuthService} from "../auth/auth.service";
import {first} from "rxjs/operators";

@Component({
  selector: 'app-welcome',
  templateUrl: './welcome.component.html',
  styleUrls: ['./welcome.component.css']
})
export class WelcomeComponent implements OnInit {

  loginForm: FormGroup;
  loading = false;
  submitted = false;
  returnUrl: string = '/home';
  error = '';

  constructor(private accountService: AccountService,
              private formBuilder: FormBuilder,
              private route: ActivatedRoute,
              private router: Router,
              private authenticationService: AuthService){
        // redirect to home if already logged in
        if(this.authenticationService.currentUserValue)
          this.router.navigate(['/home']);
        }

    ngOnInit() {
      this.loginForm = this.formBuilder.group({
        username: ['', Validators.required],
        password: ['', Validators.required]
      });
      // get return url from route parameters or default to '/'
      this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/home';
    }
    get f(){ return this.loginForm.controls; }

    public onLogin() {

      this.submitted = true;

      // stop here if form is invalid
      if (this.loginForm.invalid) {
        return;
      }

      this.loading = true;
      this.authenticationService.login(this.f.username.value, this.f.password.value)
        .pipe(first())
        .subscribe(
          data => {
            this.router.navigate([this.returnUrl]);
          },
          error => {
            this.error = error;
            this.loading = false;
          });
  }
}
