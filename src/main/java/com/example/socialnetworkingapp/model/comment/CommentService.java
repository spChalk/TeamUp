package com.example.socialnetworkingapp.model.comment;

import com.example.socialnetworkingapp.mapper.CommentMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    /*public List<CommentResponse> findAll() {
        return commentRepository.findAll().stream().map(commentMapper::CommentToCommentResponse).collect(Collectors.toList());
    }

    public List<CommentResponse> findAllComments() {
        return commentRepository.findAll().stream().map(commentMapper::CommentToCommentResponse).collect(Collectors.toList());
    }
*/
    public List<CommentResponse> findAllCommentsOfPost(Long postId) {
        return this.commentRepository.findCommentsOfPostById(postId).stream().map(commentMapper::CommentToCommentResponse).collect(Collectors.toList());
    }

    public CommentResponse addComment(Comment comment){
        List<Comment> cm = new ArrayList<>();
        cm.add(this.commentRepository.save(comment));
        return cm.stream().map(commentMapper::CommentToCommentResponse).collect(Collectors.toList()).get(0);
    }

    public CommentResponse updateComment(Long commentId, CommentRequest payload) {
        Optional<Comment> existingComment = this.commentRepository.findById(commentId);
        if(existingComment.isPresent()) {
            existingComment.get().setPayload(payload.getPayload());
        } else throw  new IllegalStateException("Comment not found!");

        List<Comment> cm = new ArrayList<>();
        cm.add(this.commentRepository.save(existingComment.get()));
        return cm.stream().map(commentMapper::CommentToCommentResponse).collect(Collectors.toList()).get(0);
    }

    public void deleteById(Long commentId) {
        this.commentRepository.deleteById(commentId);
    }

    public List<CommentResponse> findAllCommentsOfUser(Long id) {
        return this.commentRepository.findAllByAccountId(id).stream().map(commentMapper::CommentToCommentResponse).collect(Collectors.toList());
    }

    public List<Comment> findCommentsByUserId(Long id) {
        return this.commentRepository.findAllByAccountId(id);
    }
}