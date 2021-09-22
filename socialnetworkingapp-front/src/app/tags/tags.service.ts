import { Injectable } from '@angular/core';
import {environment} from "../../environments/environment";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Account} from "../account/account";
import {Job} from "../job/job";
import { Observable } from 'rxjs';
import { Tag } from './Tag';

@Injectable({
  providedIn: 'root'
})
export class TagsService {

  private url = environment.apiBaseUrl;
  constructor(private http: HttpClient) {}

  public addAccountTag(tag: string) {
    return this.http.post<Account>(`${this.url}/accounts/tags/add`, tag);
  }

  public addAllAccountTags(tags: string[]) {
    return this.http.post<Account>(`${this.url}/accounts/tags/add/all`, tags);
  }

  public addJobTag(id: number, tag: string) {
    return this.http.post<Job>(`${this.url}/jobs/tags/add/${tag}`, id);
  }

  public getAllTags(): Observable<Tag[]> {
    return this.http.get<Tag[]>(`${this.url}/tags/all`,
      {
        headers: new HttpHeaders( {
          "Access-Control-Allow-Origin": "http://localhost:4200",
        })
      });
  }

  public getUserTags(): Observable<Tag[]> {
    return this.http.get<Tag[]>(`${this.url}/accounts/myTags`,
      {
        headers: new HttpHeaders( {
          "Access-Control-Allow-Origin": "http://localhost:4200",
        })
      });
  }

}
