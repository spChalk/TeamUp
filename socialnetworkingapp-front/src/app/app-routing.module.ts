import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {HomeComponent} from "./home";
import {LoginComponent} from "./login";
import {RegisterComponent} from "./register";
import {WelcomeComponent} from "./welcome/welcome.component";
import {JobComponent} from "./job/job.component";
import {AboutComponent} from "./about/about.component";
import {SettingsComponent} from "./settings/settings.component";
import {ChatComponent} from "./chat/chat.component";
import {AccountComponent} from "./account/account.component";
import {AdminComponent} from "./admin/admin.component";
import {NetworkComponent} from "./network/network.component";
import { AuthGuardService } from './authentication';

const routes: Routes = [
  { path: '', component: WelcomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'about', component: AboutComponent, },
  { path: 'home', component: HomeComponent , canActivate: [AuthGuardService]},
  { path: 'jobs', component: JobComponent,canActivate: [AuthGuardService] },
  { path: 'settings', component: SettingsComponent, canActivate: [AuthGuardService] },
  { path: 'chat', component: ChatComponent , canActivate: [AuthGuardService]},
  { path: 'account/:id', component: AccountComponent ,canActivate: [AuthGuardService]},
  { path: 'admin', component: AdminComponent, canActivate: [AuthGuardService] },
  { path: 'network', component: NetworkComponent ,canActivate: [AuthGuardService]},
  { path: 'network/:uid', component: NetworkComponent , canActivate : [AuthGuardService ]},

  // otherwise, redirect to home
  { path: '**', redirectTo: ''}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
