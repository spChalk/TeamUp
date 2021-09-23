import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable, OnDestroy } from '@angular/core';
import { Observable , Subject, timer} from 'rxjs';
import { retry, switchMap, takeUntil, share } from 'rxjs/operators';
import { Account } from '../account/account';
import { environment } from 'src/environments/environment';
import { FromEventTarget } from 'rxjs/internal/observable/fromEvent';

export interface Message{
  payload : string;
  senderEmail : string;
  receiverEmail : string;

  senderFirstName: string;
  senderLastName: string;

  receiverFirstName: string;
  receiverLastName: string;
  date : Date;
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
  // private allMessages$ : Observable<Message[]>;
  public allFriends$ : Observable<Friends[]>;
  private stopPolling = new Subject();

  constructor(private http: HttpClient) {
  }

  getAllMessages(user : string, friend : string ): Observable<Message[]>{
    let httpOptions = { headers: new HttpHeaders(
      { 'Content-Type': 'application/json', })};

    return this.http.post<Message[]>(`${this.url}/chat`,
      {"senderEmail" :user, "receiverEmail": friend},httpOptions);

  }

  sendMessage(payload: string, receiverMail: string ): Observable<Message>{
    let httpOptions = { headers: new HttpHeaders(
      { 'Content-Type': 'application/json', })};

      console.log(payload)
    return this.http.post<Message>(`${this.url}/send`,
      {receiverMail, payload},httpOptions);
    }

  // getAllFriends(): Observable<Set<Friends>>{
    // this.allFriends$ = timer(1, 3000).pipe(
    //   switchMap(() => 
    //   this.http.get<Set<Friends>> (this.url + "/friends")),
    //   retry(),
    //   share(),
    //   takeUntil(this.stopPolling)
    // );
    // return this.allFriends$;
  // }

  // ngOnDestroy(){
  //   this.stopPolling.next();
  // }
}
