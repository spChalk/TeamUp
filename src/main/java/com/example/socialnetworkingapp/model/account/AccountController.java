package com.example.socialnetworkingapp.model.account;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<AccountResponse>> getAllAccounts(){
        List<AccountResponse> accounts = accountService.findAllAccounts();
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable("id") Long id){
        Account account = accountService.findAccountById(id);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Account> addAccount(@RequestBody Account account){
        Account newAccount = accountService.addAccount(account);
        return new ResponseEntity<>(newAccount, HttpStatus.CREATED);
    }

    @PutMapping("/add-friend")
    public ResponseEntity<String> addFriend(@RequestBody Account account){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Account user = accountService.findAccountByEmail(userDetails.getUsername());
        Account friend = accountService.findAccountByEmail(account.getUsername());
        user = accountService.addFriend(user, friend);
        return new ResponseEntity<>("You are now friends", HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Account> updateAccount(@RequestBody Account account){
        Account newAccount = accountService.updateAccount(account);
        return new ResponseEntity<>(newAccount, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAccountById(@PathVariable("id") Long id){
        accountService.deleteAccount(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }




}
