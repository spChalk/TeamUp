package com.example.socialnetworkingapp.mapper;

import com.example.socialnetworkingapp.model.account.Account;
import com.example.socialnetworkingapp.model.like.Like;
import com.example.socialnetworkingapp.model.like.LikeResponse;
import com.example.socialnetworkingapp.model.post.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LikeMapper {

    @Mapping(target = "userFirstName", expression = "java(mapFirstName(like.getUser()))")
    @Mapping(target = "userLastName", expression = "java(mapLastName(like.getUser()))")
    @Mapping(target = "userEmail", expression = "java(mapEmail(like.getUser()))")
    @Mapping(target = "authorFirstName", expression = "java(mapFirstName(like.getPost()))")
    @Mapping(target = "authorLastName", expression = "java(mapLastName(like.getPost()))")
    @Mapping(target = "authorEmail", expression = "java(mapEmail(like.getPost()))")
    LikeResponse LikeToLikeResponse(Like like);

    default String mapFirstName(Account account){
        return account.getFirstName();
    }
    default String mapLastName(Account account){
        return account.getLastName();
    }
    default String mapEmail(Account account) {
        return account.getUsername();
    }
    default String mapFirstName(Post post){
        return post.getAuthor().getFirstName();
    }
    default String mapLastName(Post post){
        return post.getAuthor().getLastName();
    }
    default String mapEmail(Post post) {
        return post.getAuthor().getEmail();
    }
}