import { Injectable } from '@angular/core';
import {Account} from "../account/account";
import {Login} from "../login/login";
import {environment} from "../../environments/environment";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {JSONFile} from "@angular/cli/utilities/json-file";
import {Byte} from "@angular/compiler/src/util";

@Injectable({
  providedIn: 'root'
})
export class ExportService {

  constructor(private http: HttpClient) { }

  public exportJSON(accounts: any[]) {

    this.http.post(`${environment.apiBaseUrl}/export/json`, accounts, {
      responseType: 'arraybuffer'
    }).subscribe(
      response => downLoadFile(response, "application/json"));

    function downLoadFile(data: any, type: string) {
      let blob = new Blob([data], { type: type});
      let url = window.URL.createObjectURL(blob);
      let pwa = window.open(url);
      if (!pwa || pwa.closed || typeof pwa.closed == 'undefined') {
        alert( 'Please disable your Pop-up blocker and try again.');
      }
    }


    /*return this.http.post<Byte[]>(`${environment.apiBaseUrl}/export/json`,
      accounts,
      {
        headers: new HttpHeaders( {
          "Access-Control-Allow-Origin": "http://localhost:4200",
        })
      });*/
  }

  public exportXML(accounts: any[]) {

    let httpOptions = { headers: new HttpHeaders(
        { 'Content-Type': 'application/json'})};

    return this.http.post<Byte[]>(`${environment.apiBaseUrl}/export/xml`,
      accounts,
      httpOptions);
  }
}
