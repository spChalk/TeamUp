package com.example.socialnetworkingapp.mapper;

import com.example.socialnetworkingapp.model.account.Account;
import com.example.socialnetworkingapp.model.comment.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {

//    private Long post_id;
//    private String payload;
//    private String authorFirstName;
//    private String authorLastName;
//    private String authorEmail;
//    private Date date;

/*
    @Mapping(target = "authorFirstName", expression = "java(mapFirstName(comment.getCommenter()))")
    @Mapping(target = "authorLastName", expression = "java(mapLastName(comment.getCommenter()))")
    @Mapping(target = "authorEmail", expression = "java(mapEmail(comment.getCommenter()))")
    CommentResponse CommentToCommentResponse(Comment comment);

    default String mapFirstName(Account account){
        return account.getFirstName();
    }
    default String mapLastName(Account account){
        return account.getLastName();
    }
    default String mapEmail(Account account) {
        return account.getUsername();
    }*/
}
