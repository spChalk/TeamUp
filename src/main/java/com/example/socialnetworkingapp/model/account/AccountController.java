package com.example.socialnetworkingapp.model.account;

import com.example.socialnetworkingapp.filesystem.FileDBService;
import com.example.socialnetworkingapp.model.bio.Bio;
import com.example.socialnetworkingapp.model.comment.Comment;
import com.example.socialnetworkingapp.model.comment.CommentService;
import com.example.socialnetworkingapp.model.connection_request.ConnectionReqService;
import com.example.socialnetworkingapp.model.connection_request.ConnectionRequestResponse;
import com.example.socialnetworkingapp.model.education.Education;
import com.example.socialnetworkingapp.model.experience.Experience;
import com.example.socialnetworkingapp.model.job.Job;
import com.example.socialnetworkingapp.model.job.JobService;
import com.example.socialnetworkingapp.model.job_application.JobApplicationResponse;
import com.example.socialnetworkingapp.model.job_application.JobApplicationService;
import com.example.socialnetworkingapp.model.job_view.JobView;
import com.example.socialnetworkingapp.model.job_view.JobViewResponse;
import com.example.socialnetworkingapp.model.job_view.JobViewService;
import com.example.socialnetworkingapp.model.like.Like;
import com.example.socialnetworkingapp.model.like.LikeService;
import com.example.socialnetworkingapp.model.tags.Tag;
import lombok.AllArgsConstructor;
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
    private final JobService jobService;
    private final JobApplicationService jobApplicationService;
    private final JobViewService jobViewService;
    private final CommentService commentService;
    private final LikeService likeService;

    @PostConstruct
    public void createAccounts() {
        accountService.createAccounts();
    }

    @GetMapping("/all")
    public ResponseEntity<List<AccountResponse>> getAllAccounts() throws IOException {

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

        List<AccountResponse> accs = new ArrayList<>();
        for(Account acc: accounts) {
            accs.add(new AccountResponse(acc));
        }
        return new ResponseEntity<>(accs, HttpStatus.OK);
    }

    @GetMapping("/find/id/{id}")
    public ResponseEntity<AccountResponse> getAccountById(@PathVariable("id") Long id){
        Account account = this.accountService.findAccountById(id);
        return new ResponseEntity<>(new AccountResponse(account), HttpStatus.OK);
    }

    @GetMapping("/find/mail/{email}")
    public ResponseEntity<AccountResponse> getAccountByEmail(@PathVariable("email") String email) {
        Account account = this.accountService.findAccountByEmail(email);
        return new ResponseEntity<>(new AccountResponse(account), HttpStatus.OK);
    }

    @GetMapping("/find/names/{keyword}")
    public ResponseEntity<List<AccountResponse>> getAccountsBySimilarName(@PathVariable("keyword") String keyword) {
        List<Account> accounts = this.accountService.findAccountsBySimilarName(keyword);
        List<AccountResponse> accs = new ArrayList<>();
        for(Account acc: accounts) {
            accs.add(new AccountResponse(acc));
        }
        return new ResponseEntity<>(accs, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<AccountResponse> updateAccount(@RequestBody Account account){
        Account newAccount = this.accountService.updateAccount(account);
        return new ResponseEntity<>(new AccountResponse(newAccount), HttpStatus.OK);
    }

    @PutMapping("/about-update")
    public ResponseEntity<AccountResponse> aboutUpdateAccount(@RequestBody AccountUpdateRequest account){
        Account newAccount = this.accountService.aboutUpdateAccount(account);
        return new ResponseEntity<>(new AccountResponse(newAccount), HttpStatus.OK);
    }

    @PostMapping("/confirmation")
    public ResponseEntity<Boolean> passwordConfirmation(@RequestBody String password){
        return new ResponseEntity<Boolean>(this.accountService.passwordConfirmation(password), HttpStatus.OK);
    }

    @PostMapping("/update-password")
    public ResponseEntity<Boolean> updatePassword(@RequestBody String newPassword){
        return new ResponseEntity<Boolean>(this.accountService.updatePassword(newPassword), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAccountById(@PathVariable("id") Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String user = authentication.getName();
        Account currUser = accountService.findAccountByEmail(user);

        for(Account acc: currUser.getNetwork()) {
            this.accountService.removeConnection(acc.getEmail(), currUser.getEmail());
        }

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

    @DeleteMapping("/network/delete/{email}")
    public ResponseEntity<?> deleteFromNetwork(@PathVariable("email") String otherEmail){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Account currUser = accountService.findAccountByEmail(authentication.getName());
        this.connectionReqService.deleteConnection(currUser.getEmail(), otherEmail);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/tags/add")
    public ResponseEntity<AccountResponse> addTag(@RequestBody String tag) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Account currUser = accountService.findAccountByEmail(authentication.getName());
        return new ResponseEntity<AccountResponse>(new AccountResponse(this.accountService.addTag(tag, currUser)), HttpStatus.OK);
    }

    @PostMapping("/tags/add/all")
    public ResponseEntity<AccountResponse> addTag(@RequestBody List<String> tags) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Account currUser = accountService.findAccountByEmail(authentication.getName());
        return new ResponseEntity<AccountResponse>(new AccountResponse(this.accountService.addAccountTags(tags, currUser)), HttpStatus.OK);
    }

    @GetMapping("/experience/get")
    public ResponseEntity<List<Experience>> getExperience() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Account currUser = accountService.findAccountByEmail(authentication.getName());
        return new ResponseEntity<>(currUser.getExperience(), HttpStatus.OK);
    }

    @GetMapping("/education/get")
    public ResponseEntity<List<Education>> getEducation() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Account currUser = accountService.findAccountByEmail(authentication.getName());
        return new ResponseEntity<>(currUser.getEducation(), HttpStatus.OK);
    }

    /* Post a {email, experience} */
    @PostMapping("/experience/add")
    public ResponseEntity<AccountResponse> addExperience(@RequestBody Experience experience) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Account currUser = accountService.findAccountByEmail(authentication.getName());
        return new ResponseEntity<>(new AccountResponse(this.accountService.addExperience(currUser, experience)),
                HttpStatus.OK);
    }

    @PostMapping("/experience/update")
    public ResponseEntity<Experience> editExperience(@RequestBody Experience experience) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Account currUser = accountService.findAccountByEmail(authentication.getName());
        return new ResponseEntity<Experience>(this.accountService.updateExperience(currUser, experience),
                HttpStatus.OK);
    }

    @PostMapping("/bio/add")
    public ResponseEntity<Bio> addBio(@RequestBody String bio){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Account currUser = accountService.findAccountByEmail(authentication.getName());
        return new ResponseEntity<>(this.accountService.addBio(currUser, new Bio(bio)),
                HttpStatus.OK);
    }

    @DeleteMapping("/bio/delete/{uid}")
    public ResponseEntity<HttpStatus> deleteBio(@PathVariable("uid") Long uid) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Account currUser = accountService.findAccountByEmail(authentication.getName());
        this.accountService.deleteBio(uid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /* Post a {email, education} */
    @PostMapping("/education/add")
    public ResponseEntity<AccountResponse> addEducation(@RequestBody Education education) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Account currUser = accountService.findAccountByEmail(authentication.getName());
        return new ResponseEntity<>(new AccountResponse(this.accountService.addEducation(currUser, education)),
                HttpStatus.OK);
    }

    @PostMapping("/education/update")
    public ResponseEntity<Education> editEducation(@RequestBody Education education) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Account currUser = accountService.findAccountByEmail(authentication.getName());
        return new ResponseEntity<Education>(this.accountService.updateEducation(currUser, education),
                HttpStatus.OK);
    }

    @PutMapping("/hide-tags")
    public ResponseEntity<AccountResponse> hideTags() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Account account = this.accountService.findAccountByEmail(authentication.getName());
        return new ResponseEntity<>(new AccountResponse(this.accountService.hideTags(account)), HttpStatus.OK);
    }

    @PutMapping("/show-tags")
    public ResponseEntity<AccountResponse> showTags() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Account account = this.accountService.findAccountByEmail(authentication.getName());
        return new ResponseEntity<>(new AccountResponse(this.accountService.showTags(account)), HttpStatus.OK);
    }

    @GetMapping("/myTags")
    public ResponseEntity<List<Tag>> getUserTags(){
        return new ResponseEntity<>(this.accountService.getUserTags(), HttpStatus.OK);
    }

}
