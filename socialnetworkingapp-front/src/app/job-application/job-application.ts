import {Account} from "../account/account";
import {Job} from "../job/job";

export class JobApplication {

  id: number;
  applicant: Account;
  job: Job;

  constructor(applicant: Account, job: Job) {
    this.applicant = applicant;
    this.job = job;
  }
}
