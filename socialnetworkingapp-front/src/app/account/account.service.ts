
import {Observable, throwError} from 'rxjs';
import {Injectable} from "@angular/core";
import {HttpClient, HttpErrorResponse, HttpEventType, HttpHeaders} from "@angular/common/http";
import {Account} from "./account";
import {environment} from "../../environments/environment";
import {Login} from "../login/login";
import {Bio} from "../bio/bio";
import { catchError } from 'rxjs/operators';
import { AccountUpdateRequest } from './accountUpdateRequest';
import { Education } from '../education/education';
import { Experience } from '../experience/experience';

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
    return this.http.get<Account[]>(`${this.url}/all`);
  }
  public getAccountById(account_id: number): Observable<Account> {
    return this.http.get<Account>(`${this.url}/find/id/${account_id}`);
  }

  public getAccountByEmail(email: string): Observable<Account> {
    return this.http.get<Account>(`${this.url}/find/mail/${email}`);
  }

  public getAccountsBySimilarName(keyword: string): Observable<Account[]> {
    return this.http.get<Account[]>(`${this.url}/find/names/${keyword}`);
  }

  public registerAccount(account: Account): Observable<Account> {

    let httpOptions = { headers: new HttpHeaders(
      { 'Content-Type': 'application/json', })};
    return this.http.post<Account>(`${environment.apiBaseUrl}/register`,
      account,httpOptions);
  }

  public updateAccount(account: Account): Observable<Account> {
    return this.http.put<Account>(`${this.url}/update`, account);
  }

  public aboutUpdateAccount(account: AccountUpdateRequest): Observable<Account> {

    let httpOptions = { headers: new HttpHeaders(
      { 'Content-Type': 'application/json', })};
    return this.http.put<Account>(`${this.url}/about-update`, account, httpOptions);
  }

  public updatePassword(newPassword: string): Observable<boolean> {
    return this.http.post<boolean>(`${this.url}/update-password`, newPassword);
  }

  public deleteAccountById(account_id: number): Observable<any> {
    return this.http.delete<any>(`${this.url}/delete/${account_id}`);
  }

  public confirmPassword(password : string):Observable<boolean>{
    return this.http.post<boolean>(`${this.url}/confirmation`, password);
  }

  public addBio(bio: string) : Observable<Bio>{
    let httpOptions = { headers: new HttpHeaders(
      { 'Content-Type': 'application/json', })};
    return this.http.post<Bio>(`${this.url}/bio/add`, bio, httpOptions);
  }

  public addEducation(education: Education) : Observable<Account>{
    console.log(education);
    let httpOptions = { headers: new HttpHeaders(
      { 'Content-Type': 'application/json', })};
    return this.http.post<Account>(`${this.url}/education/add`, education, httpOptions);
  }

  public editEducation(education: Education) : Observable<Education>{
    let httpOptions = { headers: new HttpHeaders(
      { 'Content-Type': 'application/json', })};
    return this.http.post<Education>(`${this.url}/education/update`, education, httpOptions);
  }

  public addExperience(experience: Experience) : Observable<Account>{
    let httpOptions = { headers: new HttpHeaders(
      { 'Content-Type': 'application/json', })};
    return this.http.post<Account>(`${this.url}/experience/add`, experience, httpOptions);
  }

  public editExperience(experience: Experience) : Observable<Experience>{
    let httpOptions = { headers: new HttpHeaders(
      { 'Content-Type': 'application/json', })};
    return this.http.post<Experience>(`${this.url}/experience/update`, experience, httpOptions);
  }

  public deleteBio(id: number) {
    return this.http.delete<any>(`${this.url}/bio/delete/${id}`);
  }

  public hideTags() {
    return this.http.put<Account>(`${this.url}/hide-tags`, null);
  }

  public showTags() {
    return this.http.put<Account>(`${this.url}/show-tags`,null);
  }

  public deleteEducation(id: number) {
    return this.http.delete<any>(`${this.url}/education/delete/${id}`);
  }

  public deleteExperience(id: number) {
    return this.http.delete<any>(`${this.url}/experience/delete/${id}`);
  }
}
