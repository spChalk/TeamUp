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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

        List<PostResponse> suffledPosts = postRepository.findAll().stream().map(postMapper::PostToPostResponse).collect(Collectors.toList());
        List<Account> userNetwork = user.getNetwork();
        List<PostResponse> postsToReturn = new ArrayList<>();

        /* If user has no friends, return only the posts he has made */
        if(userNetwork.isEmpty()) {
            for(PostResponse post: suffledPosts) {
                if( Objects.equals(user.getEmail(), post.getAuthorEmail()) ) {
                    postsToReturn.add(post);
                }
            }
            return postsToReturn;
        }

        /* For every user in my network */
        for(Account contact: userNetwork) {

            List<Like> likedPostsByFriend = this.likeService.findLikesByUserId(contact.getId());

            /* For every post */
            for(PostResponse post: suffledPosts) {
                /* If I've written this post or
                 * the current post has been written by a connected user,
                 * add the post to list. */
                if( Objects.equals(user.getEmail(), post.getAuthorEmail()) ||
                    Objects.equals(contact.getEmail(), post.getAuthorEmail())) {
                    if(!postsToReturn.contains(post)) {
                        postsToReturn.add(post);
                    }
                    continue;
                }
                /* Finally, check for the friend's liked posts.
                 * If the friend has liked the current post, add it to list. */
                for(Like likedPost: likedPostsByFriend) {
                    if(Objects.equals(likedPost.getPost().getId(), post.getId())) {
                        if(!postsToReturn.contains(post)) {
                            postsToReturn.add(post);
                        }
                        break;
                    }
                }
            }
        }
        return postsToReturn;
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
