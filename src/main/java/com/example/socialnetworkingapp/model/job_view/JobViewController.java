package com.example.socialnetworkingapp.model.job_view;

import com.example.socialnetworkingapp.model.account.Account;
import com.example.socialnetworkingapp.model.account.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/job_views")
@AllArgsConstructor
public class JobViewController {

    private final JobViewService jobViewService;
    private final AccountService accountService;

    @GetMapping("/{job_id}")
    public List<JobViewResponse> getViewsByJobId(@PathVariable("job_id") Long id){
        return jobViewService.getViewsByJobId(id);
    }

    @GetMapping("/sum/{job_id}")
    public Long getSumOfViewsByJobId(@PathVariable("job_id") Long id){
        return jobViewService.getSumOfViewsByJobId(id);
    }

    @PostMapping("/add/{jid}")
    public ResponseEntity<JobView> addView(@PathVariable("jid") Long jid) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Account currUser = accountService.findAccountByEmail(authentication.getName());
        return new ResponseEntity<>(jobViewService.addView(currUser, jid), HttpStatus.CREATED);
    }
}