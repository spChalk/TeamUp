import { Injectable } from '@angular/core';
import {environment} from "../../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {Account} from "../../account/account";
import {Observable} from "rxjs";
import {AccountInterest} from "./acc_interests";

@Injectable({
  providedIn: 'root'
})
export class AccInterestsService {

  private url = environment.apiBaseUrl + "/accounts/tags";
  constructor(private http: HttpClient) { }

  public addTag(account: Account, interest: number): any {
    return this.http.post<any>(`${this.url}/add`, new AccountInterest(account, interest));
  }
}
