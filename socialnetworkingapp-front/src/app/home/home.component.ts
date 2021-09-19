import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from '../authentication';
import {Account} from "../account/account";
import {Bio} from "../bio/bio";
import {AccountService} from "../account/account.service";
import {Post} from "../post/post";
import {PostService} from "../post/post.service";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  public account: Account;
  public posts: Post[];
  public postToDelete: Post;

  constructor(
      private router: Router,
      public authenticationService: AuthenticationService,
      private accountService: AccountService,
      private postService: PostService) {
  };

  ngOnInit(): void {
    if (this.authenticationService.isAdmin()) {
          this.router.navigate(['/admin']);
    }
    let email = this.authenticationService.getCurrentUser();
    this.accountService.fetchUser(email).subscribe(
      (response: Account) => {
        this.account = new Account(response);
      }
    );
    this.postService.getPosts().subscribe(
      (posts: Post[]) => {
        this.posts = posts;
      }, (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public onAddPost(payload: string) {
    this.postService.addPost(payload, this.authenticationService.getJWT()).subscribe(
      (response: Post) => {
        console.log(response);
      }, (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  window.location.reload();
  }

  public onClickModal(data: any, mode: string): void {

    const container = document.getElementById('main-container');

    const button = document.createElement('button');
    button.type = 'button';
    button.style.display = 'none';
    button.setAttribute('data-toggle', 'modal');

    if(mode === 'addPost') {
      button.setAttribute('data-target', '#addPost');
    }
    if(mode === 'deletePost') {
      this.postToDelete = data;
      button.setAttribute('data-target', '#deletePost');
    }
    if(container != null) {
      container.appendChild(button);
      button.click();
    }
  }

  public onDeletePost(postToDelete: Post) {
    this.postService.deletePost(postToDelete?.id).subscribe(
      (event: any) => {
        window.location.reload();
      }, (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }
}
