import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {HomeComponent} from "./home";
import {LoginComponent} from "./login";
import {RegisterComponent} from "./register";
import {WelcomeComponent} from "./welcome/welcome.component";
import {JobComponent} from "./job/job.component";
import {AboutComponent} from "./about/about.component";
import {SettingsComponent} from "./settings/settings.component";

const routes: Routes = [
  { path: '', component: WelcomeComponent },
  { path: 'home', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'job', component: JobComponent },
  { path: 'about', component: AboutComponent },
  { path: 'settings', component: SettingsComponent },

  // otherwise, redirect to home
  { path: '**', redirectTo: ''}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
