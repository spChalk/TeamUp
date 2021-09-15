package com.example.socialnetworkingapp.model.tags;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface TagRepository  extends JpaRepository<Tag, Long> {
    Tag findByTag(Tag tag);

    @Query("SELECT t FROM Tag t WHERE t.tag = ?1")
    Tag findByTagName(String tag);
}