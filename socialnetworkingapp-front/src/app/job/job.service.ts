import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {Account} from "../account/account";
import {Job} from "./job";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class JobService {

  private url = environment.apiBaseUrl + "/jobs";
  constructor(private http: HttpClient) {  }

  public getAllJobs(uid: number): Observable<Job[]> {
    return this.http.get<Job[]>(`${this.url}/all/${uid}`,
      {
        headers: new HttpHeaders( {
          "Access-Control-Allow-Origin": "http://localhost:4200",
        })
      });
  }

  public addJob(job: Job) {
    return this.http.post<Job>(`${this.url}/add`, job);
  }
}
