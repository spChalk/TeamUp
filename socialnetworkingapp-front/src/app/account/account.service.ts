
import {Observable} from 'rxjs';
import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Account} from "./account";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})

export class AccountService {

  constructor(private http: HttpClient) {  }

  public getAllAccounts(): Observable<Account[]> {
    return this.http.get<Account[]>(`${environment.apiBaseUrl}/accounts/all`);
  }

  public getAccountById(account_id: number): Observable<Account> {
    return this.http.get<Account>(`${environment.apiBaseUrl}/accounts/find/${account_id}`);
  }

  public addAccount(account: Account): Observable<Account> {
    return this.http.post<Account>(`${environment.apiBaseUrl}/accounts/add`,
      {
        headers: new HttpHeaders({'Content-Type': 'application/json'}),
      });
  }

  public updateAccount(account: Account): Observable<Account> {
    return this.http.put<Account>(`${environment.apiBaseUrl}/accounts/update`,
      {
        headers: new HttpHeaders({'Content-Type': 'application/json'}),
      });
  }

  public deleteAccountById(account_id: number): Observable<void> {
    return this.http.delete<void>(`${environment.apiBaseUrl}/accounts/delete/${account_id}`);
  }

  /* TODO: addFriend */
}
