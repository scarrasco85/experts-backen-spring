package com.scarrasco.expertosbackend.dao;

import com.scarrasco.expertosbackend.model.Expert.Expert;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ExpertDao {

   List<Expert> findAllByFilters(Map<String, String> map1);

   Long getTotalCount();

   public Optional<Boolean> deleteExpertsTagsRelation(Long idExpert);

}
