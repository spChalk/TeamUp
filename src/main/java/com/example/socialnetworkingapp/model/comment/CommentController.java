package com.example.socialnetworkingapp.model.comment;

import com.example.socialnetworkingapp.account.Account;
import com.example.socialnetworkingapp.account.AccountResponse;
import com.example.socialnetworkingapp.account.AccountService;
import com.example.socialnetworkingapp.post.Post;
import com.example.socialnetworkingapp.post.PostResponse;
import com.example.socialnetworkingapp.post.PostService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/posts")
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final PostService postService;
    private final AccountService accountService;


//all comments from all posts
//    @GetMapping("/comments/all")
//    public ResponseEntity<List<CommentResponse>> getAllComments(){
//        List<CommentResponse> comments = commentService.findAllComments();
//        return new ResponseEntity<>(comments, HttpStatus.OK);
//    }

    //find all comments of a post
    @GetMapping("/comments/{postid}/all")
    public ResponseEntity<List<CommentResponse>> getAllComments(@PathVariable("postid") Long postid){
        Post post = postService.findPostById(postid);
        List<CommentResponse> comments = commentService.findAllCommentsOfPost(postid);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @GetMapping("/comments/all")
    public ResponseEntity<List<CommentResponse>> getAll(){
        List<CommentResponse> comments = commentService.findAll();
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

//    @GetMapping("/comments/find/{id}")
//    public ResponseEntity<Account> getById(@PathVariable("id") Long id){
//        Account account = accountService.findAccountById(id);
//        return new ResponseEntity<>(account, HttpStatus.OK);
//    }

    @PostMapping("/{postid}/comment/add")
    public ResponseEntity<String> addComment(@RequestBody CommentRequest request, @PathVariable("postid") Long postid){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Account user = accountService.findAccountByEmail(userDetails.getUsername());
        Post post = postService.findPostById(postid);
        Comment newComment = new Comment(request.getPayload(), user , post , new Date());
        commentService.addComment(newComment);
        return new ResponseEntity<>("Comment created", HttpStatus.CREATED);
    }

//    @PutMapping("/update")
//    public ResponseEntity<Account> updateAccount(@RequestBody Account account){
//        Account newAccount = accountService.updateAccount(account);
//        return new ResponseEntity<>(newAccount, HttpStatus.OK);
//    }

//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<?> deleteAccountById(@PathVariable("id") Long id){
//        accountService.deleteAccount(id);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
}