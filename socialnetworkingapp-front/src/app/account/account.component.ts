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
import { FormBuilder, FormControl, Validators, FormGroup, FormArray } from '@angular/forms';
import { TagsService } from '../tags/tags.service';
import { Tag } from '../tags/Tag'

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
  public TagsArray: Tag[];
  public UsersTags: Tag[];

  aboutForm: FormGroup;
  bioForm: FormGroup;
  addEducationForm: FormGroup;
  editEducationForm: FormGroup;
  interestsForm: FormGroup;
  experienceForm: FormGroup;
  editExperienceForm: FormGroup;


  constructor(private accountService: AccountService,
    private route: ActivatedRoute,
    public authenticationService: AuthenticationService,
    private experienceService: ExperienceService,
    private educationService: EducationService,
    private router: Router,
    private fb: FormBuilder,
    private tagsService: TagsService
  ) { }

  public fetchUserInfo() {
    let email = this.authenticationService.getCurrentUser();
    this.accountService.fetchUser(email).subscribe(
      (response: Account) => {
        this.account = new Account(response);
        if (this.account.bio === null) {
          this.account.bio = new Bio("No available bio.");
        }
      }
    );
  }

  ngOnInit(): void {


    this.fetchUserInfo();

    this.aboutForm = this.fb.group({
      email: new FormControl(this.authenticationService.getCurrentUser()),
      firstName: new FormControl('', [Validators.maxLength(20), Validators.pattern('[a-zA-Z ]*')]),
      lastName: new FormControl('', [Validators.maxLength(20), Validators.pattern('[a-zA-Z ]*')]),
      phone: new FormControl('', [Validators.pattern('[0-9]*'), Validators.maxLength(15)]),
    },
    );

    this.bioForm = this.fb.group({
      email: new FormControl(this.authenticationService.getCurrentUser()),
      description: new FormControl('')
    },
    );

    this.addEducationForm = this.fb.group({
      school: new FormControl(''),
      degree: new FormControl(''),
      field: new FormControl(''),
      startDate: new FormControl(''),
      endDate: new FormControl(''),
      grade: new FormControl(''),
      description: new FormControl(''),
      visible: new FormControl('true'),
    },
    );

    this.editEducationForm = this.fb.group({
      id: new FormControl(''),
      school: new FormControl(''),
      degree: new FormControl(''),
      field: new FormControl(''),
      startDate: new FormControl(''),
      endDate: new FormControl(''),
      grade: new FormControl(''),
      description: new FormControl(''),
      visible: new FormControl(''),
    },
    );

    this.experienceForm = this.fb.group({
      // id: new FormControl(''),
      title: new FormControl(''),
      employmentType : new FormControl(''),
      experienceLevel: new FormControl(''),
      company: new FormControl(''),
      location: new FormControl(''),
      startDate: new FormControl(''),
      endDate: new FormControl(''),
      headline: new FormControl(''),
      description: new FormControl(''),
      visible: new FormControl('true'),
    },
    );

    this.editExperienceForm = this.fb.group({
      id: new FormControl(''),
      title: new FormControl(''),
      employmentType : new FormControl(''),
      experienceLevel: new FormControl(''),
      company: new FormControl(''),
      location: new FormControl(''),
      startDate: new FormControl(''),
      endDate: new FormControl(''),
      headline: new FormControl(''),
      description: new FormControl(''),
      visible: new FormControl(''),
    },
    );



    this.tagsService.getUserTags().subscribe(
      (response: Tag[]) => {
        this.UsersTags = response;
      }
    );

    this.interestsForm = this.fb.group({
      interests: this.fb.array([], [Validators.required, Validators.minLength(2)])
    },
    );

    this.tagsService.getAllTags().subscribe(
      (response: Tag[]) => {
        this.TagsArray = response;
      }
    );




  }

  onCbChange(e: any) {
    const interests: FormArray = this.interestsForm.get('interests') as FormArray;
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
    if (mode === 'deleteEducation') {
      this.selectedEdu = data;
      button.setAttribute('data-target', '#educationdelete');
    }
    if (mode === 'deleteExperience') {
      this.selectedExp = data;
      button.setAttribute('data-target', '#experiencedelete');
    }
    if (container != null) {
      container.appendChild(button);
      button.click();
    }
  }

  public hide(data: any, mode: string) {

    if (mode == 'tag') {
      this.accountService.hideTags().subscribe(
        (response: Account) => {
          this.fetchUserInfo();
        }, (error: HttpErrorResponse) => {
          alert(error.message);
        }
      );
    }
    if (mode == 'experience') {
      this.experienceService.hideExperience(data).subscribe(
        (response: Experience) => {
          this.fetchUserInfo();
        }, (error: HttpErrorResponse) => {
          alert(error.message);
          window.location.reload();
        }
      );
    }
    if (mode == 'education') {
      this.educationService.hideEducation(data).subscribe(
        (response: Education) => {
          this.fetchUserInfo();
        }, (error: HttpErrorResponse) => {
          alert(error.message);
        }
      );
    }
    this.fetchUserInfo();
  }

  public show(data: any, mode: string) {

    if (mode == 'tag') {
      this.accountService.showTags().subscribe(
        (response: Account) => {
          this.fetchUserInfo();
        }, (error: HttpErrorResponse) => {
          alert(error.message);
        }
      );
    }
    if (mode == 'experience') {
      this.experienceService.showExperience(data).subscribe(
        (response: Experience) => {
          this.fetchUserInfo();
        }, (error: HttpErrorResponse) => {
          alert(error.message);
        }
      );
    }
    if (mode == 'education') {
      this.educationService.showEducation(data).subscribe(
        (response: Education) => {
          this.fetchUserInfo();
        }, (error: HttpErrorResponse) => {
          alert(error.message);
        }
      );
    }
  }

  public aboutSubmit(aboutForm: FormGroup) {

    this.accountService.aboutUpdateAccount(aboutForm.value).subscribe(
      (response: Account) => {
        this.fetchUserInfo();
      },
      (error: any) => {
        this.aboutForm.reset();
        console.log(error);
      }
    );


  }

  public bioSubmit(bioSubmit: FormGroup) {

    this.accountService.addBio(bioSubmit.value.description).subscribe(
      (response: Bio) => {
        this.fetchUserInfo();
      },
      (error: any) => {
        this.aboutForm.reset();
        console.log(error);
      }
    );
  }

  public addEducation(addEducationForm: FormGroup) {

    this.accountService.addEducation(addEducationForm.value).subscribe(
      (response: Account) => {
        this.fetchUserInfo();
        addEducationForm.reset();
      },
      (error: any) => {
        this.addEducationForm.reset();
        console.log(error);
      }
    );
  }

  public addExperience(experienceForm: FormGroup) {

    this.accountService.addExperience(experienceForm.value).subscribe(
      (response: Account) => {
        this.fetchUserInfo();
        experienceForm.reset();
      },
      (error: any) => {
        this.experienceForm.reset();
        console.log(error);
      }
    );
  }

  public editEducation(editEducationForm: FormGroup, selectedEdu: Education) {

    editEducationForm.get('id')?.setValue(selectedEdu.id);
    editEducationForm.get('visible')?.setValue(selectedEdu.visible);
    this.accountService.editEducation(editEducationForm.value).subscribe(
      (response: Education) => {
        this.fetchUserInfo();
      },
      (error: any) => {
        this.aboutForm.reset();
        console.log(error);
      }
    );
  }

  public editExperience(experienceForm: FormGroup) {

    experienceForm.get('id')?.setValue(this.selectedExp.id);
    experienceForm.get('visible')?.setValue(this.selectedExp.visible);
    this.accountService.editExperience(experienceForm.value).subscribe(
      (response: Experience) => {
        this.fetchUserInfo();
      },
      (error: any) => {
        this.experienceForm.reset();
        console.log(error);
      }
    );
  }

  public editInterests(interestsForm: FormGroup) {

    this.tagsService.addAllAccountTags(interestsForm.value.interests).subscribe(
      (resp: Account) => {
        this.fetchUserInfo();
      },
      (err: HttpErrorResponse) => {
        console.log(err);
        this.interestsForm.reset();
      }
    );

  }

  public deleteEducation(selectedEdu: Education) {
    this.accountService.deleteEducation(selectedEdu.id).subscribe(
      (response: void) => {
        this.fetchUserInfo();
      },
      (err: HttpErrorResponse) => {
        console.log(err);
        this.interestsForm.reset();
      }
    );
  }

  public deleteExperience(selectedExp: Experience) {
    this.accountService.deleteExperience(selectedExp.id).subscribe(
      (response: void) => {
        this.fetchUserInfo();
      },
      (err: HttpErrorResponse) => {
        console.log(err);
        this.interestsForm.reset();
      }
    );
  }
}
