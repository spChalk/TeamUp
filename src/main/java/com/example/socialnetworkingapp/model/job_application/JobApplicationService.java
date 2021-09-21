package com.example.socialnetworkingapp.model.job_application;

import com.example.socialnetworkingapp.mapper.JobApplicationMapper;
import com.example.socialnetworkingapp.model.job.Job;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class JobApplicationService {

    private final JobApplicationRepository jobApplicationRepository;
    private final JobApplicationMapper jobApplicationMapper;

    public List<JobApplicationResponse> findApplicationsByUserId(Long id) {
        return this.jobApplicationRepository.findAllApplicationsByUserId(id)
                .stream().map(jobApplicationMapper::JobApplicationToJobApplicationResponse).collect(Collectors.toList());
    }

    public List<JobApplicationResponse> findApplicationsByJobId(Long id) {
        return this.jobApplicationRepository.findAllApplicationsByJobId(id)
                .stream().map(jobApplicationMapper::JobApplicationToJobApplicationResponse).collect(Collectors.toList());
    }

    public JobApplicationResponse addJobApplication(JobApplication jobApplication) {
        List<JobApplication> jobapp = new ArrayList<>();
        jobapp.add(this.jobApplicationRepository.save(jobApplication));
        return jobapp.stream().map(jobApplicationMapper::JobApplicationToJobApplicationResponse).collect(Collectors.toList()).get(0);
    }

    public void deleteJobApplication(Long id) {
        this.jobApplicationRepository.deleteById(id);
    }

    public JobApplicationResponse findApplicationByUserAndJobIds(Long userId, Long jobId) {
        List<JobApplication> ja = new ArrayList<>();
        ja.add(this.jobApplicationRepository.findApplicationByUserAndJobIds(userId, jobId));
        return ja.stream().map(jobApplicationMapper::JobApplicationToJobApplicationResponse).collect(Collectors.toList()).get(0);
    }
}