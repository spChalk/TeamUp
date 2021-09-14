package com.example.socialnetworkingapp.model.tags;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface TagRepository  extends JpaRepository<Tag, Long> {
    Tag findByTag(Tag tag);
}