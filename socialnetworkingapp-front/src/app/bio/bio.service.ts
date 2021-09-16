import { Injectable } from '@angular/core';
import {Bio} from "./bio";
import {AccountService} from "../account/account.service";
import {Account} from "../account/account";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class BioService {

  private accountService: AccountService;
  private account: Account;

  constructor(private http: HttpClient) { }

  addBio(bio: Bio) {
    return this.http.post<Bio>(`${environment.apiBaseUrl}/bio/add`, bio);
  }

  getBioByAccountId(id: number) {
    return this.http.get<Bio>(`${environment.apiBaseUrl}/bio/acc_ref/${id}`)
  }

  deleteBioByAccountId(id: number) {
    return this.http.delete<void>(`${environment.apiBaseUrl}/bio/delete/${id}`);
  }

  updateBio(bio: Bio) {
    return this.http.put<Bio>(`${environment.apiBaseUrl}/bio/update`, bio);
  }

  deleteBioById(bioId: number) {
    return this.http.delete<void>(`${environment.apiBaseUrl}/bio/delete/${bioId}`);
  }
}
