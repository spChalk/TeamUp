package com.example.socialnetworkingapp.model.connection_request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ConnectionRequestResponse {

    Long id;
    String sender;
    String receiver;
}