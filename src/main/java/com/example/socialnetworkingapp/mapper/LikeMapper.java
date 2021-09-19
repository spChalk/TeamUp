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
    LikeResponse LikeToLikeResponse(Like like);

    default String mapFirstName(Account account){
        return account.getFirstName();
    }
    default String mapLastName(Account account){
        return account.getLastName();
    }
}
