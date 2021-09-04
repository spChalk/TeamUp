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
import {AuthGuardService} from "./auth/auth.guard.service";
import {AuthGuard} from "./auth/auth.guard";

const routes: Routes = [
  { path: '', component: WelcomeComponent },
  { path: 'home', component: HomeComponent, canActivate:[AuthGuard]  },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'job', component: JobComponent, canActivate:[AuthGuard] },
  { path: 'about', component: AboutComponent },
  { path: 'settings', component: SettingsComponent, canActivate:[AuthGuard] },
  { path: 'chat', component: ChatComponent, canActivate:[AuthGuard] },
  { path: 'account', component: AccountComponent, canActivate:[AuthGuard] },
  { path: 'admin', component: AdminComponent, canActivate:[AuthGuard] },
  { path: 'network', component: NetworkComponent, canActivate:[AuthGuard] },

  // otherwise, redirect to home
  { path: '**', redirectTo: ''}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
