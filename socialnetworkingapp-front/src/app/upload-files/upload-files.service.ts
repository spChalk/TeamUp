import { Injectable } from '@angular/core';
import { HttpClient, HttpRequest, HttpHeaders, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs';
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class UploadFileService {

  constructor(private http: HttpClient) { }

  public uploadUser(file: File, token: string): Observable<HttpEvent<any>> {
    const formData: FormData = new FormData();

    formData.append('file', file);

    const req = new HttpRequest("POST", `${environment.apiBaseUrl}/upload-user`, formData, {
      reportProgress: true,
      responseType: 'json'
    });

    return this.http.request(req);
  }

  public uploadAdmin(file: File, email: string, token: string): Observable<HttpEvent<any>> {
    const formData: FormData = new FormData();

    formData.append('file', file);
    formData.append('email', email);

    const req = new HttpRequest("POST", `${environment.apiBaseUrl}/upload-admin`, formData, {
      reportProgress: true,
      responseType: 'json'
    });

    return this.http.request(req);
  }

  public uploadPost(file: File, postId: number, token: string): Observable<HttpEvent<any>> {
    const formData: FormData = new FormData();

    formData.append('file', file);
    formData.append('postId', postId.toString());

    const req = new HttpRequest("POST", `${environment.apiBaseUrl}/upload-post`, formData, {
      reportProgress: true,
      responseType: 'json'
    });

    return this.http.request(req);
  }

  getFiles(): Observable<any> {
    return this.http.get(`${environment.apiBaseUrl}/files`);
  }

  delete(imageUrl: string) {
    return this.http.delete(`${environment.apiBaseUrl}/files/delete/${imageUrl}`);
  }
}
