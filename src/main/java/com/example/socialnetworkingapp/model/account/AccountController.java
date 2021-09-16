package com.example.socialnetworkingapp.model.account;

import com.example.socialnetworkingapp.filesystem.FileDBService;
import com.example.socialnetworkingapp.model.bio.Bio;
import com.example.socialnetworkingapp.model.connection_request.ConnectionReqService;
import com.example.socialnetworkingapp.model.connection_request.ConnectionRequest;
import com.example.socialnetworkingapp.model.experience.Experience;
import com.example.socialnetworkingapp.model.tags.Tag;
import com.example.socialnetworkingapp.model.tags.TagService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpRange;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;
    private final ConnectionReqService connectionReqService;
    private final FileDBService fileDBService;

    @PostConstruct
    public void createAdmin(){
        accountService.createAdmin();
    }

    @GetMapping("/all")
    public ResponseEntity<List<Account>> getAllAccounts() throws IOException {

        /* Utility method to move all dangling files to the corresponding folders */
        Set<String> files = this.fileDBService.listDir(".");
        for (String filename: files) {
            if(filename.contains("EXP_")) {
                if (filename.contains(".xml"))
                    this.fileDBService.moveFile(filename, "./exported_XML/" + filename);
                if (filename.contains(".json"))
                    this.fileDBService.moveFile(filename, "./exported_JSON/" + filename);
            }
        }

        List<Account> accounts = this.accountService.findAllAccounts();
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @GetMapping("/find/id/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable("id") Long id){
        Account account = this.accountService.findAccountById(id);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @GetMapping("/find/mail/{email}")
    public ResponseEntity<Account> getAccountByEmail(@PathVariable("email") String email) {
        Account account = this.accountService.findAccountByEmail(email);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @GetMapping("/find/mails/{keyword}")
    public ResponseEntity<List<Account>> getAccountsBySimilarEmail(@PathVariable("keyword") String keyword) {
        List<Account> accounts = this.accountService.findAccountsBySimilarEmail(keyword);
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @GetMapping("/find/names/{keyword}")
    public ResponseEntity<List<Account>> getAccountsBySimilarName(@PathVariable("keyword") String keyword) {
        List<Account> accounts = this.accountService.findAccountsBySimilarName(keyword);
        return new ResponseEntity<>(accounts, HttpStatus.OK);
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

    @GetMapping("/network/all/{email}")
    public ResponseEntity<List<Account>> getNetwork(@PathVariable("email") String email) {

        /* Get ALL accepted requests, the ones that the user has sent AND received */
        List<ConnectionRequest> sentRequests = this.connectionReqService.findSentAcceptedRequestsByAccEmail(email);
        List<ConnectionRequest> receivedRequests = this.connectionReqService.findReceivedAcceptedRequestsByAccEmail(email);

        List<Account> net = new ArrayList<>();
        for (ConnectionRequest request: sentRequests) {
            net.add(request.getReceiver());
        }
        for (ConnectionRequest request: receivedRequests) {
            net.add(request.getSender());
        }
        return new ResponseEntity<>(net, HttpStatus.OK);
    }

    @DeleteMapping("/network/{me}/delete/{uid}")
    public ResponseEntity<?> deleteFromNetwork(@PathVariable("me") Long me, @PathVariable("uid") Long uid){
        this.connectionReqService.deleteRequestByAccIds(me, uid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/tags/add/{tagName}")
    public ResponseEntity<Account> addTag(@PathVariable("tagName") String tagName, @RequestBody String email) {
        return new ResponseEntity<>(this.accountService.addTag(tagName, email), HttpStatus.OK);
    }

    @GetMapping("/experience/get")
    public ResponseEntity<List<Experience>> getExperience(@RequestBody String email) {
        return new ResponseEntity<>(this.accountService.getExperience(email), HttpStatus.OK);
    }

    /* Post a {email, experience} */
    @PostMapping("/experience/add")
    public ResponseEntity<Account> addExperience(@RequestBody AccountExperience accountExperience) {
        return new ResponseEntity<>(this.accountService.addExperience(accountExperience.getEmail(), accountExperience.getXp()),
                HttpStatus.OK);
    }

    /* Post a {email, bio} */
    @PostMapping("/bio/add")
    public ResponseEntity<Bio> addBio(@RequestBody AccountBio accountBio) {
        return new ResponseEntity<>(this.accountService.addBio(accountBio.getEmail(), accountBio.getBio()),
                HttpStatus.OK);
    }

    /* Post a {email, education} */
    @PostMapping("/education/add")
    public ResponseEntity<Account> addEducation(@RequestBody AccountEducation accountEducation) {
        return new ResponseEntity<>(this.accountService.addEducation(accountEducation.getEmail(), accountEducation.getEducation()),
                HttpStatus.OK);
    }
}
