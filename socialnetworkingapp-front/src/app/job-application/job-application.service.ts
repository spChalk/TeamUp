import { Injectable } from '@angular/core';
import {Account} from "../account/account";
import {Job} from "../job/job";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {JobApplication} from "./job-application";
import {environment} from "../../environments/environment";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class JobApplicationService {

  private url = environment.apiBaseUrl + "/jobapp";

  constructor(private http: HttpClient) { }

  public applyToJob(selectedJob: Job, token: string) {
    return this.http.post<JobApplication>(`${this.url}/apply/${selectedJob.id}`, {},
      {
        headers: new HttpHeaders({"Authorization": "Bearer " + token})
      });
  }

  public getUserApplications(token: string) {
    return this.http.get<JobApplication[]>(`${this.url}/u`,
      {
        headers: new HttpHeaders({"Authorization": "Bearer " + token})
      });
  }

  public deleteJobApplication(id: number) {
    return this.http.delete<any>(`${this.url}/delete/${id}`);
  }

  public getApplicationByUserAndJobIds(id: number, jobIdToDeleteApplicationFrom: number, token: string): Observable<JobApplication> {
    return this.http.get<JobApplication>(`${this.url}/uj/${id}/${jobIdToDeleteApplicationFrom}`,
      {
        headers: new HttpHeaders({"Authorization": "Bearer " + token})
      });
  }
}
