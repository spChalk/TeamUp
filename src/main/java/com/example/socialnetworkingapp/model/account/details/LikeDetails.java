package com.example.socialnetworkingapp.model.account.details;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LikeDetails {

    private String date;
    private String postAuthorFirstName;
    private String postAuthorLastName;
    private String postAuthorEmail;
    private String postPayload;
    private String postCreationDate;
    private String postImagePath;
    private String postVideoPath;
    private String postSoundPath;
}