import { Injectable } from '@angular/core';
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {Experience} from "./experience";

@Injectable({
  providedIn: 'root'
})
export class ExperienceService {

  private url = environment.apiBaseUrl + '/xp';
  constructor(private http: HttpClient) {  }

  public hideExperience(experience: Experience) {
    return this.http.post<Experience>(`${this.url}/hide`, experience);
  }

  public showExperience(experience: Experience) {
    return this.http.post<Experience>(`${this.url}/show`, experience);
  }

  public deleteExperience(id: number) {
    return this.http.delete<any>(`${this.url}/delete/${id}`);
  }
}
