import { Injectable } from '@angular/core';
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {Account} from "../account/account";
import {Job} from "../job/job";

@Injectable({
  providedIn: 'root'
})
export class TagsService {

  private url = environment.apiBaseUrl;
  constructor(private http: HttpClient) {}

  public addAccountTag(email: string, tag: string) {
    return this.http.post<Account>(`${this.url}/accounts/tags/add/${tag}`, email);
  }

  public addJobTag(id: number, tag: string) {
    return this.http.post<Job>(`${this.url}/jobs/tags/add/${tag}`, id);
  }
}
