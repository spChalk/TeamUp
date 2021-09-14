import { Injectable } from '@angular/core';
import { HttpClient, HttpRequest, HttpHeaders, HttpEvent } from '@angular/common/http';
import { Observable } from 'rxjs';
import {environment} from "../../environments/environment";

/* https://www.bezkoder.com/angular-spring-boot-file-upload/ */

@Injectable({
  providedIn: 'root'
})
export class UploadFileService {

  constructor(private http: HttpClient) { }

  upload(file: File, email: string): Observable<HttpEvent<any>> {
    const formData: FormData = new FormData();

    formData.append('file', file);
    formData.append('email',  email);

    const req = new HttpRequest("POST", `${environment.apiBaseUrl}/upload`, formData, {
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
