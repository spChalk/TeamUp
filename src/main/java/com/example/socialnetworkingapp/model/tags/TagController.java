package com.example.socialnetworkingapp.model.tags;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/tags")
public class TagController {

    private TagService tagService;

    @PostConstruct
    public void createTags(){
        this.tagService.createTags();
    }
    @PostMapping("/add")
    public ResponseEntity<Tag> addTag(@RequestBody Tag tag) {
        return new ResponseEntity<>(this.tagService.addTag(tag), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Tag>> getAllTags(){
        return new ResponseEntity<>(this.tagService.getAllTags(), HttpStatus.OK);
    }

}