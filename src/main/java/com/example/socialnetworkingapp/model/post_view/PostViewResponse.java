package com.example.socialnetworkingapp.model.post_view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class PostViewResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String imageUrl;
}