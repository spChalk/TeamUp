package com.example.socialnetworkingapp.model.like;

import com.example.socialnetworkingapp.model.account.Account;
import com.example.socialnetworkingapp.model.account.AccountService;
import com.example.socialnetworkingapp.model.post.Post;
import com.example.socialnetworkingapp.model.post.PostResponse;
import com.example.socialnetworkingapp.model.post.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/likes")
@AllArgsConstructor
public class LikeController {

    private final LikeService likeService;
    private final AccountService accountService;
    private final PostService postService;

    @GetMapping("/all/of-post/{postId}")
    public ResponseEntity<List<LikeResponse>> getLikesOfPost(@PathVariable("postId") Long pid){
        Post post = postService.findPostById(pid);
        return new ResponseEntity<>(this.likeService.findAllLikesOfPost(pid), HttpStatus.OK);
    }

    @GetMapping("/all/of-user/{userEmail}")
    public ResponseEntity<List<LikeResponse>> getAllLikesOfUser(@PathVariable("userEmail") String email) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        this.accountService.findAccountByEmail(authentication.getName());
        return new ResponseEntity<>(this.likeService.findAllLikesOfUser(this.accountService.findAccountByEmail(email).getId()),
                HttpStatus.OK);
    }

    @PostMapping("/add/{postId}")
    public ResponseEntity<LikeResponse> addLike(@PathVariable("postId") Long postId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Account user = accountService.findAccountByEmail(authentication.getName());
        Post post = postService.findPostById(postId);
        return new ResponseEntity<>(this.likeService.addLike(new Like(user, post, new Date())), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{likeId}")
    public ResponseEntity<HttpStatus> deleteLike (@PathVariable("likeId") Long likeId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Account currUser = accountService.findAccountByEmail(authentication.getName());
        this.likeService.deleteLikeById(likeId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/all-likes-of-my-posts")
    public ResponseEntity<List<LikeResponse>> getAllLikesOfMyPosts(){

        Account author = this.accountService.findAccountByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        List<PostResponse> posts =  this.postService.findPostsByAuthorId(author.getId());

        List<LikeResponse> likes = new ArrayList<>();
        for(PostResponse post : posts){
            likes.addAll(this.likeService.findAllLikesOfPost(post.getId()));
        }
        return new ResponseEntity<>(likes, HttpStatus.OK);
    }

}