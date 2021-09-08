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

  public getAllJobs(): Observable<Job[]> {
    return this.http.get<Job[]>(`${this.url}/all`,
      {
        headers: new HttpHeaders( {
          "Access-Control-Allow-Origin": "http://localhost:4200",
        })
      });
  }
}
