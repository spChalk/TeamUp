package com.example.socialnetworkingapp.model.connection_request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/crequest")
public class ConnectionReqController {

    private final ConnectionReqService connectionReqService;

    @Autowired
    public ConnectionReqController(ConnectionReqService connectionReqService) {
        this.connectionReqService = connectionReqService;
    }
}