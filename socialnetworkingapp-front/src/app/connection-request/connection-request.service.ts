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

  public getAllRequests(): Observable<ConnectionRequest[]> {
    return this.http.get<ConnectionRequest[]>(`${this.url}/all-requests`)
  }
  public checkForPendingRequest(receiverEmail: string): Observable<number> {
    return this.http.get<number>(`${this.url}/search-pending/${receiverEmail}`)
  }

  public checkIfHasReceivedRequest(email: string): Observable<number> {
    return this.http.get<number>(`${this.url}/search-received/${email}`)
  }

  public addRequest(email: string): Observable<any> {
    return this.http.post<any>(`${this.url}/add/${email}`, {});
  }


  public rejectRequest(email: string): Observable<any> {
    return this.http.put<any>(`${this.url}/reject/${email}`, {});
  }

  public removeRequest(connectionRequestId: number): Observable<any> {
    return this.http.delete<any>(`${this.url}/delete/${connectionRequestId}`);
  }

  public deleteFromNetwork(otherEmail: string): Observable<any> {
    return this.http.delete<any>(`${environment.apiBaseUrl}/accounts/network/delete/${otherEmail}`);
  }

  public acceptRequest(reqId: number): Observable<any> {
    return this.http.put<any>(`${this.url}/accept/${reqId}`, {});
  }
}
