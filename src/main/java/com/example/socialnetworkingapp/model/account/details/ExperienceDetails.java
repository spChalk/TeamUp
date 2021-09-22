package com.example.socialnetworkingapp.model.account.details;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ExperienceDetails {

    private String title;
    private String employmentType;
    private String company;
    private String location;
    private String startDate;
    private String endDate;
    private String headline;
    private String description;
}