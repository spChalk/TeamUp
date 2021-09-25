import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {AccountService} from "./account/account.service";
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { HomeComponent } from './home';
import { LoginComponent } from './login';
import { RegisterComponent } from './register';
import { AccountComponent } from './account/account.component';
import { BioComponent } from './bio/bio.component';
import { CommentComponent } from './comment/comment.component';
import { ExperienceComponent } from './experience/experience.component';
import { JobComponent } from './job/job.component';
import { JobApplicationComponent } from './job-application/job-application.component';
import { LikeComponent } from './like/like.component';
import { MessageComponent } from './message/message.component';
import { PostComponent } from './post/post.component';
import { SettingsComponent } from './settings/settings.component';
import { WelcomeComponent } from './welcome/welcome.component';
import { AboutComponent } from './about/about.component';
import { ChatComponent } from './chat/chat.component'
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import { AdminComponent } from './admin/admin.component';
import { NetworkComponent } from './network/network.component';
import { UploadFilesComponent } from './upload-files/upload-files.component';
import { NavBarAuthenticatedComponent } from './nav-bar-authenticated/nav-bar-authenticated.component';
import { NavBarComponent } from './nav-bar/nav-bar.component';
import { NgxWebstorageModule } from 'ngx-webstorage';
import { JobViewComponent } from './job-view/job-view.component';
import { TagsComponent } from './tags/tags.component';
import { EducationComponent } from './education/education.component';
import { VisitAccountComponent } from './visit-account/visit-account.component';
import { ConnectionRequestComponent } from './connection-request/connection-request.component';
import { TokenInterceptorService } from './authentication';
import {NavBarAdminComponent} from "./nav-bar-admin/nav-bar-admin.component";
import { PostViewComponent } from './post-view/post-view.component';
@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    LoginComponent,
    RegisterComponent,
    AccountComponent,
    BioComponent,
    CommentComponent,
    ExperienceComponent,
    JobComponent,
    JobApplicationComponent,
    LikeComponent,
    MessageComponent,
    PostComponent,
    SettingsComponent,
    WelcomeComponent,
    AboutComponent,
    ChatComponent,
    AdminComponent,
    NetworkComponent,
    UploadFilesComponent,
    NavBarAuthenticatedComponent,
    NavBarComponent,
    NavBarAdminComponent,
    JobViewComponent,
    TagsComponent,
    EducationComponent,
    VisitAccountComponent,
    ConnectionRequestComponent,
    PostViewComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    NgxWebstorageModule.forRoot()
  ],
  providers: [AccountService, {provide : HTTP_INTERCEPTORS , useClass : TokenInterceptorService , multi: true}],
  bootstrap: [AppComponent]
})
export class AppModule { }
