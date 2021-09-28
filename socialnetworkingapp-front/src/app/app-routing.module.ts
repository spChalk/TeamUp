import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {HomeComponent} from "./home";
import {LoginComponent} from "./login";
import {RegisterComponent} from "./register";
import {WelcomeComponent} from "./welcome/welcome.component";
import {JobComponent} from "./job/job.component";
import {SettingsComponent} from "./settings/settings.component";
import {ChatComponent} from "./chat/chat.component";
import {AccountComponent} from "./account/account.component";
import {AdminComponent} from "./admin/admin.component";
import {NetworkComponent} from "./network/network.component";
import { AuthGuardService } from './authentication';
import {VisitAccountComponent} from "./visit-account/visit-account.component";
import { NotificationsComponent } from './notifications/notifications.component';

const routes: Routes = [
  { path: '',             component: WelcomeComponent },
  { path: 'login',        component: LoginComponent },
  { path: 'register',     component: RegisterComponent },
  { path: 'home',         component: HomeComponent ,        canActivate: [AuthGuardService]},
  { path: 'jobs',         component: JobComponent,          canActivate: [AuthGuardService] },
  { path: 'settings',     component: SettingsComponent,     canActivate: [AuthGuardService] },
  { path: 'chat',         component: ChatComponent ,        canActivate: [AuthGuardService]},
  { path: 'account',      component: AccountComponent ,     canActivate: [AuthGuardService]},
  { path: 'visit/:email', component: VisitAccountComponent ,canActivate: [AuthGuardService]},
  { path: 'admin',        component: AdminComponent,        canActivate: [AuthGuardService] },
  { path: 'network',      component: NetworkComponent ,     canActivate: [AuthGuardService]},
  { path: 'notifications',component: NotificationsComponent,canActivate: [AuthGuardService]},

  // otherwise, redirect to home
  { path: '**', redirectTo: ''}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
