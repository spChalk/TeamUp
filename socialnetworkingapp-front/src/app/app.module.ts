import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {AccountService} from "./account/account.service";
import { HttpClientModule } from '@angular/common/http';
import { HomeComponent } from './home';
import { LoginComponent } from './login';
import { RegisterComponent } from './register';
import { AccountComponent } from './account/account.component';
import { BioComponent } from './bio/bio.component';
import { CommentComponent } from './comment/comment.component';
import { ExperienceComponent } from './experience/experience.component';
import { InterestsComponent } from './interests/interests.component';
import { JobComponent } from './job/job.component';
import { JobApplicationComponent } from './job-application/job-application.component';
import { LikeComponent } from './like/like.component';
import { MessageComponent } from './message/message.component';
import { PostComponent } from './post/post.component';
import { SettingsComponent } from './settings/settings.component';
import { WelcomeComponent } from './welcome/welcome.component';
import { AboutComponent } from './about/about.component'

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
    InterestsComponent,
    JobComponent,
    JobApplicationComponent,
    LikeComponent,
    MessageComponent,
    PostComponent,
    SettingsComponent,
    WelcomeComponent,
    AboutComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule
  ],
  providers: [AccountService],
  bootstrap: [AppComponent]
})
export class AppModule { }
