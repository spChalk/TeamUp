package com.example.socialnetworkingapp.model.connection_request;

import com.example.socialnetworkingapp.model.bio.Bio;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/crequest")
public class ConnectionReqController {

    private final ConnectionReqService connectionReqService;

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

    /* Lists all received *PENDING* connection requests */
    @GetMapping("/{uid}")
    public ResponseEntity<List<ConnectionRequest>> getPendingConnectionRequests(@PathVariable("uid") Long uid) {
        return new ResponseEntity<>(this.connectionReqService.findPendingRequestsByAccId(uid), HttpStatus.OK);
    }

    @PutMapping("/accept")
    public ResponseEntity<HttpStatus> acceptConnectionRequests(@RequestBody ConnectionRequest connectionRequest) {
        this.connectionReqService.acceptRequest(connectionRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/reject")
    public ResponseEntity<HttpStatus> rejectConnectionRequests(@RequestBody ConnectionRequest connectionRequest) {
        this.connectionReqService.rejectRequest(connectionRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}