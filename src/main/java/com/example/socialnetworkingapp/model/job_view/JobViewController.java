package com.example.socialnetworkingapp.model.job_view;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/job_views")
@AllArgsConstructor
public class JobViewController {

    private final JobViewService jobViewService;

    @GetMapping("/{job_id}")
    public List<JobView> getViewsByJobId(@PathVariable("job_id") Long id){
        return jobViewService.getViewsByJobId(id);
    }

    @GetMapping("/sum/{job_id}")
    public Long getSumOfViewsByJobId(@PathVariable("job_id") Long id){
        return jobViewService.getSumOfViewsByJobId(id);
    }

    @PostMapping("/add/{uid}/{jid}")
    public ResponseEntity<JobView> addView(@PathVariable("uid") Long uid,
                                           @PathVariable("jid") Long jid) {
        return new ResponseEntity<>(jobViewService.addView(uid, jid), HttpStatus.CREATED);
    }

    /*@GetMapping("/{job_id}")
    public JobView getJobById(@PathVariable("job_id") Long id){
        return jobService.findJobById(id);
    }

    @GetMapping("/all")
    public List<JobView> getJobs(){
        return jobService.getJobs();
    }

    @PostMapping("/add")
    public ResponseEntity<JobView> addJob(@RequestBody JobView job) {
        return new ResponseEntity<>(jobService.addJob(job), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<JobView> updateJob(@RequestBody JobView job){
        JobView newJob = jobService.updateJob(job);
        return new ResponseEntity<>(newJob, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteJobById(@PathVariable("id") Long id){
        jobService.deleteJob(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
*/
}