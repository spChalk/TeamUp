import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import {Account} from "../account/account";
import {environment} from "../../environments/environment";

@Injectable({ providedIn: 'root' })
export class AuthService {
  private currentUserSubject: BehaviorSubject<Account>;
  public currentUser: Observable<Account>;

  constructor(private http: HttpClient) {
    this.currentUserSubject = new BehaviorSubject<Account>(JSON.parse(<string>localStorage.getItem('currentUser')));
    this.currentUser = this.currentUserSubject.asObservable();
  }

  public get currentUserValue(): Account {
    return this.currentUserSubject.value;
  }

  login(username: string, password: string): Observable<Account> {
    return this.http.post<any>(`${environment.apiBaseUrl}/login`, { username, password })
      .pipe(map(user => {
        // store user details and jwt token in local storage to keep user logged in between page refreshes
        localStorage.setItem('currentUser', JSON.stringify(user));
        this.currentUserSubject.next(user);
        return user;
      }));
  }

  logout() {
    // remove user from local storage to log user out
    localStorage.removeItem('currentUser');
    this.currentUserSubject.next(null);
  }
}
