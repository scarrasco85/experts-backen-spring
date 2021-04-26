package com.scarrasco.expertosbackend.dao;

import com.scarrasco.expertosbackend.model.Tag.Tag;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface TagDao {

    List<Tag> findAllByFilters(Map<String, String> map1);

    Long getTotalCount();

    public Optional<Boolean> deleteTagsExpertsRelation(Long idTag);
}
