package com.example.socialnetworkingapp.model.job;

import com.example.socialnetworkingapp.model.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class JobRequest {

    private String title;
    private String location;
    private JobType jobType;
    private ExperienceLevel experienceLevel;
    private String info;
    private List<Tag> tags;
}