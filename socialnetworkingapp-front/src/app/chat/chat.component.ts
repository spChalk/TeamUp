import { Component, OnInit, Sanitizer } from '@angular/core';
import { Account } from '../account/account';
import { AccountService } from '../account/account.service';
import { AuthenticationService } from '../authentication';
import { ChatService, Friends } from './chat.service';
import { Message } from './chat.service';
import { DomSanitizer, SafeHtml, SafeScript } from '@angular/platform-browser';
import { ActivatedRoute } from '@angular/router';
import { retry, switchMap, takeUntil, share } from 'rxjs/operators';
import { HttpClient } from '@angular/common/http';
import { Observable, Subject, timer } from 'rxjs';
import { environment } from 'src/environments/environment';
import { FormBuilder, FormGroup, FormControl } from '@angular/forms';


@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})

export class ChatComponent implements OnInit {

  messages : Message[] ;
  account: Account;
  friends: Friends[];
  allFriends$: Observable<Friends[]>;
  allMessages$: Observable<Message[]>;

  private stopPolling = new Subject();

  private url = environment.apiBaseUrl + '/messages';
  public queryParams: string;
  public messageForm: FormGroup;
  public sendForm: FormGroup;

  constructor(private chatService: ChatService, private accountService: AccountService, private authenticationService: AuthenticationService, private fb: FormBuilder, private route: ActivatedRoute, private http: HttpClient) {

    this.allFriends$ = timer(1, 10000).pipe(
      switchMap(() =>
        this.http.get<Friends[]>(this.url + "/friends")),
      retry(),
      share(),
      takeUntil(this.stopPolling)
    );

    this.allMessages$ = timer(1, 10000).pipe(
      switchMap(() =>
        this.http.post<Message[]>(this.url + "/chat", this.queryParams)),
      retry(),
      share(),
      takeUntil(this.stopPolling)
    );

  }

  ngOnInit(): void {

    this.messageForm = this.fb.group({
      payload: new FormControl('')
    },
    );

    this.sendForm = this.fb.group({
      receiver: new FormControl(''),
      payload: new FormControl('')
    },
    );
    this.accountService.fetchUser(this.authenticationService.getCurrentUser()).subscribe(
      (response: Account) => {
        this.account = response;
      },
      (error: any) => {
        console.log(error);
      }
    )
    this.allFriends$.subscribe(
      (response: Friends[]) => {
        this.friends = response;
        console.log(response);
      },
      (error: any) => {
        console.log(error);
      }
    )

    this.route.queryParams.subscribe(params => {
      this.queryParams = params.receiverEmail;
      this.allMessages$.subscribe(
          (resp: Message[])=>{
              this.messages = resp;
          },
          (error : any)=>{
              console.log(error);
          }
      )
    });

  }

  public getMessages(withFriend: string) {
    this.chatService.getAllMessages(this.authenticationService.getCurrentUser(), withFriend).subscribe(
      (resp: Message[]) => {
        this.messages = resp;
      },
      (error: any) => {
        console.log(error);
      }
    )
  }

  public sendMessage(messageForm: FormGroup, receiver: string) {
    this.chatService.sendMessage(messageForm.get('payload')?.value, receiver).subscribe(
      (resp: Message) => {
        console.log(resp);
        // window.location.reload();
        this.messageForm.reset();

      },
      (error: any) => {
        console.log(error);
      }
    )
  }

  public onClickModal(data: any, mode: string): void {

    const container = document.getElementById('main-container');

    const button = document.createElement('button');
    button.type = 'button';
    button.style.display = 'none';
    button.setAttribute('data-toggle', 'modal');

    if (mode === 'send') {
      button.setAttribute('data-target', '#send');
    }
    if (container != null) {
      container.appendChild(button);
      button.click();
    }
  }

  public onSendMessage(sendForm: FormGroup) {
    this.chatService.sendMessage(sendForm.get('payload')?.value, sendForm.get('receiver')?.value).subscribe(
      (resp: Message) => {
        console.log(resp);
        window.location.reload();
      },
      (error: any) => {
        console.log(error);
        this.sendForm.reset();
      }
    )
  }

  ngOnDestroy() {
    this.stopPolling.next();
  }

}
