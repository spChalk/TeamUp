package com.example.socialnetworkingapp.model.account.details;

import com.example.socialnetworkingapp.model.account.AccountUpdateRequest;
import com.example.socialnetworkingapp.model.education.EducationResponse;
import com.example.socialnetworkingapp.model.experience.ExperienceResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class FullAccountDetails {

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String imageUrl;
    private String dateCreated;

    private List<AccountDetails> network;
    private List<String> tags;

    private List<ExperienceDetails> experience;
    private List<EducationDetails> education;

    private String bio;

    private List<PostDetails> posts;
    private List<JobDetails> jobs;
    private List<LikeDetails> likes;
    private List<CommentDetails> comments;
}