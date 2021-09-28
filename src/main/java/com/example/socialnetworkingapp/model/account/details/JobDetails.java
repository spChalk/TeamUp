package com.example.socialnetworkingapp.model.account.details;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
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