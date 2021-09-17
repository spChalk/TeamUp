import { Injectable } from '@angular/core';
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {Experience} from "../experience/experience";
import {Education} from "./education";

@Injectable({
  providedIn: 'root'
})
export class EducationService {

  private url = environment.apiBaseUrl + '/education';
  constructor(private http: HttpClient) {  }

  public hideEducation(edu: Education) {
    return this.http.post<Education>(`${this.url}/hide`, edu);
  }

  public showEducation(edu: Education) {
    return this.http.post<Education>(`${this.url}/show`, edu);
  }
}
