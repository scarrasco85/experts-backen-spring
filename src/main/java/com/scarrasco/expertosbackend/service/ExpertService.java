package com.scarrasco.expertosbackend.service;

import com.scarrasco.expertosbackend.model.Expert.Expert;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ExpertService {

    List<Expert> findAll(Map<String, String> map1);

    Optional<Expert> findOne(Long id);

    Expert createExpert(Expert expert);

    Expert updateExpert(Long id, Expert expert);

    Optional<Boolean> deleteOne(Long id);

    Long getTotalCount();
}
