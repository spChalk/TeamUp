package com.example.socialnetworkingapp.model.message;

import com.example.socialnetworkingapp.model.bio.Bio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {


}
