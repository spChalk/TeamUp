package com.example.socialnetworkingapp.model.tags;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TagService {

    private TagRepository tagRepository;

    public Tag addTag(Tag tag) {

        if(this.tagRepository.findByTag(tag) != null) {
            return tag;
        }
        return this.tagRepository.save(tag);
    }
}