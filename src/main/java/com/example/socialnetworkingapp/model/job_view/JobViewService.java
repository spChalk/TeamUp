package com.example.socialnetworkingapp.model.job_view;

import com.example.socialnetworkingapp.mapper.JobViewMapper;
import com.example.socialnetworkingapp.model.account.Account;
import com.example.socialnetworkingapp.model.job.JobService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class JobViewService {

    private final JobViewRepository jobViewRepository;
    private final JobService jobService;
    private final JobViewMapper jobViewMapper;

    public List<JobViewResponse> getViewsByJobId(Long id) {
        Optional<List<JobView>> views = this.jobViewRepository.getViewsByJobId(id);
        if(!views.isPresent()) {
            throw new IllegalStateException("Job with id: " + id.toString() + " does not exist!");
        }
        return views.get().stream().map(jobViewMapper::JobViewToJobViewResponse).collect(Collectors.toList());
    }

    public Long getSumOfViewsByJobId(Long id) {
        List<JobView> views = this.jobViewRepository.getViewsByJobId(id).orElseThrow(
                () -> new IllegalStateException("Job with id: " + id.toString() + " does not exist!")
        );
        Long sum = 0L;
        for(JobView view: views) {
            sum += view.getTimes();
        }
        return sum;
    }

    public JobView addView(Account account, Long jid) {

        Optional<JobView> jv = this.jobViewRepository.findViewByIds(account.getId(), jid);
        if(jv.isPresent()) {
            jv.get().increaseViews();
            return this.jobViewRepository.save(jv.get());
        }
        return this.jobViewRepository.save(new JobView(
                account,
                this.jobService.findJobById(jid)));
    }
}