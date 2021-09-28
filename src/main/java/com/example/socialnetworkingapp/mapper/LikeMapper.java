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
    @Mapping(target = "postId", expression = "java(mapId(like.getPost()))")
    @Mapping(target = "imageUrl", expression = "java(mapImage(like.getUser()))")
    LikeResponse LikeToLikeResponse(Like like);

    default Long mapId(Post post){
        return post.getId();
    }

    default String mapImage(Account account){
        return account.getImageUrl();
    }

    default String mapFirstName(Account account){
        return account.getFirstName();
    }
    default String mapLastName(Account account){
        return account.getLastName();
    }
    default String mapEmail(Account account){
        return account.getEmail();
    }
}
