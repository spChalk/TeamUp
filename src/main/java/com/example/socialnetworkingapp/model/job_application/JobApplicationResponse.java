package com.example.socialnetworkingapp.model.job_application;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class JobApplicationResponse {

    private Long id;
    private String applicantFirstName;
    private String applicantLastName;
    private String applicantEmail;
    private String applicantImage;
    private Long jobId;
}