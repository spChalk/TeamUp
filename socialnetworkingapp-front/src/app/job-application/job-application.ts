import {Account} from "../account/account";
import {Job} from "../job/job";

export interface JobApplication {

  id: number;
  user: Account;
  job: Job;
}
