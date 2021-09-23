package com.example.socialnetworkingapp.mapper;

import com.example.socialnetworkingapp.model.account.Account;
import com.example.socialnetworkingapp.model.connection_request.ConnectionRequest;
import com.example.socialnetworkingapp.model.connection_request.ConnectionRequestResponse;
import com.example.socialnetworkingapp.model.post.Post;
import com.example.socialnetworkingapp.model.post.PostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ConnectionRequestMapper {

    @Mapping(target = "id", expression = "java(mapId(connectionRequest.getId()))")
    @Mapping(target = "sender", expression = "java(mapSender(connectionRequest.getSender()))")
    @Mapping(target = "receiver", expression = "java(mapReceiver(connectionRequest.getReceiver()))")
    ConnectionRequestResponse ConnectionRequestToConnectionRequestResponse(ConnectionRequest connectionRequest);

    default Long mapId(Long id){
        return id;
    }
    default String mapSender(Account account){
        return account.getEmail();
    }
    default String mapReceiver(Account account){
        return account.getEmail();
    }
}
