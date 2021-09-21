package com.example.socialnetworkingapp.mapper;

import com.example.socialnetworkingapp.model.account.Account;
import com.example.socialnetworkingapp.model.comment.Comment;
import com.example.socialnetworkingapp.model.comment.CommentResponse;
import com.example.socialnetworkingapp.model.job.Job;
import com.example.socialnetworkingapp.model.job_application.JobApplication;
import com.example.socialnetworkingapp.model.job_application.JobApplicationResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface JobApplicationMapper {

    @Mapping(target = "id", expression = "java(mapId(jobApplication.getId()))")
    @Mapping(target = "applicantEmail", expression = "java(mapEmail(jobApplication.getApplicant()))")
    @Mapping(target = "jobId", expression = "java(mapJobId(jobApplication.getJob()))")
    JobApplicationResponse JobApplicationToJobApplicationResponse(JobApplication jobApplication);

    default Long mapId(Long id){
        return id;
    }
    default String mapEmail(Account account) {
        return account.getEmail();
    }
    default Long mapJobId(Job job) {
        return job.getId();
    }
}
