import { Component, OnInit } from '@angular/core';
import {Account} from "../account/account";
import {HttpErrorResponse} from "@angular/common/http";
import { Job } from './job';
import {JobService} from "./job.service";
import {JobApplicationService} from "../job-application/job-application.service";
import {AccountService} from "../account/account.service";
import {ActivatedRoute, Router} from "@angular/router";
import {JobApplication} from "../job-application/job-application";
import {FormArray, FormBuilder, FormControl, FormGroup, NgForm, Validators} from "@angular/forms";
import {JobViewService} from "../job-view/job-view.service";
import {JobView} from "../job-view/job-view";
import {TagsService} from "../tags/tags.service";
import {AuthenticationService} from "../authentication";
import {JobRequest} from "./job-request";
import {Tag} from "../tags/Tag";

@Component({
  selector: 'app-job',
  templateUrl: './job.component.html',
  styleUrls: ['./job.component.css']
})
export class JobComponent implements OnInit {

  public jobs: Job[];

  /* [job id, views] */
  public jobViews: Map<number, JobView[]> = new Map<number, JobView[]>();
  public hasAppliedToJob: Map<number, boolean> = new Map<number, boolean>();

  public account: Account;

  public selectedJob: Job;
  public selectedJobApplicants: JobApplication[] = [];
  public jobIdToDelete: number;
  public jobIdToEdit: number;
  public jobIdToDeleteApplicationFrom: number;

  public jobForm: FormGroup;
  public TagsArray: Tag[];

  constructor(private jobService: JobService,
              private jobAppService: JobApplicationService,
              private jobViewService: JobViewService,
              private accountService: AccountService,
              private route: ActivatedRoute,
              private tagsService: TagsService,
              public authenticationService: AuthenticationService,
              private router: Router,
              private fb: FormBuilder) {

    if (this.authenticationService.isAdmin()) {
      this.router.navigate(['/admin']);
    }
    let email = this.authenticationService.getCurrentUser();
    this.accountService.fetchUser(email).subscribe(
      (response: Account) => {
        this.account = new Account(response);
      }
    );
    this.getJobs();
  }

  ngOnInit() {
    this.jobForm = this.fb.group({
        title: new FormControl('', [Validators.required, Validators.maxLength(50), Validators.pattern('^[\.a-zA-Z0-9\-,!? ]*$')]),
        location: new FormControl('', [Validators.required, Validators.maxLength(40), Validators.pattern('^[\.a-zA-Z0-9\-,!? ]*$')]),
        interests: this.fb.array([],[Validators.required , Validators.minLength(2)]),
        jobType: new FormControl(''),
        experienceLevel: new FormControl(''),
        info: new FormControl(''),
    }
    );
    this.tagsService.getAllTags().subscribe(
      (response: Tag[]) => {
        this.TagsArray= response;
      }
    );
  }

  public getJobs(): void {
    this.jobService.getAllJobs().subscribe(
      (jobs: Job[]) => {
          this.jobs = jobs;
          for(let job of this.jobs) {
            this.jobViewService.getViewsByJob(job.id).subscribe(
              (views: JobView[]) => {
                this.jobViews.set(job.id, views);
              }, (err: HttpErrorResponse) => {
                alert(err.message);
              }
            );
          }
        },
      (error: HttpErrorResponse) => {
        alert(error.message);
      });
    this.jobAppService.getUserApplications().subscribe(
      (applications: JobApplication[]) => {
        for(let application of applications) {
          this.hasAppliedToJob.set(application.jobId, true);
        }
      }
    );
  }

  public getTotalViews(views: JobView[]) {
    if(views === undefined) return 0;

    let sum = 0;
    for(let view of views) {
      sum += view.times;
    }
    return sum;
  }

  public setSelectedJob(job: Job): void {
    this.selectedJob = job;
  }

  onCbChange(e : any) {
    const interests : FormArray = this.jobForm.get('interests') as FormArray;
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

    if(mode === 'apply') {
      button.setAttribute('data-target', '#apply');
    }
    if(mode === 'addJob') {
      button.setAttribute('data-target', '#addJob');
    }
    if(mode === 'deleteJob') {
      this.jobIdToDelete = data;
      button.setAttribute('data-target', '#deleteJob');
    }
    if(mode === 'editJob') {
      this.jobIdToEdit = data;
      button.setAttribute('data-target', '#editJob');
    }
    if(mode === 'deleteApplication') {
      this.jobIdToDeleteApplicationFrom = data;
      button.setAttribute('data-target', '#deleteApplication');
    }
    if(mode === 'seeViews') {
      button.setAttribute('data-target', '#seeViews');
    }
    if(mode === 'seeApplicants') {
      this.jobAppService.getJobApplicants(data).subscribe(
        (applications: JobApplication[]) => {
          this.selectedJobApplicants = applications;
        }, (err: HttpErrorResponse) => {
          alert(err.message);
        }
      );
      button.setAttribute('data-target', '#seeApplicants');
    }
    if(container != null) {
      container.appendChild(button);
      button.click();
    }
  }

  public onApplyToJob(selectedJob: Job) {

    this.jobAppService.applyToJob(selectedJob).subscribe(
      (response: JobApplication) => {
        this.getJobs();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public onAddJob(jobForm: FormGroup) {

    let newJobRequest = new JobRequest(jobForm.value.title,
      jobForm.value.location,
      jobForm.value.jobType,
      jobForm.value.experienceLevel,
      jobForm.value.info,
      jobForm.value.interests
    );

    this.jobService.addJob(newJobRequest).subscribe(
      (response: Job) => {
        this.getJobs();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public increaseView(jid: number) {
    this.jobViewService.addView(jid).subscribe(
      (response: JobView) => {

      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public onDeleteJob(jobIdToDelete: number) {
    this.jobService.deleteJob(jobIdToDelete).subscribe(
      (response: void) => {
        this.selectedJob = undefined;
        this.getJobs();
      }, (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public onDeleteJobApplication(jobIdToDeleteApplicationFrom: number) {
    this.jobAppService.getApplicationByUserAndJobIds(this.account.id, jobIdToDeleteApplicationFrom).subscribe(
      (app: JobApplication) => {
        this.jobAppService.deleteJobApplication(
          app.id
        ).subscribe(
          (response: any) => {
            window.location.reload();
          }, (error: HttpErrorResponse) => {
            alert(error.message);
          }
        );
      }, (err: HttpErrorResponse) => {
        alert(err.message);
      }
    );
  }

  public onEditJob(jobId: number, jobForm: FormGroup) {
    let newJobRequest = new JobRequest(jobForm.value.title,
      jobForm.value.location,
      jobForm.value.jobType,
      jobForm.value.experienceLevel,
      jobForm.value.info,
      jobForm.value.interests
    );

    this.jobService.editJob(jobId, newJobRequest).subscribe(
      (response: Job) => {
        this.getJobs();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }
}
