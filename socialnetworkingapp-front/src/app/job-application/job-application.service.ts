import { Injectable } from '@angular/core';
import {Job} from "../job/job";
import {HttpClient} from "@angular/common/http";
import {JobApplication} from "./job-application";
import {environment} from "../../environments/environment";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class JobApplicationService {

  private url = environment.apiBaseUrl + "/jobapp";

  constructor(private http: HttpClient) { }

  public applyToJob(selectedJob: Job) {
    return this.http.post<JobApplication>(`${this.url}/apply/${selectedJob.id}`, {});
  }

  public getUserApplications() {
    return this.http.get<JobApplication[]>(`${this.url}/u`);
  }

  public getJobApplicants(jobId: number) {
    return this.http.get<JobApplication[]>(`${this.url}/j/${jobId}`);
  }

  public deleteJobApplication(id: number) {
    return this.http.delete<any>(`${this.url}/delete/${id}`);
  }

  public getApplicationByUserAndJobIds(id: number, jobIdToDeleteApplicationFrom: number): Observable<JobApplication> {
    return this.http.get<JobApplication>(`${this.url}/uj/${id}/${jobIdToDeleteApplicationFrom}`);
  }
}
