package com.example.socialnetworkingapp.model.post_view;

import com.example.socialnetworkingapp.model.account.Account;
import com.example.socialnetworkingapp.model.account.AccountService;
import com.example.socialnetworkingapp.model.post.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post_views")
@AllArgsConstructor
public class PostViewController {

    private final PostViewService postViewService;
    private final AccountService accountService;
    private final PostService postService;

    @GetMapping("/{post_id}")
    public List<PostViewResponse> getViewsByPostId(@PathVariable("post_id") Long id){
        return postViewService.getViewsByPostId(id);
    }

    @GetMapping("/sum/{post_id}")
    public Long getSumOfViewsByPostId(@PathVariable("post_id") Long id){
        return postViewService.getSumOfViewsByPostId(id);
    }

    @PostMapping("/add/{jid}")
    public ResponseEntity<PostView> addView(@PathVariable("jid") Long jid) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Account currUser = accountService.findAccountByEmail(authentication.getName());
        return new ResponseEntity<>(postViewService.addView(currUser, jid, this.postService.findPostById(jid)), HttpStatus.CREATED);
    }
}