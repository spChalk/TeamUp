package com.example.socialnetworkingapp.model.job_view;

import com.example.socialnetworkingapp.model.account.AccountService;
import com.example.socialnetworkingapp.model.job.JobService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class JobViewService {

    private final JobViewRepository jobViewRepository;
    private final AccountService accountService;
    private final JobService jobService;
/*

    public JobView findJobById(Long id) {
        return this.jobRepository.findById(id).
                orElseThrow(() -> new IllegalStateException("Job with id " + id + " was not found!" ));
    }

    public List<JobView> getJobs() {
        return this.jobRepository.findAll();
    }

    public JobView addJob(JobView job) {
        return this.jobRepository.save(job);
    }

    public JobView updateJob(JobView job) {
        return this.jobRepository.save(job);
    }

    public void deleteJob(Long id) {
        this.jobRepository.deleteById(id);
    }
*/

    public List<JobView> getViewsByJobId(Long id) {
        return this.jobViewRepository.getViewsByJobId(id).orElseThrow(
                () -> new IllegalStateException("Job with id: " + id.toString() + " does not exist!")
        );
    }

    public JobView addView(Long uid, Long jid) {

        Optional<JobView> jv = this.jobViewRepository.findViewByIds(uid, jid);
        if(jv.isPresent()) {
            jv.get().increaseViews();
            return this.jobViewRepository.save(jv.get());
        }
        return this.jobViewRepository.save(new JobView(
                this.accountService.findAccountById(uid),
                this.jobService.findJobById(jid)));
    }
}