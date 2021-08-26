package com.example.socialnetworkingapp.model.comment;

import com.example.socialnetworkingapp.model.bio.Bio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {



    @Query(value = "Select c from comments c WHERE c.post_id = ?1", nativeQuery = true)
    List<Comment> findCommentsOfPostById(Long postid);
}
