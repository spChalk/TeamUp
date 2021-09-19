package com.example.socialnetworkingapp.mapper;

import com.example.socialnetworkingapp.model.account.Account;
import com.example.socialnetworkingapp.model.comment.Comment;
import com.example.socialnetworkingapp.model.comment.CommentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(target = "authorFirstName", expression = "java(mapFirstName(comment.getCommenter()))")
    @Mapping(target = "authorLastName", expression = "java(mapLastName(comment.getCommenter()))")
    @Mapping(target = "authorEmail", expression = "java(mapEmail(comment.getCommenter()))")
    @Mapping(target = "authorImage", expression = "java(mapImage(comment.getCommenter()))")
    CommentResponse CommentToCommentResponse(Comment comment);

    default String mapFirstName(Account account){
        return account.getFirstName();
    }
    default String mapLastName(Account account){
        return account.getLastName();
    }
    default String mapEmail(Account account) {
        return account.getUsername();
    }
    default String mapImage(Account account) {
        return account.getImageUrl();
    }
}
