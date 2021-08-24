//package com.example.socialnetworkingapp.post;
//
//
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("posts")
//@AllArgsConstructor
//public class PostController {
//
//
//    private final PostService postService;
//
//
//    //create a post
//    @PostMapping
//    public ResponseEntity createPost(@RequestMapping PostRequest newPost){
//        postService.save(newPost);
//        return new ResponseEntity(HttpStatus.CREATED);
//    }
//
//
//    //get all posts
//    @GetMapping("/{id}")
//    public PostResponse getPost(@PathVariable Long id){
//        return postService.getPostById(id);
//    }
//
//    @GetMapping("/all")
//    public List<PostResponse> getAllPosts(){
//        return postService.getAllPosts();
//    }
//
//    @GetMapping("/by-user/{name}")
//    public List<PostResponse> getPostsByUsername(String username){
//        return postService.getPostsByUsername(username);
//    }
//
//
//
//
//
//}
