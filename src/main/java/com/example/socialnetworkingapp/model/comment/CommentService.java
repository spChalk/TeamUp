package com.example.socialnetworkingapp.model.comment;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;


}