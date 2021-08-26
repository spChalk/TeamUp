package com.example.socialnetworkingapp.model.post;


import com.example.socialnetworkingapp.exception.UserNotFoundException;
import com.example.socialnetworkingapp.mapper.PostMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;


    public String addPost(Post post){
        postRepository.save(post);
        return "workeeeed ";
    }

    public List<PostResponse> findAllPosts(){
        return postRepository.findAll().stream().map(postMapper::PostToPostResponse).collect(Collectors.toList());
    }

    public void deletePost(Long id){
        postRepository.deletePostById(id);
    }

    public Post findPostById(Long id){
        return postRepository.findPostById(id).orElseThrow( () -> new UserNotFoundException("User by id "+ id + "was not found !"));
    }

}
