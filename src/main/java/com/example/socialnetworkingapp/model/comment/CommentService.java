package com.example.socialnetworkingapp.model.comment;

import com.example.socialnetworkingapp.mapper.CommentMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;


    public List<CommentResponse> findAll() {
        return commentRepository.findAll().stream().map(commentMapper::CommentToCommentResponse).collect(Collectors.toList());
    }

    public List<CommentResponse> findAllComments() {
        return commentRepository.findAll().stream().map(commentMapper::CommentToCommentResponse).collect(Collectors.toList());
    }

    public List<CommentResponse> findAllCommentsOfPost(Long postid) {
        return commentRepository.findCommentsOfPostById(postid).stream().map(commentMapper::CommentToCommentResponse).collect(Collectors.toList());
    }

    public String addComment(Comment comment){
        commentRepository.save(comment);
        return "workeeeed ";
    }
}