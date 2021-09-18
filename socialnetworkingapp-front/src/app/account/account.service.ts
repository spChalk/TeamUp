
import {Observable, throwError} from 'rxjs';
import {Injectable} from "@angular/core";
import {HttpClient, HttpErrorResponse, HttpEventType, HttpHeaders} from "@angular/common/http";
import {Account} from "./account";
import {environment} from "../../environments/environment";
import {Login} from "../login/login";
import {Bio} from "../bio/bio";
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})

export class AccountService {

  private url = environment.apiBaseUrl + '/accounts';
  constructor(private http: HttpClient) {  }

  public fetchUser(email:string): Observable<Account> {
    return this.http.get<Account>(`${this.url}/find/mail/${email}`);
  }
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

                console.log(account)
    return this.http.post<Account>(`${environment.apiBaseUrl}/register`,
      account,httpOptions);
  }

  public updateAccount(account: Account): Observable<Account> {
    return this.http.put<Account>(`${this.url}/update`, account);
  }

  public deleteAccountById(account_id: number): Observable<void> {
    return this.http.delete<void>(`${this.url}/delete/${account_id}`);
  }

  public getNetworkByEmail(email: string) {
    return this.http.get<Account[]>(`${this.url}/network/all/${email}`);
  }

  /* TODO: addFriend */

  public addBio(email: string, bio: Bio) {
    return this.http.post<Bio>(`${this.url}/bio/add`, {email, bio});
  }

  public deleteBio(id: number) {
    return this.http.delete<any>(`${this.url}/bio/delete/${id}`);
  }

  public hideTags(account: Account) {
    return this.http.put<Account>(`${this.url}/hide-tags`, account);
  }

  public showTags(account: Account) {
    return this.http.put<Account>(`${this.url}/show-tags`, account);
  }
}
