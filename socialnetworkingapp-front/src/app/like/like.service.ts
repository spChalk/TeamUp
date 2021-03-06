import { Injectable } from '@angular/core';
import {environment} from "../../environments/environment";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {Like} from "./like";

@Injectable({
  providedIn: 'root'
})
export class LikeService {

  private url = environment.apiBaseUrl + '/likes';
  constructor(private http: HttpClient) {  }

  public getLikesOfPost(pid: number): Observable<Like[]> {
    return this.http.get<Like[]>(`${this.url}/all/of-post/${pid}`);
  }

  public addLike(pid: number): Observable<Like> {
    return this.http.post<Like>(`${this.url}/add/${pid}`, {},);
  }

  public deleteLike(lid: number): Observable<any> {
    return this.http.delete<any>(`${this.url}/delete/${lid}`);
  }

  public getAllLikesOfMyPosts(): Observable<Like[]> {
    return this.http.get<Like[]>(`${this.url}/all-likes-of-my-posts`);
  }

}
