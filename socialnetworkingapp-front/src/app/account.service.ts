
import {Observable} from 'rxjs';
import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Account} from "./account";
import {environment} from "../environments/environment";

@Injectable({
  providedIn: 'root'
})

export class AccountService {
  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) {  }

  public getAllAccounts(): Observable<Account[]> {
    return this.http.get<Account[]>(`${this.apiServerUrl}/accounts/all`);
  }

  public getAccountById(account_id: number): Observable<Account> {
    return this.http.get<Account>(`${this.apiServerUrl}/accounts/find/${account_id}`);
  }

  public addAccount(account: Account): Observable<Account> {
    /* TODO: Remove this later -> */ // @ts-ignore
    return this.http.post<Account>(`${this.apiServerUrl}/accounts/add`);
  }

  public updateAccount(account: Account): Observable<Account> {
    /* TODO: Remove this later -> */ // @ts-ignore
    return this.http.put<Account>(`${this.apiServerUrl}/accounts/update`);
  }

  public deleteAccountById(account_id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl}/accounts/delete/${account_id}`);
  }

  /* TODO: addFriend */
}
