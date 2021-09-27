import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable, OnDestroy } from '@angular/core';
import { Observable , Subject, timer} from 'rxjs';
import { retry, switchMap, takeUntil, share } from 'rxjs/operators';
import { Account } from '../account/account';
import { environment } from 'src/environments/environment';
import { FromEventTarget } from 'rxjs/internal/observable/fromEvent';
import { EMPTY } from 'rxjs'

export interface Message{
  payload : string;
  date : string;

  senderEmail : string;
  senderFirstName: string;
  senderLastName: string;
  senderImageUrl: string;
  senderPhone: string;

  receiverFirstName: string;
  receiverLastName: string;
  receiverEmail : string;
  receiverImageUrl: string;
  receiverPhone: string;
}

export interface Friends{
  firstName:string;
  lastName:String;
  email:String;
  imageUrl :String;
}

@Injectable({
  providedIn: 'root'
})

export class ChatService {

  private url = environment.apiBaseUrl + '/messages';

  constructor(private http: HttpClient) {
  }

  getAllMessages(friend : string ): Observable<Message[]> {
      if(friend != null && friend != "")
        return this.http.post<Message[]>(this.url + "/chat", friend);

        return EMPTY;
  }

  sendMessage(payload: string, receiverMail: string ): Observable<Message>{
    let httpOptions = { headers: new HttpHeaders(
      { 'Content-Type': 'application/json', })};

      console.log(payload)
    return this.http.post<Message>(`${this.url}/send`,
      {receiverMail, payload},httpOptions);
    }

}
