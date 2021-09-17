import { HttpClient, HttpHeaderResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import {Login} from '../login/login';
import { environment } from 'src/environments/environment';
import {map} from 'rxjs/operators'
import { LoginComponent } from '../login';
import { LocalStorageService } from 'ngx-webstorage';
import { AccountService } from '../account/account.service';
import { Account } from '../account/account';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService{

    private currentUser: string;
    private loggedIn = false ;
    private role = 'USER';

    constructor(private http: HttpClient,private localStorage : LocalStorageService , private accountService : AccountService) { 
    }

    public logIn(credentials: Login): Observable<boolean> {
    return this.http.post<any>(`${environment.apiBaseUrl}/login`, credentials).pipe(map(data => {
        this.localStorage.store('token', data.token);
        this.localStorage.store('username', data.username)
        this.localStorage.store('role', data.role)
        this.currentUser = data.username;
        this.role = data.role;
        this.loggedIn = true;
        return true;
    }));

  }

  public isLoggedIn(){
        return (this.getJWT() != null);
  }
  public getCurrentUser(){
      return this.localStorage.retrieve('username');
  }
  public isAdmin(){
        return this.localStorage.retrieve('role') =='ADMIN';
  }

  public getJWT(){
      return this.localStorage.retrieve('token');
  }

  logOut(){
      this.localStorage.clear();
      this.loggedIn = false;
  }
}
