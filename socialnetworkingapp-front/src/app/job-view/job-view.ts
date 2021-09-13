import {Account} from "../account/account";
import {Job} from "../job/job";

export class JobView {

  id: number;
  viewer: Account;
  job: Job;
  times: number;

  constructor(viewer: Account, job: Job, times: number) {
    this.viewer = viewer;
    this.job = job;
    this.times = times;
  }
}
