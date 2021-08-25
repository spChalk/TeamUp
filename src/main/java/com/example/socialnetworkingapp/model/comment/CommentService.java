package com.example.socialnetworkingapp.model.comment;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public List<Comment> getPostComments(Long post_id) {
        return this.commentRepository.findAllByPost(post_id);
    }

    public List<Comment> getUserComments(Long user_id) {
        return this.commentRepository.findAllByUser(user_id);
    }

    public Comment addComment(Comment comment) {
        return this.commentRepository.save(comment);
    }

    public void deleteComment(Long id) {
        this.commentRepository.deleteById(id);
    }
}