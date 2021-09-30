package com.example.socialnetworkingapp.model.tags;

import com.example.socialnetworkingapp.model.account.Account;
import com.example.socialnetworkingapp.model.account.AccountRole;
import com.example.socialnetworkingapp.model.account.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/tags")
public class TagController {

    private TagService tagService;
    private AccountService accountService;

    @PostMapping("/add")
    public ResponseEntity<Tag> addTag(@RequestBody Tag tag) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String user = authentication.getName();
        Account currUser = accountService.findAccountByEmail(user);
        if(currUser.getRole()!= AccountRole.ADMIN){
            return null;
        }
        return new ResponseEntity<>(this.tagService.addTag(tag), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Tag>> getAllTags(){
        return new ResponseEntity<>(this.tagService.getAllTags(), HttpStatus.OK);
    }

}