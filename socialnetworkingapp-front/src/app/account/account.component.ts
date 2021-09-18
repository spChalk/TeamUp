import { Component, OnInit } from '@angular/core';
import { Account } from "./account";
import { AccountService } from "./account.service";
import { HttpErrorResponse } from "@angular/common/http";
import { ActivatedRoute, Router } from "@angular/router";
import { Bio } from "../bio/bio";
import { BioService } from "../bio/bio.service";
import { AuthenticationService } from '../authentication';
import { Education } from "../education/education";
import { Experience } from "../experience/experience";
import { ExportService } from "../export/export.service";
import { EducationService } from "../education/education.service";
import { ExperienceService } from "../experience/experience.service";
import { FormBuilder, FormControl, Validators, FormGroup } from '@angular/forms';

/* https:odecraft.tv/courses/angular/routing/parameterised-routes/ */

@Component({
  selector: 'app-account',
  templateUrl: './account.component.html',
  styleUrls: ['./account.component.css']
})
export class AccountComponent implements OnInit {

  public account: Account;
  public selectedEdu: Education;
  public selectedExp: Experience;

  aboutForm: FormGroup;
  bioForm: FormGroup;

  constructor(private accountService: AccountService,
    private route: ActivatedRoute,
    private authenticationService: AuthenticationService,
    private experienceService: ExperienceService,
    private educationService: EducationService,
    private router: Router,
    private fb: FormBuilder
  ) { }


  ngOnInit(): void {

    let email = this.authenticationService.getCurrentUser();
    this.accountService.fetchUser(email).subscribe(
      (response: Account) => {
        this.account = new Account(response);
        if (this.account.bio === null) {
          this.account.bio = new Bio("No available bio.");
        }
      }
    );

    this.aboutForm = this.fb.group({
      email: new FormControl(this.authenticationService.getCurrentUser()),
      firstName: new FormControl(''),
      lastName: new FormControl(''),
      phone: new FormControl('')
    },
    );

    this.bioForm = this.fb.group({
      email: new FormControl(this.authenticationService.getCurrentUser()),
      description : new FormControl('')
    },
    );

  }

  public onClickModal(data: any, mode: string): void {

    const container = document.getElementById('main-container');

    const button = document.createElement('button');
    button.type = 'button';
    button.style.display = 'none';
    button.setAttribute('data-toggle', 'modal');

    if (mode === 'about') {
      button.setAttribute('data-target', '#about');
    }
    if (mode === 'bio') {
      button.setAttribute('data-target', '#biom');
    }
    if (mode === 'education') {
      this.selectedEdu = data;
      button.setAttribute('data-target', '#educationm');
    }
    if (mode === 'add_education') {
      button.setAttribute('data-target', '#educationadd');
    }
    if (mode === 'experience') {
      this.selectedExp = data;
      button.setAttribute('data-target', '#experiencem');
    }
    if (mode === 'add_experience') {
      button.setAttribute('data-target', '#experienceadd');
    }
    if (mode === 'interests') {
      button.setAttribute('data-target', '#interests');
    }
    if (container != null) {
      container.appendChild(button);
      button.click();
    }
  }

  public hide(data: any, mode: string) {

    if (mode == 'tag') {
      this.accountService.hideTags(this.account).subscribe(
        (response: Account) => {
          console.log(response);
        }, (error: HttpErrorResponse) => {
          alert(error.message);
        }
      );
    }
    if (mode == 'experience') {
      this.experienceService.hideExperience(data).subscribe(
        (response: Experience) => {
          console.log(response);
        }, (error: HttpErrorResponse) => {
          alert(error.message);
        }
      );
    }
    if (mode == 'education') {
      this.educationService.hideEducation(data).subscribe(
        (response: Education) => {
          console.log(response);
        }, (error: HttpErrorResponse) => {
          alert(error.message);
        }
      );
    }
    window.location.reload();
  }

  public show(data: any, mode: string) {

    if (mode == 'tag') {
      this.accountService.showTags(this.account).subscribe(
        (response: Account) => {
          console.log(response);
        }, (error: HttpErrorResponse) => {
          alert(error.message);
        }
      );
    }
    if (mode == 'experience') {
      this.experienceService.showExperience(data).subscribe(
        (response: Experience) => {
          console.log(response);
        }, (error: HttpErrorResponse) => {
          alert(error.message);
        }
      );
    }
    if (mode == 'education') {
      this.educationService.showEducation(data).subscribe(
        (response: Education) => {
          console.log(response);
        }, (error: HttpErrorResponse) => {
          alert(error.message);
        }
      );
    }
    window.location.reload();
  }

  public aboutSubmit(aboutForm: FormGroup) {

    this.accountService.aboutUpdateAccount(aboutForm.value).subscribe(
      (response: Account) => {
        console.log(response);
      },
      (error: any) => {
        this.aboutForm.reset();
        console.log(error);
      }
    );


  }

  public bioSubmit(bioSubmit: FormGroup) {

    this.accountService.addBio(bioSubmit.value.email, bioSubmit.value.description).subscribe(
      (response: Bio) => {
        console.log(response);
      },
      (error: any) => {
        this.aboutForm.reset();
        console.log(error);
      }
    );
    window.location.reload();
  }
}
