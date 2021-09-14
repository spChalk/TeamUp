import { Injectable } from '@angular/core';
import {environment} from "../../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Job} from "../../job/job";
import {JobInterest} from "./job_interests";
import {JobInterestsComponent} from "./job-interests.component";

@Injectable({
  providedIn: 'root'
})
export class JobInterestsService {

  private url = environment.apiBaseUrl + "/jobs/tags";
  constructor(private http: HttpClient) { }

  public addTag(job: Job, interest: number): Observable<JobInterest> {
    return this.http.post<JobInterest>(`${this.url}/add`, new JobInterest(job, interest));
  }
}
