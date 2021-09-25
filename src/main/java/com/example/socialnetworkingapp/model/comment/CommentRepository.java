package com.example.socialnetworkingapp.model.comment;

import com.example.socialnetworkingapp.model.bio.Bio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT c FROM Comment c WHERE c.post.id = ?1")
    List<Comment> findCommentsOfPostById(Long postid);

    @Query("SELECT c FROM Comment c WHERE c.post.id = ?1 AND c.id = ?2")
    Optional<Comment> findCommentByIds(Long postId, Long commentId);

    @Query("SELECT c FROM Comment c WHERE c.commenter.id = ?1")
    List<Comment> findAllByAccountId(Long id);

    @Query("SELECT c FROM Comment c WHERE c.commenter.id = ?1 AND c.post.id = ?2")
    List<Comment> findCommentsOfUserInAPost(Long uid, Long pid);
}
