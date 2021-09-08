package com.example.socialnetworkingapp.model.account;

import com.example.socialnetworkingapp.model.connection_request.ConnectionReqService;
import com.example.socialnetworkingapp.model.connection_request.ConnectionRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;
    private final ConnectionReqService connectionRequestService;

    @PostConstruct
    public void createAdmin(){
        accountService.createAdmin();
    }

    @GetMapping("/all")
    public ResponseEntity<List<Account>> getAllAccounts() {
        List<Account> accounts = this.accountService.findAllAccounts();
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @GetMapping("/find/id/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable("id") Long id){
        Account account = this.accountService.findAccountById(id);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @GetMapping("/find/mail/{email}")
    public ResponseEntity<Account> getAccountByEmail(@PathVariable("email") String email){
        Account account = this.accountService.findAccountByEmail(email);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @PutMapping("/follow")
    public ResponseEntity<String> follow(@RequestBody TransferAccounts accounts) {

        /*Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();*/
        this.accountService.follow(accounts.sender, accounts.receiver);
        return new ResponseEntity<>("You have followed " + accounts.receiver.getFirstName() + "!", HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Account> updateAccount(@RequestBody Account account){
        Account newAccount = this.accountService.updateAccount(account);
        return new ResponseEntity<>(newAccount, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAccountById(@PathVariable("id") Long id){
        this.accountService.deleteAccount(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /* Lists all received *PENDING* connection requests */
    @GetMapping("/crequests/{uid}")
    public ResponseEntity<List<ConnectionRequest>> getConnectionRequests(@PathVariable("uid") Long uid) {
        return new ResponseEntity<>(this.connectionRequestService.findRequestsByAccId(uid), HttpStatus.OK);
    }

    @PutMapping("/crequests/accept")
    public ResponseEntity<HttpStatus> acceptConnectionRequests(@RequestBody ConnectionRequest connectionRequest) {
        this.connectionRequestService.acceptRequest(connectionRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/crequests/reject")
    public ResponseEntity<HttpStatus> rejectConnectionRequests(@RequestBody ConnectionRequest connectionRequest) {
        this.connectionRequestService.rejectRequest(connectionRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
