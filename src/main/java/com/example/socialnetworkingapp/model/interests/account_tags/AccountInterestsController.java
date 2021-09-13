package com.example.socialnetworkingapp.model.interests.account_tags;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/acc_tags")
@AllArgsConstructor
public class AccountInterestsController {

    private final AccountInterestsService interestsService;

    @PostMapping("/add")
    public ResponseEntity<AccountInterest> addTag(@RequestBody AccountInterest interest){
        return new ResponseEntity<>(this.interestsService.addTag(interest), HttpStatus.CREATED);
    }
}