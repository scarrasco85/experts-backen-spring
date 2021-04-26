package com.scarrasco.expertosbackend.service;

import com.scarrasco.expertosbackend.model.Tag.Tag;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface TagService {

    List<Tag> findAll(Map<String, String> map1);

    Optional<Tag> findOne(Long id);

    Tag createTag(Tag tag);

    Tag updateTag(Long id, Tag tag);

    Optional<Boolean> deleteOne(Long id);

    Long getTotalCount();
}
