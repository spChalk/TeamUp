package com.example.socialnetworkingapp.model.job;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
*   1. Get all jobs (List<Job>)
*   2. Get all users (List<Account>)
*   3. Transform List<Job> -> ArrayList<Job>
*   4. Transform List<Account> -> ArrayList<Account>
*   5. Map {Job.id: index in ArrayList<Job>}
*   6. Map {Account.id: index in ArrayList<Account>}
*   7. Get all job views (List<JobView>)
*       7.1 If list of job views is empty, create an array of tuples (0, job-id) for all the jobs and proceed to (13).
*       7.2 Else, proceed to 8.
*       7.3 END
*   8. Make a zeroed 2D matrix, where   (index in x axis === index in array of Jobs),
*                                       (index in y axis === index in array of Accounts)
*                                   and ( {x, y} === Job view with job.id: Jobs[x].id and viewer.id: Accounts[y].id )
*                                   and fill up the existing views (traverse the list of views and use the hash tables
*                                   to fill up the matrix).
*   9. If Current user has seen EVERY job, (his row has NO zeros), proceed to (11).
*       9.1 Else, proceed to 10.
*       9.2 END
*   10. Run matrix factorization.
*   11. Get the row of current user (M[i] s.t Accounts[i].id === MY ID).
*   12. From the previous row, create an array of tuples (views, job-id).
*
*   13. TAG FILTERING
*   ------------------
*   14. For every Job with id === tuple.job-id, see how many tags the job and the user have in common
*           and for every tag, add 1 to views.
*   15. SORT the array of tuples, by views.
*   16. Make an empty list, iterate the tuple array, and push() all the Job classes s.t Job.id == tuple.job-id
*   17. Return the list of jobs.
* */

@RestController
@RequestMapping("/jobs")
@AllArgsConstructor
public class JobController {

    private final JobService jobService;

    @GetMapping("/{job_id}")
    public Job getJobById(@PathVariable("job_id") Long id){
        return jobService.findJobById(id);
    }

    @GetMapping("/all")
    public List<Job> getJobs(){
        return jobService.getJobs();
    }

    @PostMapping("/add")
    public ResponseEntity<Job> addJob(@RequestBody Job job) {
        return new ResponseEntity<>(jobService.addJob(job), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Job> updateJob(@RequestBody Job job){
        Job newJob = jobService.updateJob(job);
        return new ResponseEntity<>(newJob, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteJobById(@PathVariable("id") Long id){
        jobService.deleteJob(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}