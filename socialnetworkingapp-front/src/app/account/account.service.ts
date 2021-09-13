
import {Observable} from 'rxjs';
import {Injectable} from "@angular/core";
import {HttpClient, HttpErrorResponse, HttpEventType, HttpHeaders} from "@angular/common/http";
import {Account} from "./account";
import {environment} from "../../environments/environment";
import {Login} from "../login/login";
import {Bio} from "../bio/bio";

@Injectable({
  providedIn: 'root'
})

export class AccountService {

  private url = environment.apiBaseUrl + '/accounts';
  constructor(private http: HttpClient) {  }

  public getAllAccounts(): Observable<Account[]> {
    return this.http.get<Account[]>(`${this.url}/all`,
      {
        headers: new HttpHeaders( {
          "Access-Control-Allow-Origin": "http://localhost:4200",
        })
      });
  }

  public getAccountById(account_id: number): Observable<Account> {
    return this.http.get<Account>(`${this.url}/find/id/${account_id}`);
  }

  public getAccountByEmail(email: string): Observable<Account> {
    return this.http.get<Account>(`${this.url}/find/mail/${email}`, {
      headers: new HttpHeaders( {
        "Access-Control-Allow-Origin": "http://localhost:4200",
      })
    });
  }

  public getAccountsBySimilarEmail(keyword: string): Observable<Account[]> {
    return this.http.get<Account[]>(`${this.url}/find/mails/${keyword}`, {
      headers: new HttpHeaders( {
        "Access-Control-Allow-Origin": "http://localhost:4200",
      })
    });
  }

  public getAccountsBySimilarName(keyword: string): Observable<Account[]> {
    return this.http.get<Account[]>(`${this.url}/find/names/${keyword}`, {
      headers: new HttpHeaders( {
        "Access-Control-Allow-Origin": "http://localhost:4200",
      })
    });
  }

  public registerAccount(account: Account): Observable<Account> {

    /* https://www.gitmemory.com/issue/angular/angular/18396/490910837 */
    let httpOptions = { headers: new HttpHeaders(
      { 'Content-Type': 'application/json', })};

    return this.http.post<Account>(`${environment.apiBaseUrl}/register`,
      account,
      httpOptions);
  }

  public logIn(credentials: Login): Observable<Login> {
    return this.http.post<Login>(`${environment.apiBaseUrl}/login`, credentials);
  }

  public updateAccount(account: Account): Observable<Account> {
    return this.http.put<Account>(`${this.url}/update`, account);
  }

  public deleteAccountById(account_id: number): Observable<void> {
    return this.http.delete<void>(`${this.url}/delete/${account_id}`);
  }

  public getNetworkById(uid: number) {
    return this.http.get<Account[]>(`${this.url}/network/all/${uid}`);
  }

  /* TODO: addFriend */

}
