import { HttpClient, HttpHeaderResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import {Login} from '../login/login';
import { environment } from 'src/environments/environment';
import {map} from 'rxjs/operators'
import { LoginComponent } from '../login';
import { LocalStorageService } from 'ngx-webstorage';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService{

    private currentUser: string;
    private loggedIn = false ;

    constructor(private http: HttpClient,private localStorage : LocalStorageService ) { 
    }

    public logIn(credentials: Login): Observable<boolean> {
    return this.http.post<any>(`${environment.apiBaseUrl}/login`, credentials).pipe(map(data => {
        this.localStorage.store('token', data.token);
        this.localStorage.store('username', data.username)
        this.currentUser = data.username;
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

  public getJWT(){
      return this.localStorage.retrieve('token');
  }

  logOut(){
      this.localStorage.clear();
      this.loggedIn = false;
  }
}
