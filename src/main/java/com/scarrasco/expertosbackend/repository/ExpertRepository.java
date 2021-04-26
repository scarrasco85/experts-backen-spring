package com.scarrasco.expertosbackend.repository;

import com.scarrasco.expertosbackend.model.Expert.Expert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpertRepository extends JpaRepository<Expert, Long> {

    Expert findOneByNif(String nif);
}
