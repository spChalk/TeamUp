import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AccountService } from '../account/account.service';
import { AuthenticationService } from '../authentication';
import { CommentService } from '../comment/comment.service';
import { LikeService } from '../like/like.service';
import { PostViewService } from '../post-view/post-view.service';
import { PostService } from '../post/post.service';
import { UploadFileService } from '../upload-files/upload-files.service';

@Component({
    selector: 'app-notifications',
    templateUrl: './notifications.component.html',
    styleUrls: ['./notifications.component.css']
})
export class NotificationsComponent implements OnInit {

    constructor(
        private router: Router,
        public authenticationService: AuthenticationService,
        private accountService: AccountService,
        private uploadService: UploadFileService,
        private likeService: LikeService,
        private commentService: CommentService,
        private postViewService: PostViewService,
        private postService: PostService) {
    };

    ngOnInit(): void {

        if (this.authenticationService.isAdmin()) {
            this.router.navigate(['/admin']);
        }
        let email = this.authenticationService.getCurrentUser();
        // this.accountService.fetchUser(email).subscribe(
        //     (response: Account) => {
        //         this.account = new Account(response);
        //     }
        // );
        // this.loadPosts();
    }

  public onClickModal(data: any, mode: string): void {

    const container = document.getElementById('main-container');

    const button = document.createElement('button');
    button.type = 'button';
    button.style.display = 'none';
    button.setAttribute('data-toggle', 'modal');

    if (mode === 'friends') {
      button.setAttribute('data-target', '#friends');
    }
    if (mode === 'comments') {
      button.setAttribute('data-target', '#comments');
    }

    if (mode === 'likes') {
      button.setAttribute('data-target', '#likes');
    }
    if (container != null) {
      container.appendChild(button);
      button.click();
    }
  }

}
