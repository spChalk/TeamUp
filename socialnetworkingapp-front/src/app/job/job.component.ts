import { Component, OnInit } from '@angular/core';
import {Account} from "../account/account";
import {HttpErrorResponse} from "@angular/common/http";
import { Job } from './job';
import {JobService} from "./job.service";
import {JobApplicationService} from "../job-application/job-application.service";
import {AccountService} from "../account/account.service";
import {BioService} from "../bio/bio.service";
import {ActivatedRoute, Router} from "@angular/router";
import {JobApplication} from "../job-application/job-application";
import {NgForm} from "@angular/forms";
import {JobViewService} from "../job-view/job-view.service";
import {JobView} from "../job-view/job-view";
import {TagsService} from "../tags/tags.service";
import {AuthenticationService} from "../authentication";
import {JobRequest} from "./job-request";
import {readSpanComment} from "@angular/compiler-cli/src/ngtsc/typecheck/src/comments";

@Component({
  selector: 'app-job',
  templateUrl: './job.component.html',
  styleUrls: ['./job.component.css']
})
export class JobComponent implements OnInit {

  public jobs: Job[];
  /* [job id, views] */
  public jobViews: Map<number, number> = new Map<number, number>();
  public hasAppliedToJob: Map<number, boolean> = new Map<number, boolean>();
  public selectedJob: Job;
  public account: Account;
  public jobIdToDelete: number;
  public jobIdToEdit: number;
  public jobIdToDeleteApplicationFrom: number;

  constructor(private jobService: JobService,
              private jobAppService: JobApplicationService,
              private jobViewService: JobViewService,
              private accountService: AccountService,
              private route: ActivatedRoute,
              private tagsService: TagsService,
              public authenticationService: AuthenticationService,
              private router: Router) {

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
  }

  public getJobs(): void {
    this.jobService.getAllJobs(this.authenticationService.getJWT()).subscribe(
      (jobs: Job[]) => {
          this.jobs = jobs;
          for(let job of this.jobs) {
            this.jobViewService.getViewsByJob(job.id).subscribe(
              (views: number) => {
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
    this.jobAppService.getUserApplications(this.authenticationService.getJWT()).subscribe(
      (applications: JobApplication[]) => {
        for(let application of applications) {
          this.hasAppliedToJob.set(application.jobId, true);
        }
      }
    );
  }

  public setSelectedJob(job: Job): void {
    this.selectedJob = job;
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
    if(container != null) {
      container.appendChild(button);
      button.click();
    }
  }

  public onApplyToJob(selectedJob: Job) {

    this.jobAppService.applyToJob(selectedJob, this.authenticationService.getJWT()).subscribe(
      (response: JobApplication) => {
        this.getJobs();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public onAddJob(jobForm: NgForm) {

    let newJobRequest = new JobRequest(jobForm.value.title,
      jobForm.value.location,
      jobForm.value.jobType,
      jobForm.value.experienceLevel,
      jobForm.value.info,
      jobForm.value.interests
    );

    this.jobService.addJob(newJobRequest, this.authenticationService.getJWT()).subscribe(
      (response: Job) => {
        console.log(response);
        this.getJobs();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public increaseView(uid: number, jid: number) {
    this.jobViewService.addView(uid, jid).subscribe(
      (response: JobView) => {
        console.log(response);
        this.getJobs();
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public onDeleteJob(jobIdToDelete: number) {
    this.jobService.deleteJob(jobIdToDelete).subscribe(
      (response: void) => {
        this.getJobs();
      }, (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public onDeleteJobApplication(jobIdToDeleteApplicationFrom: number) {
    this.jobAppService.getApplicationByUserAndJobIds(this.account.id, jobIdToDeleteApplicationFrom,
      this.authenticationService.getJWT()).subscribe(
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
}
