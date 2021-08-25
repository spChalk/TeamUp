package com.example.socialnetworkingapp.post;


import com.example.socialnetworkingapp.account.Account;
import com.example.socialnetworkingapp.account.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@AllArgsConstructor
public class PostController {

    private final PostService postService;
    private final AccountService accountService;


    @PostMapping("/add")
    public String createPost(@RequestBody PostRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Account user = accountService.findAccountByEmail(userDetails.getUsername());
        Post newPost = new Post(request.getTitle(), request.getPayload(), user);
        return postService.addPost(newPost);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PostResponse>> getAllPosts(){
//        List<Post> posts = postService.findAllPosts();
        List<PostResponse> posts = postService.findAllPosts();
        return new ResponseEntity<>(posts, HttpStatus.OK);
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
        postService.deletePost(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }





}
