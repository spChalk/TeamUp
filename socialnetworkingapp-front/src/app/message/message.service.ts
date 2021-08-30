import {Observable} from 'rxjs';
import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {Message} from "./message";


@Injectable({
  providedIn: 'root'
})

export class MessageService {

  constructor(private http: HttpClient) { }

  public getConversation(): Observable<Message[]> {
    return this.http.get<Message[]>(`${environment.apiBaseUrl}/messages/chat`);
  }

  public addMessage(message: Message): Observable<Message> {
    return this.http.post<Message>(`${environment.apiBaseUrl}/messages/add`,
      {
        headers: new HttpHeaders({'Content-Type': 'application/json'}),
      });
  }

  public updateMessage(message: Message): Observable<Message> {
    return this.http.put<Message>(`${environment.apiBaseUrl}/messages/update`,
      {
        headers: new HttpHeaders({'Content-Type': 'application/json'}),
      });
  }

  public deleteMessage(message_id: number): Observable<void> {
    return this.http.delete<void>(`${environment.apiBaseUrl}/messages/delete/${message_id}`);
  }
}
