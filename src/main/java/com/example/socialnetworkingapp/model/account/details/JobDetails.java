package com.example.socialnetworkingapp.model.account.details;

import com.example.socialnetworkingapp.model.job.ExperienceLevel;
import com.example.socialnetworkingapp.model.job.JobType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class JobDetails {

    private String title;
    private String location;
    private String date;
    private String jobType;
    private String experienceLevel;
    private String info;
    private List<String> tags;
}