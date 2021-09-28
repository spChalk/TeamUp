package com.example.socialnetworkingapp.registration;

import com.example.socialnetworkingapp.model.account.Account;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/register")
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping
    public ResponseEntity<Account> register(@RequestBody RegistrationRequest request) throws InterruptedException {
         return new ResponseEntity<>(this.registrationService.register(request), HttpStatus.CREATED);
    }
}
