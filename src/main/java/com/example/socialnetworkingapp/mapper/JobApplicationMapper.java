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
    @Mapping(target = "applicantFirstName", expression = "java(mapFirstName(jobApplication.getApplicant()))")
    @Mapping(target = "applicantLastName", expression = "java(mapLastName(jobApplication.getApplicant()))")
    @Mapping(target = "applicantEmail", expression = "java(mapEmail(jobApplication.getApplicant()))")
    @Mapping(target = "applicantImage", expression = "java(mapImage(jobApplication.getApplicant()))")
    @Mapping(target = "jobId", expression = "java(mapJobId(jobApplication.getJob()))")
    JobApplicationResponse JobApplicationToJobApplicationResponse(JobApplication jobApplication);

    default Long mapId(Long id){
        return id;
    }
    default String mapFirstName(Account account) {
        return account.getFirstName();
    }
    default String mapLastName(Account account) {
        return account.getLastName();
    }
    default String mapEmail(Account account) {
        return account.getEmail();
    }
    default String mapImage(Account account) {
        return account.getImageUrl();
    }
    default Long mapJobId(Job job) {
        return job.getId();
    }
}
