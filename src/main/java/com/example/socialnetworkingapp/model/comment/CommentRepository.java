package com.example.socialnetworkingapp.model.comment;

import com.example.socialnetworkingapp.model.bio.Bio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT Comment FROM Comment WHERE Comment.post.id = ?1")
    List<Comment> findAllByPost(Long id);

    @Query("SELECT Comment FROM Comment WHERE Comment.commenter.id = ?1")
    List<Comment> findAllByUser(Long id);
}
