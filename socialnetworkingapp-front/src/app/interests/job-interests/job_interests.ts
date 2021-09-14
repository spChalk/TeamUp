import {Tags} from "../Tags";
import {Job} from "../../job/job";

export class JobInterest {

  id: number;
  job: Job;
  tag: Tags;

  constructor(job: Job, tag: Tags) {
    this.job = job;
    this.tag = tag;
  }
}
