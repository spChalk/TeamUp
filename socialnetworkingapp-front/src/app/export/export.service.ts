import { Injectable } from '@angular/core';
import {Account} from "../account/account";
import {Login} from "../login/login";
import {environment} from "../../environments/environment";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {JSONFile} from "@angular/cli/utilities/json-file";
import {Byte} from "@angular/compiler/src/util";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ExportService {

  constructor(private http: HttpClient) { }

  public exportJSON(accountIds: number[]) {

    let httpOptions = { headers: new HttpHeaders(
        { 'Content-Type': 'application/json', }),
      responseType: 'text' as 'json' };

    return this.http.post<string>(`${environment.apiBaseUrl}/export/json`,
      accountIds,
      httpOptions);
  }

  public exportXML(accountIds: number[]) {

    let httpOptions = { headers: new HttpHeaders(
        { 'Content-Type': 'application/json', }),
      responseType: 'text' as 'json' };

    return this.http.post<string>(`${environment.apiBaseUrl}/export/xml`,
      accountIds,
      httpOptions);
  }

  public downloadFile(url: string): Observable<Blob> {
    return this.http.get(url, {responseType: 'blob'});
  }
}
