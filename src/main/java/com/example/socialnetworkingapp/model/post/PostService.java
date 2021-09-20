package com.example.socialnetworkingapp.model.post;


import com.example.socialnetworkingapp.exception.UserNotFoundException;
import com.example.socialnetworkingapp.mapper.PostMapper;
import com.example.socialnetworkingapp.model.account.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;


    public PostResponse addPost(Post post){

        Post saved = postRepository.save(post);
        return new PostResponse(saved.getId(), saved.getPayload(), saved.getAuthor().getFirstName(),
                saved.getAuthor().getLastName(), saved.getAuthor().getEmail(),
                saved.getAuthor().getImageUrl(), saved.getAuthor().getDateCreated().toString(),
                saved.getImagePath(), saved.getVideoPath(), saved.getSoundPath());
    }

    public List<PostResponse> findAllPosts(){
        return postRepository.findAll().stream().map(postMapper::PostToPostResponse).collect(Collectors.toList());
    }

    public void deletePost(Long id){
        this.postRepository.deletePostById(id);
    }

    public Post findPostById(Long id){
        return postRepository.findPostById(id).orElseThrow( () -> new IllegalStateException("Post with id "+ id.toString() + " was not found !"));
    }

    public Post updatePost(Post p) {

        Optional<Post> postPresent = this.postRepository.findPostById(p.getId());
        if(!postPresent.isPresent()) {
            throw new IllegalStateException("Post with id " + p.getId() + " does not exist!");
        }

        Post post = postPresent.get();
        post.setPayload(p.getPayload());
        post.setAuthor(p.getAuthor());
        post.setDate(p.getDate());
        post.setImagePath(p.getImagePath());
        post.setVideoPath(p.getVideoPath());
        post.setSoundPath(p.getSoundPath());
        return this.postRepository.save(post);
    }
}
