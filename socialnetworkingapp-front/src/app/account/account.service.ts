
import {Observable} from 'rxjs';
import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Account} from "./account";
import {environment} from "../../environments/environment";
import {Login} from "../login/login";

@Injectable({
  providedIn: 'root'
})

export class AccountService {

  private url = environment.apiBaseUrl + '/accounts';
  constructor(private http: HttpClient) {  }

  public getAllAccounts(): Observable<Account[]> {
    return this.http.get<Account[]>(`${this.url}/all`, {
      headers: new HttpHeaders({"Authorization": "Bearer eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJzY0BnbWFpbC5jb20iLCJhdXRob3JpdGllcyI6W3siYXV0aG9yaXR5IjoiVVNFUiJ9XSwiaWF0IjoxNjMwNjE2NDAwLCJleHAiOjE2MzA3MDI4MDB9.WD222Ijsjg_5rSXg_mRSqGfgNUT5azLew1rIV6Y2RU5x-ZDq6VcbrwPWT4x0_alr"})
    });
  }

  public getAccountById(account_id: number): Observable<Account> {
    return this.http.get<Account>(`${this.url}/find/${account_id}`);
  }

  public registerAccount(account: Account): Observable<Account> {

    /* https://www.gitmemory.com/issue/angular/angular/18396/490910837 */
    let httpOptions = { headers: new HttpHeaders(
      { 'Content-Type': 'application/json', }),
                responseType: 'text' as 'json' };

    return this.http.post<Account>(`${environment.apiBaseUrl}/register`,
      account,
      httpOptions);
  }

  public logIn(credentials: Login): Observable<Login> {
    return this.http.post<Login>(`${environment.apiBaseUrl}/login`, credentials);
  }

  public addAccount(account: Account): Observable<Account> {
    return this.http.post<Account>(`${this.url}/add`,
      {
        headers: new HttpHeaders({'Content-Type': 'application/json'}),
      });
  }

  public updateAccount(account: Account): Observable<Account> {
    return this.http.put<Account>(`${this.url}/update`,
      {
        headers: new HttpHeaders({'Content-Type': 'application/json'}),
      });
  }

  public deleteAccountById(account_id: number): Observable<void> {
    return this.http.delete<void>(`${this.url}/delete/${account_id}`);
  }

  /* TODO: addFriend */
}
