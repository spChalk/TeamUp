import { Injectable } from '@angular/core';
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {ConnectionRequest} from "./ConnectionRequest";

@Injectable({
  providedIn: 'root'
})
export class ConnectionRequestService {

  private url = environment.apiBaseUrl + '/crequest';
  constructor(private http: HttpClient) {  }

  public checkForPendingRequest(senderEmail: string, receiverEmail: string): Observable<number> {
    return this.http.get<number>(`${this.url}/search/${senderEmail}/${receiverEmail}`)
  }

  public addRequest(connectionRequest: ConnectionRequest): Observable<ConnectionRequest> {
    return this.http.post<ConnectionRequest>(`${this.url}/add`, connectionRequest);
  }

  public removeRequest(connectionRequestId: number): Observable<any> {
    return this.http.delete<any>(`${this.url}/delete/${connectionRequestId}`);
  }

  public deleteFromNetwork(myEmail: string, otherEmail: string): Observable<any> {
    return this.http.delete<any>(`${environment.apiBaseUrl}/accounts/network/${myEmail}/delete/${otherEmail}`);
  }
}
