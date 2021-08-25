package com.example.socialnetworkingapp.model.like;

import com.example.socialnetworkingapp.model.bio.Bio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/likes")
public class LikeController {

    private final LikeService likeService;

    @Autowired
    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @GetMapping("/{user_id}")
    public List<Like> getLikesByUserId(@PathVariable("user_id") Long id){
        return likeService.findLikesByUserId(id);
    }

    @PostMapping("/add")
    public ResponseEntity<Like> addLike(@RequestBody Like like){
        Like newLike = likeService.addLike(like);
        return new ResponseEntity<>(newLike, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{user_id}&{post_id}")
    public ResponseEntity<?> deleteLike(
            @PathVariable("user_id") Long user_id,
            @PathVariable("post_id") Long post_id) {
        likeService.deleteLike(user_id, post_id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}