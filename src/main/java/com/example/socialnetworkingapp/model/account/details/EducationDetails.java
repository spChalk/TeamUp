package com.example.socialnetworkingapp.model.account.details;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EducationDetails {

    private String school;
    private String degree;
    private String field;
    private String startDate;
    private String endDate;
    private float grade;
    private String description;
}