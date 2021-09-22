import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {Account} from "../account/account";
import {Job} from "./job";
import {environment} from "../../environments/environment";
import {JobRequest} from "./job-request";

@Injectable({
  providedIn: 'root'
})
export class JobService {

  private url = environment.apiBaseUrl + "/jobs";
  constructor(private http: HttpClient) {  }

  public getAllJobs(): Observable<Job[]> {
    return this.http.get<Job[]>(`${this.url}/all`);
  }

  public addJob(jobr: JobRequest) {
    return this.http.post<Job>(`${this.url}/add`, jobr);
  }

  public deleteJob(jobIdToDelete: number): Observable<void> {
    return this.http.delete<any>(`${this.url}/delete/${jobIdToDelete}`);
  }

  public editJob(jId: number, newJobRequest: JobRequest, token: any): Observable<Job> {
    return this.http.put<Job>(`${this.url}/update/${jId}`, newJobRequest,
      {
        headers: new HttpHeaders({"Authorization" : "Bearer " + token})
      })
  }
}
