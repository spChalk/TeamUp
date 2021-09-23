package com.example.socialnetworkingapp.model.comment;

import com.example.socialnetworkingapp.model.account.Account;
import com.example.socialnetworkingapp.model.account.AccountService;
import com.example.socialnetworkingapp.model.post.Post;
import com.example.socialnetworkingapp.model.post.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;
    private final PostService postService;
    private final AccountService accountService;

    //find all comments of a post
    @GetMapping("/all/of-post/{postId}")
    public ResponseEntity<List<CommentResponse>> getAllCommentsOfPost(@PathVariable("postId") Long postId) {
        Post post = this.postService.findPostById(postId);
        return new ResponseEntity<>(this.commentService.findAllCommentsOfPost(postId), HttpStatus.OK);
    }

    @GetMapping("/all/of-user/{userEmail}")
    public ResponseEntity<List<CommentResponse>> getAllCommentsOfUser(@PathVariable("userEmail") String email) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        this.accountService.findAccountByEmail(authentication.getName());
        return new ResponseEntity<>(this.commentService.findAllCommentsOfUser(this.accountService.findAccountByEmail(email).getId()),
                HttpStatus.OK);
    }

    @PostMapping("/add/{postId}")
    public ResponseEntity<CommentResponse> addComment(@RequestBody CommentRequest request, @PathVariable("postId") Long postId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Account user = this.accountService.findAccountByEmail(authentication.getName());
        Post post = this.postService.findPostById(postId);
        Comment newComment = new Comment(request.getPayload(), user , post , LocalDate.now().toString());
        return new ResponseEntity<>(this.commentService.addComment(newComment), HttpStatus.CREATED);
    }

    @PutMapping("/update/{cId}")
    public ResponseEntity<CommentResponse> updateComment(@PathVariable("cId") Long  commentId,
                                                         @RequestBody CommentRequest comment) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Account user = this.accountService.findAccountByEmail(authentication.getName());
        return new ResponseEntity<>(this.commentService.updateComment(commentId, comment), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{cId}")
    public ResponseEntity<HttpStatus> deleteComment(@PathVariable("cId") Long commentId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Account user = this.accountService.findAccountByEmail(authentication.getName());
        this.commentService.deleteById(commentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}