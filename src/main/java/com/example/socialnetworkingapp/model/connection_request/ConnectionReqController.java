package com.example.socialnetworkingapp.model.connection_request;

import com.example.socialnetworkingapp.model.account.Account;
import com.example.socialnetworkingapp.model.account.AccountService;
import com.example.socialnetworkingapp.model.bio.Bio;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/crequest")
public class ConnectionReqController {

    private final ConnectionReqService connectionReqService;
    private final AccountService accountService;

    @PostMapping("/add/{email}")
    public ResponseEntity<ConnectionRequestResponse> addRequest(@PathVariable("email") String email){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Account currUser = accountService.findAccountByEmail(authentication.getName());
        ConnectionRequest newCr = new ConnectionRequest(currUser, this.accountService.findAccountByEmail(email));
        ConnectionRequestResponse newConnectionRequest = connectionReqService.addRequest(newCr);
        return new ResponseEntity<>(newConnectionRequest, HttpStatus.CREATED);
    }

    @GetMapping("/search/{receiver_email}")
    public ResponseEntity<Long> SearchRequest(@PathVariable("receiver_email") String receiverEmail){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Account currUser = accountService.findAccountByEmail(authentication.getName());
        return new ResponseEntity<>(this.connectionReqService.findRequestByAccEmails(currUser.getEmail(), receiverEmail),
                HttpStatus.OK);
    }

    @DeleteMapping("/delete/{req_id}")
    public ResponseEntity<HttpStatus> deleteRequestById(@PathVariable("req_id") Long id){
        connectionReqService.deleteRequest(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ConnectionRequestResponse>> getConnectionRequests() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Account currUser = accountService.findAccountByEmail(authentication.getName());
        return new ResponseEntity<>(this.connectionReqService.findRequestsByAccId(currUser.getId()), HttpStatus.OK);
    }

    @PutMapping("/accept/{email}")
    public ResponseEntity<HttpStatus> acceptConnectionRequests(@PathVariable("email") String email) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Account currUser = accountService.findAccountByEmail(authentication.getName());
        this.connectionReqService.acceptRequest(currUser, this.accountService.findAccountByEmail(email));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/reject/{email}")
    public ResponseEntity<HttpStatus> rejectConnectionRequests(@PathVariable("email") String email) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Account currUser = accountService.findAccountByEmail(authentication.getName());
        this.connectionReqService.rejectRequest(currUser, this.accountService.findAccountByEmail(email));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}