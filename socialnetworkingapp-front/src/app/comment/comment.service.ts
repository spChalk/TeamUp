import { Injectable } from '@angular/core';
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  private url = environment.apiBaseUrl + '/comments';
  constructor(private http: HttpClient) {  }

  public getCommentsOfPost(pid: number): Observable<Comment[]> {
    return this.http.get<Comment[]>(`${this.url}/all/of-post/${pid}`);
  }

  public addComment(pid: number, payload: string): Observable<Comment> {
    return this.http.post<Comment>(`${this.url}/add/${pid}`, payload);
  }

  public updateComment(cid: number, payload: string): Observable<Comment> {
    return this.http.put<Comment>(`${this.url}/update/${cid}`, payload);
  }

  public deleteComment(lid: number): Observable<any> {
    return this.http.delete<any>(`${this.url}/delete/${lid}`);
  }
}
