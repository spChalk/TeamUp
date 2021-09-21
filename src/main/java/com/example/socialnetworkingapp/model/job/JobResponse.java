package com.example.socialnetworkingapp.model.job;

import com.example.socialnetworkingapp.model.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class JobResponse {

    private Long id;
    private String title;
    private String publisherFirstName;
    private String publisherLastName;
    private String publisherEmail;
    private String publisherImage;
    private String location;
    private String date;
    private JobType jobType;
    private ExperienceLevel experienceLevel;
    private String info;
    private List<Tag> tags;
}