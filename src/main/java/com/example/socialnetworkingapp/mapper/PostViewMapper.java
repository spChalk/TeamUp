package com.example.socialnetworkingapp.mapper;

import com.example.socialnetworkingapp.model.account.Account;
import com.example.socialnetworkingapp.model.post_view.PostView;
import com.example.socialnetworkingapp.model.post_view.PostViewResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostViewMapper {

    @Mapping(target = "id", expression = "java(mapId(postView.getId()))")
    @Mapping(target = "firstName", expression = "java(mapFirstName(postView.getViewer()))")
    @Mapping(target = "lastName", expression = "java(mapLastName(postView.getViewer()))")
    @Mapping(target = "email", expression = "java(mapEmail(postView.getViewer()))")
    @Mapping(target = "imageUrl", expression = "java(mapImage(postView.getViewer()))")
    PostViewResponse PostViewToPostViewResponse(PostView postView);

    default Long mapId(Long id) {
        return id;
    }
    default String mapFirstName(Account account){
        return account.getFirstName();
    }
    default String mapLastName(Account account){
        return account.getLastName();
    }
    default String mapEmail(Account account){
        return account.getUsername();
    }
    default String mapImage(Account account){
        return account.getImageUrl();
    }
}