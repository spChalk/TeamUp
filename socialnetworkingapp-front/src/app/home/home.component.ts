import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from '../authentication';
import {Account} from "../account/account";
import {AccountService} from "../account/account.service";
import {Post} from "../post/post";
import {PostService} from "../post/post.service";
import {HttpErrorResponse, HttpEventType, HttpResponse} from "@angular/common/http";
import {UploadFileService} from "../upload-files/upload-files.service";
import {Like} from "../like/like";
import {LikeService} from "../like/like.service";
import {Comment} from "../comment/comment";
import {CommentService} from "../comment/comment.service";
import {JobView} from "../job-view/job-view";
import {PostView} from "../post-view/post-view";
import {PostViewService} from "../post-view/post-view.service";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  public account: Account;
  public posts: Post[] = [];
  /* postId => [num of likes, If i have liked this post, show the like ID, else -1] */
  public likedPosts: Map<number, [number, number]> = new Map<number, [number, number]>();
  /* postId => [comments of post, every comment id I have made, else null] */
  public commentedPosts: Map<number, [Comment[], number[]]> = new Map<number, [Comment[], number[]]>();

  public postIdToDelete: number;
  public commentIdToDelete: number;
  public commentToEdit: Comment;
  public selectedPost: Post = undefined;

  public tempRefreshPostId: number;

  private currImageFile: File = null;
  private currVideoFile: File = null;
  private currSoundFile: File = null;
  progress = 0;
  message = '';

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

public loadPostLikes(pid: number) {
  this.likeService.getLikesOfPost(pid).subscribe(
    (likes: Like[]) => {
      this.likedPosts.set(pid, [likes.length, -1]);

      for(let like of likes) {
        if(like.userEmail === this.authenticationService.getCurrentUser()) {
          this.likedPosts.set(pid, [likes.length, like.id]);
        }
      }
    }, (error: HttpErrorResponse) => {
      alert(error.message);
    }
  );
}

  public loadPostComments(pid: number) {
    this.commentService.getCommentsOfPost(pid).subscribe(
      (comments: any) => {

        /* ids */
        let myComments: number[] = null;
        this.commentedPosts.set(pid, [comments, myComments]);

        for(let comment of comments) {
          if(comment.authorEmail === this.authenticationService.getCurrentUser()) {
            myComments.push(comment.id);
          }
        }
      }, (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  private loadPosts() {
    this.postService.getPosts().subscribe(
      (posts: Post[]) => {
        this.posts = posts;
        for(let post of this.posts) {
          this.loadPostLikes(post.id);
          this.loadPostComments(post.id);
        }
      }, (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }


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
    this.loadPosts();
  }

  public onAddPost(payload: string) {

    this.postService.addPost(payload).subscribe(
      (response: Post) => {

        let files: File[] = [this.currImageFile, this.currVideoFile, this.currSoundFile];

        for(let file of files) {

          if(file === null || file === undefined) {
            continue;
          }

          this.progress = 0;
          this.uploadService.uploadPost(file, response?.id, this.authenticationService.getJWT()).subscribe(
            event => {
              if (event.type === HttpEventType.UploadProgress) {
                this.progress = Math.round(100 * event.loaded / event.total);
              } else if (event instanceof HttpResponse) {
                this.message = event.body.message;
                this.loadPosts();
              }

            },
            err => {
              this.progress = 0;
              this.message = 'Could not upload the file!';
            });
          this.currImageFile = undefined;
          this.currVideoFile = undefined;
          this.currSoundFile = undefined;
        }
        this.loadPosts();

      }, (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );

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
      this.postIdToDelete = data;
      button.setAttribute('data-target', '#deletePost');
    }
    if(mode === 'deleteComment') {
      this.commentIdToDelete = data[0];
      this.tempRefreshPostId = data[1];
      button.setAttribute('data-target', '#deleteComment');
    }
    if(mode === 'editComment') {
      this.commentToEdit = data[0];
      this.tempRefreshPostId = data[1];
      button.setAttribute('data-target', '#editComment');
    }
    if(mode === 'viewPost') {
      this.selectedPost = data;
      button.setAttribute('data-target', '#viewPost');
    }
    if(container != null) {
      container.appendChild(button);
      button.click();
    }
  }

  public onDeletePost(postIdToDelete: number) {
    this.postService.deletePost(postIdToDelete).subscribe(
      (event: any) => {
        window.location.reload();
      }, (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public selectImageFile(event: any) {
    let tempFileList: FileList = event.target.files;
    this.currImageFile = tempFileList.item(0);
  }
  public selectVideoFile(event: any) {
    let tempFileList: FileList = event.target.files;
    this.currVideoFile = tempFileList.item(0);
  }
  public selectSoundFile(event: any) {
    let tempFileList: FileList = event.target.files;
    this.currSoundFile = tempFileList.item(0);
  }

  public onLike(pid: number) {
    this.likeService.addLike(pid).subscribe(
      (response: Like) => {
        console.log(response);
        this.loadPostLikes(pid);
      }, (error: HttpErrorResponse) => {
       alert(error.message);
      }
    );
  }

  public onDislike(pid: number) {
    this.likeService.deleteLike(this.likedPosts.get(pid)[1]).subscribe(
      (response: void) => {
        console.log("Like removed");
        this.loadPostLikes(pid);
      }, (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public onAddComment(postId: number, payload: string) {
    this.commentService.addComment(postId, payload).subscribe(
      (response: any) => {
        console.log(response);
        this.loadPostComments(postId);
      }, (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public onDeleteComment(commentIdToDelete: number) {
    this.commentService.deleteComment(commentIdToDelete).subscribe(
      (event: any) => {
        this.loadPostComments(this.tempRefreshPostId);
      }, (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public onEditComment(cId: number, payload: string) {
    this.commentService.updateComment(cId, payload).subscribe(
      (event: any) => {
        this.loadPostComments(this.tempRefreshPostId);
      }, (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

  public increaseView(pid: number) {
    this.postViewService.addView(pid).subscribe(
      (response: PostView) => {

      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }
}
