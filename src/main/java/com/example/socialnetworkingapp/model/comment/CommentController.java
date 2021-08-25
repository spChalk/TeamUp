package com.example.socialnetworkingapp.model.comment;

import com.example.socialnetworkingapp.model.bio.Bio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/{post_id}")
    public List<Comment> getPostComments(@PathVariable("post_id") Long id) {
        return commentService.getPostComments(id);
    }

    @GetMapping("/{user_id}")
    public List<Comment> getUserComments(@PathVariable("user_id") Long id) {
        return commentService.getUserComments(id);
    }

    @PostMapping("/add")
    public ResponseEntity<Comment> addComment(@RequestBody Comment comment){
        Comment newComment = commentService.addComment(comment);
        return new ResponseEntity<>(newComment, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{cid}")
    public ResponseEntity<?> deleteCommentById(@PathVariable("cid") Long id){
        commentService.deleteComment(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}