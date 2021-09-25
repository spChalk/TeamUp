package com.example.socialnetworkingapp.model.post;


import com.example.socialnetworkingapp.model.account.Account;
import com.example.socialnetworkingapp.model.account.AccountService;
import com.example.socialnetworkingapp.model.comment.CommentResponse;
import com.example.socialnetworkingapp.model.comment.CommentService;
import com.example.socialnetworkingapp.model.like.Like;
import com.example.socialnetworkingapp.model.like.LikeResponse;
import com.example.socialnetworkingapp.model.like.LikeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/posts")
@AllArgsConstructor
public class PostController {

    private final PostService postService;
    private final AccountService accountService;
    private final LikeService likeService;
    private final CommentService commentService;

    @PostMapping("/add")
    public ResponseEntity<PostResponse> createPost(@RequestBody PostRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String user = authentication.getName();
        Account newUser = accountService.findAccountByEmail(user);
        Post newPost = new Post(request.getPayload(), newUser, new Date(), null, null, null);
        return new ResponseEntity<>(postService.addPost(newPost), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PostResponse>> getAllPosts() throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String user = authentication.getName();
        Account newUser = accountService.findAccountByEmail(user);
        return new ResponseEntity<>(postService.findAllPosts(newUser), HttpStatus.OK);
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<PostResponse> getPost(@PathVariable("id") Long id){
//        PostResponse post = postService.findPostById(id);
//        return new ResponseEntity<>(post , HttpStatus.OK);
//    }
//    @GetMapping("/by-user/{name}")
//    public List<PostResponse> getPostsByUsername(String username){
//        return postService.getPostsByUsername(username);
//    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePostById(@PathVariable("id") Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Account currUser = accountService.findAccountByEmail(authentication.getName());
        for(LikeResponse like: this.likeService.findAllLikesOfPost(id)) {
            this.likeService.deleteLikeById(like.getId());
        }
        for(CommentResponse cm: this.commentService.findAllCommentsOfPost(id)) {
            this.commentService.deleteById(cm.getId());
        }
        this.postService.deletePost(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }





}
