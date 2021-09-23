package com.example.socialnetworkingapp.mapper;

import com.example.socialnetworkingapp.model.account.Account;
import com.example.socialnetworkingapp.model.message.Message;
import com.example.socialnetworkingapp.model.message.MessageResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;
import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface MessageMapper {

    @Mapping(target = "senderFirstName", expression = "java(mapFirstName(message.getSender()))")
    @Mapping(target = "senderLastName", expression = "java(mapLastName(message.getSender()))")
    @Mapping(target = "senderEmail", expression = "java(mapEmail(message.getSender()))")
    @Mapping(target = "receiverFirstName", expression = "java(mapFirstName(message.getReceiver()))")
    @Mapping(target = "receiverLastName", expression = "java(mapLastName(message.getReceiver()))")
    @Mapping(target = "receiverEmail", expression = "java(mapEmail(message.getReceiver()))")
    @Mapping(target = "date", expression = "java(mapDate(message))")
    MessageResponse MessageToMessageResponse(Message message);

    default String mapFirstName(Account account){
        return account.getFirstName();
    }
    default LocalDateTime mapDate(Message message){
        return message.getDate();
    }
    default String mapLastName(Account account){
        return account.getLastName();
    }
    default String mapEmail(Account account) {
        return account.getUsername();
    }
}
