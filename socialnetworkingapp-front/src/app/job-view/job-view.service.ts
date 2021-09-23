import { Injectable } from '@angular/core';
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {JobView} from "./job-view";

@Injectable({
  providedIn: 'root'
})
export class JobViewService {

  private url = environment.apiBaseUrl + "/job_views";
  constructor(private http: HttpClient) {  }

  public addView(jid: number) {
    return this.http.post<JobView>(`${this.url}/add/${jid}`, {});
  }

  public getViewsByJob(id: number) {
    return this.http.get<number>(`${this.url}/sum/${id}`);
  }
}
