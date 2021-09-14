import { Injectable } from '@angular/core';
import {Account} from "../account/account";
import {Job} from "../job/job";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {JobApplication} from "./job-application";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class JobApplicationService {

  private url = environment.apiBaseUrl + "/jobapp";

  constructor(private http: HttpClient) { }

  public applyToJob(currUser: Account, selectedJob: Job) {
    return this.http.post<JobApplication>(`${this.url}/apply`,  new JobApplication(currUser, selectedJob));
  }
}
