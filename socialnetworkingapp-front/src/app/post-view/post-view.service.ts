import { Injectable } from '@angular/core';
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {PostView} from "./post-view";

@Injectable({
  providedIn: 'root'
})
export class PostViewService {

  private url = environment.apiBaseUrl + "/post_views";
  constructor(private http: HttpClient) {  }

  public addView(pid: number) {
    return this.http.post<PostView>(`${this.url}/add/${pid}`, {});
  }

  public getViewsByPost(id: number): Observable<PostView[]> {
    return this.http.get< PostView[]>(`${this.url}/${id}`);
  }
}
