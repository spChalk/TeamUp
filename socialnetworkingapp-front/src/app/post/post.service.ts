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

  public addPost(payload: string): Observable<Post> {
    return this.http.post<Post>(`${this.url}/add`, payload );
  }

  public getPosts(): Observable<Post[]> {
    return this.http.get<Post[]>(`${this.url}/all`);
  }

  public deletePost(pid: number): Observable<any> {
    return this.http.delete<void>(`${this.url}/delete/${pid}`);
  }
}
