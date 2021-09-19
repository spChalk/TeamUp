package com.example.socialnetworkingapp.model.comment;

import com.example.socialnetworkingapp.mapper.CommentMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
        Comment cm =  this.commentRepository.save(comment);
        return new CommentResponse(cm.getId(), cm.getPayload(), cm.getCommenter().getFirstName(),
                cm.getCommenter().getLastName(), cm.getCommenter().getEmail(), cm.getCommenter().getImageUrl(),
                cm.getDate());
    }

    public CommentResponse updateComment(Long commentId, CommentRequest payload) {
        Optional<Comment> existingComment = this.commentRepository.findById(commentId);
        if(existingComment.isPresent()) {
            existingComment.get().setPayload(payload.getPayload());
        } else throw  new IllegalStateException("Comment not found!");

        Comment cm = this.commentRepository.save(existingComment.get());
        return new CommentResponse(cm.getId(), cm.getPayload(), cm.getCommenter().getFirstName(),
                cm.getCommenter().getLastName(), cm.getCommenter().getEmail(), cm.getCommenter().getImageUrl(),
                cm.getDate());
    }

    public void deleteById(Long commentId) {
        this.commentRepository.deleteById(commentId);
    }

    public List<CommentResponse> findAllCommentsOfUser(Long id) {
        return this.commentRepository.findAllByAccountId(id).stream().map(commentMapper::CommentToCommentResponse).collect(Collectors.toList());
    }
}