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

@Component({
  selector: 'app-job',
  templateUrl: './job.component.html',
  styleUrls: ['./job.component.css']
})
export class JobComponent implements OnInit {

  public jobs: Job[] = [];
  public selectedJob: Job;
  public account: Account;

  constructor(private jobService: JobService,
              private jobAppService: JobApplicationService,
              private jobViewService: JobViewService,
              private accountService: AccountService,
              private route: ActivatedRoute,
              private tagsService: TagsService,
              private authenticationService: AuthenticationService,
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
      (response: Job[]) => {
        this.jobs = response;
        console.log(this.jobs);
    },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public setSelectedJob(job: Job): void {
    this.selectedJob = job;
  }

  public onClickModal(mode: string): void {

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
    if(container != null) {
      container.appendChild(button);
      button.click();
    }
  }

  public onApplyToJob(selectedJob: Job) {

    this.jobAppService.applyToJob(this.account, selectedJob).subscribe(
      (response: JobApplication) => {
        console.log(response);
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
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }
}
