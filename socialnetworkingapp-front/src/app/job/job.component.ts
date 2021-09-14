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
import {NgForm} from "@angular/forms";
import {JobViewService} from "../job-view/job-view.service";
import {JobView} from "../job-view/job-view";
import {AccountInterest} from "../interests/acc-interests/acc_interests";
import {AccInterestsService} from "../interests/acc-interests/acc-interests.service";
import {JobInterestsService} from "../interests/job-interests/job-interests.service";
import {JobInterest} from "../interests/job-interests/job_interests";

@Component({
  selector: 'app-job',
  templateUrl: './job.component.html',
  styleUrls: ['./job.component.css']
})
export class JobComponent implements OnInit {

  public jobs: Job[] = [];
  public selectedJob: Job;
  public currUser: Account;

  constructor(private jobService: JobService,
              private jobAppService: JobApplicationService,
              private jobViewService: JobViewService,
              private accountService: AccountService,
              private route: ActivatedRoute,
              private interestsService: JobInterestsService) {

  }

  ngOnInit() {
    this.route.params.subscribe(params => {
      console.log(params);
      if (params['uid']) {
        this.getJobs(params['uid']);
        this.accountService.getAccountById(params['uid']).subscribe(
          (acc: Account) => {
            this.currUser = acc;
          });
      }
    });
  }

  public getJobs(uid: number): void {
    this.jobService.getAllJobs(uid).subscribe(
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

    this.jobAppService.applyToJob(this.currUser, selectedJob).subscribe(
      (response: JobApplication) => {
        console.log(response);
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public onAddJob(jobForm: NgForm) {

    let newJob = new Job(jobForm.value.title,
      this.currUser,
      jobForm.value.location,
      new Date(),
      jobForm.value.jobType,
      jobForm.value.experienceLevel,
      jobForm.value.info,
      );

    this.jobService.addJob(newJob).subscribe(
      (response: Job) => {
        console.log(response);

        for (let interest of jobForm.value.interests) {
          this.interestsService.addTag(response, interest).subscribe(
            (res: JobInterest) => {
              console.log(res);
            },
            (err: HttpErrorResponse) => {
              alert(err.message);
            }
          );
        }


        this.getJobs(this.currUser.id);
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
