package com.example.socialnetworkingapp.model.tags;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/tags")
public class TagController {

    private TagService tagService;

    @PostMapping("/add")
    public ResponseEntity<Tag> addTag(@RequestBody Tag tag) {
        return new ResponseEntity<>(this.tagService.addTag(tag), HttpStatus.CREATED);
    }
}