package com.example.socialnetworkingapp.model.connection_request;

import com.example.socialnetworkingapp.model.bio.Bio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/crequest")
public class ConnectionReqController {

    private final ConnectionReqService connectionReqService;

    @Autowired
    public ConnectionReqController(ConnectionReqService connectionReqService) {
        this.connectionReqService = connectionReqService;
    }

    @GetMapping("/{acc_id}")
    public List<ConnectionRequest> getRequestsByAccId(@PathVariable("acc_id") Long id){
        return connectionReqService.findRequestsByAccId(id);
    }

    @PostMapping("/add")
    public ResponseEntity<ConnectionRequest> addRequest(@RequestBody ConnectionRequest connectionRequest){
        ConnectionRequest newConnectionRequest = connectionReqService.addRequest(connectionRequest);
        return new ResponseEntity<>(newConnectionRequest, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{req_id}")
    public ResponseEntity<?> deleteRequestById(@PathVariable("req_id") Long id){
        connectionReqService.deleteRequest(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}