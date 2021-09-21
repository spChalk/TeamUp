import { Injectable } from '@angular/core';
import {environment} from "../../environments/environment";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {Account} from "../account/account";
import {Post} from "./post";

@Injectable({
  providedIn: 'root'
})
export class PostService {

  private url = environment.apiBaseUrl + '/posts';
  constructor(private http: HttpClient) {  }

  public addPost(payload: string, token: string): Observable<Post> {
    return this.http.post<Post>(`${this.url}/add`, payload , {
      headers: new HttpHeaders({"Authorization" : "Bearer " + token})
    });
  }

  /* TODO: TEMPORARY. When homepage is ready, get posts by network etc.. */
  public getPosts(token: string): Observable<Post[]> {
    return this.http.get<Post[]>(`${this.url}/all`, {
      headers: new HttpHeaders({"Authorization" : "Bearer " + token})
    });
  }

  public deletePost(pid: number): Observable<any> {
    return this.http.delete<void>(`${this.url}/delete/${pid}`);
  }
}