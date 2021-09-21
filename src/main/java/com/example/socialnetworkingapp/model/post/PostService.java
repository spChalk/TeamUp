package com.example.socialnetworkingapp.model.post;


import com.example.socialnetworkingapp.exception.UserNotFoundException;
import com.example.socialnetworkingapp.mapper.PostMapper;
import com.example.socialnetworkingapp.model.account.Account;
import com.example.socialnetworkingapp.model.account.AccountService;
import com.example.socialnetworkingapp.model.like.Like;
import com.example.socialnetworkingapp.model.like.LikeService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final AccountService accountService;
    private final LikeService likeService;


    public PostResponse addPost(Post post){

        Post saved = postRepository.save(post);
        return new PostResponse(saved.getId(), saved.getPayload(), saved.getAuthor().getFirstName(),
                saved.getAuthor().getLastName(), saved.getAuthor().getEmail(),
                saved.getAuthor().getImageUrl(), saved.getAuthor().getDateCreated().toString(),
                saved.getImagePath(), saved.getVideoPath(), saved.getSoundPath());
    }

    public List<PostResponse> findAllPosts(Account user) {

        /* Get user's network */
        List<Account> userNetwork = user.getNetwork();
        /* Get user's posts */
        List<PostResponse> postsToReturn = this.findPostsByAuthorId(user.getId());

        /* If user has no friends, return only the posts he has made */
        if(userNetwork.isEmpty()) {
            return postsToReturn
                    .stream()
                    .sorted(Comparator.comparing(PostResponse::getDate).reversed())
                    .collect(Collectors.toList());
        }

        /* For every user's connection in network */
        for(Account contact: userNetwork) {

            /* Get the posts of user's connections and add them to the list */
            List<PostResponse> friendPosts = this.findPostsByAuthorId(contact.getId());
            if(!friendPosts.isEmpty()) {
                postsToReturn.addAll(friendPosts);
            }

            /* Get user's connections' likes and extract the liked posts to 'likedPostsByFriend' */
            List<Like> friendLikes = this.likeService.findLikesByUserId(contact.getId());
            List<Post> likedPostsByFriend = new ArrayList<>();
            for(Like like: friendLikes) {
                likedPostsByFriend.add(like.getPost());
            }
            /* Add the posts to the list */
            if(!likedPostsByFriend.isEmpty()) {
                postsToReturn.addAll(likedPostsByFriend
                        .stream().map(postMapper::PostToPostResponse).collect(Collectors.toList()));
            }
        }
        /* Return the posts without duplicates and with the latest posts first! */
        return postsToReturn.stream().distinct().collect(Collectors.toList())
                .stream()
                .sorted(Comparator.comparing(PostResponse::getDate).reversed())
                .collect(Collectors.toList());
    }

    public List<PostResponse> findPostsByAuthorId(Long id) {
        return this.postRepository.findPostsByAuthorId(id).stream().map(postMapper::PostToPostResponse).collect(Collectors.toList());
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
