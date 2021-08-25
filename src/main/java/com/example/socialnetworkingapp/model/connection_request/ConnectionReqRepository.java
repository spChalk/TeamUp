package com.example.socialnetworkingapp.model.connection_request;

import com.example.socialnetworkingapp.model.bio.Bio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConnectionReqRepository extends JpaRepository<ConnectionRequest, Long> {


}
