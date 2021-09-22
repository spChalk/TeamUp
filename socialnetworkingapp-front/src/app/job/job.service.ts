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

  public getAllJobs(token: string): Observable<Job[]> {
    return this.http.get<Job[]>(`${this.url}/all`,
      {
        headers: new HttpHeaders( {
          "Authorization": "Bearer " + token,
        })
      });
  }

  public addJob(jobr: JobRequest, token: string) {
    return this.http.post<Job>(`${this.url}/add`, jobr,
      {
        headers: new HttpHeaders({
          "Authorization": "Bearer " + token
        })
      });
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
