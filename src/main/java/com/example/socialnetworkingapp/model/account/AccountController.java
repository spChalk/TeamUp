package com.example.socialnetworkingapp.model.account;

import com.example.socialnetworkingapp.filesystem.FileDBService;
import com.example.socialnetworkingapp.model.bio.Bio;
import com.example.socialnetworkingapp.model.connection_request.ConnectionReqService;
import com.example.socialnetworkingapp.model.connection_request.ConnectionRequest;
import com.example.socialnetworkingapp.model.education.Education;
import com.example.socialnetworkingapp.model.experience.Experience;
import com.example.socialnetworkingapp.model.tags.Tag;
import com.example.socialnetworkingapp.model.tags.TagService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpRange;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String user = authentication.getName();
        Account currUser = accountService.findAccountByEmail(user);
        List<Account> accounts = this.accountService.findAllAccounts();
        accounts.remove(currUser);
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

  /*  @PutMapping("/follow")
    public ResponseEntity<String> follow(@RequestBody TransferAccounts accounts) {

        *//*Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();*//*
        this.accountService.follow(accounts.sender, accounts.receiver);
        return new ResponseEntity<>("You have followed " + accounts.receiver.getFirstName() + "!", HttpStatus.OK);
    }*/

    @PutMapping("/update")
    public ResponseEntity<Account> updateAccount(@RequestBody Account account){
        Account newAccount = this.accountService.updateAccount(account);
        return new ResponseEntity<>(newAccount, HttpStatus.OK);
    }

    @PutMapping("/about-update")
    public ResponseEntity<Account> aboutUpdateAccount(@RequestBody AccountUpdateRequest account){
        Account newAccount = this.accountService.aboutUpdateAccount(account);
        return new ResponseEntity<>(newAccount, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAccountById(@PathVariable("id") Long id){
        this.accountService.deleteAccount(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/education/delete/{id}")
    public ResponseEntity<?> deleteEducationById(@PathVariable("id") Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String user = authentication.getName();
        Account currUser = accountService.findAccountByEmail(user);
        this.accountService.deleteEducation(currUser, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/experience/delete/{id}")
    public ResponseEntity<?> deleteExperienceById(@PathVariable("id") Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String user = authentication.getName();
        Account currUser = accountService.findAccountByEmail(user);
        this.accountService.deleteExperience(currUser, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*@GetMapping("/network/all/{email}")
    public ResponseEntity<List<Account>> getNetwork(@PathVariable("email") String email) {

        *//* Get ALL accepted requests, the ones that the user has sent AND received *//*
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
    }*/

    @GetMapping("/network/all/{email}")
    public ResponseEntity<List<Account>> getNetwork(@PathVariable("email") String email) {
        return new ResponseEntity<>(this.accountService.findAccountByEmail(email).getNetwork(), HttpStatus.OK);
    }

    @DeleteMapping("/network/delete/{uid}")
    public ResponseEntity<?> deleteFromNetwork(@PathVariable("uid") String otherEmail){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Account currUser = accountService.findAccountByEmail(authentication.getName());
        this.connectionReqService.deleteConnection(currUser.getEmail(), otherEmail);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/tags/add")
    public ResponseEntity<Account> addTag(@RequestBody String tag) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return new ResponseEntity<Account>(this.accountService.addTag(tag, email), HttpStatus.OK);
    }

    @PostMapping("/tags/add/all")
    public ResponseEntity<Account> addTag(@RequestBody List<String> tags) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return new ResponseEntity<Account>(this.accountService.addAccountTags(tags, email), HttpStatus.OK);
    }

    @GetMapping("/experience/get")
    public ResponseEntity<List<Experience>> getExperience(@RequestBody String email) {
        return new ResponseEntity<>(this.accountService.getExperience(email), HttpStatus.OK);
    }

    @GetMapping("/education/get")
    public ResponseEntity<List<Education>> getEducation(@RequestBody String email) {
        return new ResponseEntity<>(this.accountService.getEducation(email), HttpStatus.OK);
    }

    /* Post a {email, experience} */
    @PostMapping("/experience/add")
    public ResponseEntity<Account> addExperience(@RequestBody AccountExperience accountExperience) {
        return new ResponseEntity<>(this.accountService.addExperience(accountExperience.getEmail(), accountExperience.getXp()),
                HttpStatus.OK);
    }

    @PostMapping("/experience/update")
    public ResponseEntity<Experience> editExperience(@RequestBody AccountExperience accountExperience) {
        return new ResponseEntity<Experience>(this.accountService.updateExperience(accountExperience.getEmail(), accountExperience.getXp()),
                HttpStatus.OK);
    }

    /* Post a {email, bio} */
    @PostMapping("/bio/add")
    public ResponseEntity<Bio> addBio(@RequestBody AccountBio accountBio){
        return new ResponseEntity<>(this.accountService.addBio(accountBio.getEmail(), accountBio.getBio()),
                HttpStatus.OK);
    }

    @DeleteMapping("/bio/delete/{uid}")
    public ResponseEntity<HttpStatus> deleteBio(@PathVariable("uid") Long uid) {
        this.accountService.deleteBio(uid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /* Post a {email, education} */
    @PostMapping("/education/add")
    public ResponseEntity<Account> addEducation(@RequestBody AccountEducation accountEducation) {
        return new ResponseEntity<>(this.accountService.addEducation(accountEducation.getEmail(), accountEducation.getEducation()),
                HttpStatus.OK);
    }

    @PostMapping("/education/update")
    public ResponseEntity<Education> editEducation(@RequestBody AccountEducation accountEducation) {
        return new ResponseEntity<Education>(this.accountService.updateEducation(accountEducation.getEmail(), accountEducation.getEducation()),
                HttpStatus.OK);
    }

    @PutMapping("/hide-tags")
    public ResponseEntity<Account> hideTags() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Account account = this.accountService.findAccountByEmail(authentication.getName());
        return new ResponseEntity<>(this.accountService.hideTags(account), HttpStatus.OK);
    }

    @PutMapping("/show-tags")
    public ResponseEntity<Account> showTags() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Account account = this.accountService.findAccountByEmail(authentication.getName());
        return new ResponseEntity<>(this.accountService.showTags(account), HttpStatus.OK);
    }

    @GetMapping("/myTags")
    public ResponseEntity<List<Tag>> getUserTags(){
        return new ResponseEntity<>(this.accountService.getUserTags(), HttpStatus.OK);
    }

}
