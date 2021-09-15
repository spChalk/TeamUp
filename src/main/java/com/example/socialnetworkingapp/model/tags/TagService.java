package com.example.socialnetworkingapp.model.tags;

import com.example.socialnetworkingapp.model.account.Account;
import com.example.socialnetworkingapp.model.account.AccountRepository;
import com.example.socialnetworkingapp.model.account.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TagService {

    private TagRepository tagRepository;

    public Tag addTag(Tag tag) {

        Tag existingTag = this.tagRepository.findByTagName(tag.getTag());
        if(existingTag != null) { return existingTag; }
        return this.tagRepository.save(tag);
    }

    public Tag getTagByName(String tagName) {
        return this.tagRepository.findByTagName(tagName);
    }
}