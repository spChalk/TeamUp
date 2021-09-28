package com.example.socialnetworkingapp.model.tags;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TagService {

    private TagRepository tagRepository;

    public List<Tag> getAllTags(){
        return this.tagRepository.findAll();
    }
    public Tag addTag(Tag tag) {

        Tag existingTag = this.tagRepository.findByTagName(tag.getTag());
        if(existingTag != null) { return existingTag; }
        return this.tagRepository.save(tag);
    }

    public Tag getTagByName(String tagName) {
        return this.tagRepository.findByTagName(tagName);
    }
}