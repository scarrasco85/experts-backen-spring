package com.scarrasco.expertosbackend.repository;

import com.scarrasco.expertosbackend.model.Tag.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {

    Tag findOneByName(String name);
}
