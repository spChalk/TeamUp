import {Account} from "../account/account";
import {Job} from "../job/job";

export class JobApplication {

  id: number;
  applicantEmail: string;
  jobId: number;

  constructor(id: number, applicantEmail: string, jobId: number) {
    this.id = id;
    this.applicantEmail = applicantEmail;
    this.jobId = jobId;
  }
}
