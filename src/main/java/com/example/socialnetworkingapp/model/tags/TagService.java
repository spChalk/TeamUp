package com.example.socialnetworkingapp.model.tags;

import com.example.socialnetworkingapp.model.account.Account;
import com.example.socialnetworkingapp.model.account.AccountRepository;
import com.example.socialnetworkingapp.model.account.AccountService;
import lombok.AllArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Iterator;
import java.util.List;

import static org.assertj.core.util.Files.newFile;

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