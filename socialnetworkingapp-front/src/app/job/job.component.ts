import { Component, OnInit } from '@angular/core';
import {Account} from "../account/account";
import {HttpErrorResponse} from "@angular/common/http";
import { Job } from './job';
import {JobService} from "./job.service";
import {JobApplicationService} from "../job-application/job-application.service";

@Component({
  selector: 'app-job',
  templateUrl: './job.component.html',
  styleUrls: ['./job.component.css']
})
export class JobComponent implements OnInit {

  public jobs: Job[];
  public selectedJob: Job;

  constructor(private jobService: JobService,
              private jobAppService: JobApplicationService) { }

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

  onApplyToJob(selectedJob: Job) {
    /* TODO */
  }
}
