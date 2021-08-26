package com.example.socialnetworkingapp.model.post;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/* when a user makes a post take that post request an create a post */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {

    private String title;
    private String payload;
}
