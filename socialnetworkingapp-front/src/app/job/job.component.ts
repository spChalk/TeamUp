import { Component, OnInit } from '@angular/core';
import {Account} from "../account/account";
import {HttpErrorResponse} from "@angular/common/http";
import { Job } from './job';
import {JobService} from "./job.service";
import {JobApplicationService} from "../job-application/job-application.service";
import {AccountService} from "../account/account.service";
import {BioService} from "../bio/bio.service";
import {ActivatedRoute} from "@angular/router";
import {JobApplication} from "../job-application/job-application";

@Component({
  selector: 'app-job',
  templateUrl: './job.component.html',
  styleUrls: ['./job.component.css']
})
export class JobComponent implements OnInit {

  public jobs: Job[];
  public selectedJob: Job;
  public currUser: Account;

  constructor(private jobService: JobService,
              private jobAppService: JobApplicationService,
              private accountService: AccountService,
              private route: ActivatedRoute) {
   this.route.params.subscribe(params => {
      console.log(params);
      if (params['uid']) {
/*
        this.getJobs(params['uid']);
*/
        this.accountService.getAccountById(params['uid']).subscribe(acc => this.currUser = acc);
      }
    });
  }

  ngOnInit() {
    this.getJobs();
  }

  public getJobs(): void {
    this.jobService.getAllJobs().subscribe(
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
    if(container != null) {
      container.appendChild(button);
      button.click();
    }
  }

  public onApplyToJob(selectedJob: Job) {
    this.jobAppService.applyToJob(this.currUser, selectedJob).subscribe(
      (response: JobApplication) => {
        console.log(response);
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }
}
