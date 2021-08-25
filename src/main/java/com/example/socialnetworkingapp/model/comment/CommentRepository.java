package com.example.socialnetworkingapp.model.comment;

import com.example.socialnetworkingapp.model.bio.Bio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {


}
