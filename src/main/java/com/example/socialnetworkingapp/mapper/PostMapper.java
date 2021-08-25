package com.example.socialnetworkingapp.mapper;

import com.example.socialnetworkingapp.account.Account;
import com.example.socialnetworkingapp.post.Post;
import com.example.socialnetworkingapp.post.PostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {

    @Mapping(target = "authorFirstName", expression = "java(mapFirstName(post.getAuthor()))")
    @Mapping(target = "authorLastName", expression = "java(mapLastName(post.getAuthor()))")
    @Mapping(target = "authorEmail", expression = "java(mapEmail(post.getAuthor()))")
    @Mapping(target = "date", expression = "java(new java.util.Date())")
    PostResponse PostToPostResponse(Post post);

    default String mapFirstName(Account account){
        return account.getFirstName();
    }
    default String mapLastName(Account account){
        return account.getLastName();
    }
    default String mapEmail(Account account){
        return account.getUsername();
    }
//    Post PostResponseToPost(PostResponse postResponse);
}
