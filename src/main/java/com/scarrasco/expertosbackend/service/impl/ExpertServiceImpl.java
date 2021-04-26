package com.scarrasco.expertosbackend.service.impl;

import com.scarrasco.expertosbackend.dao.ExpertDao;
import com.scarrasco.expertosbackend.model.Expert.Expert;
import com.scarrasco.expertosbackend.model.Expert.ExpertStatus;
import com.scarrasco.expertosbackend.repository.ExpertRepository;
import com.scarrasco.expertosbackend.service.ExpertService;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class ExpertServiceImpl implements ExpertService {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private Session session;

    private final Logger log = LoggerFactory.getLogger(ExpertServiceImpl.class);

    private final ExpertRepository repository;

    private final ExpertDao expertDao;


    public ExpertServiceImpl(ExpertRepository repository, ExpertDao expertDao) {
        this.repository = repository;
        this.expertDao = expertDao;
    }


    /**
     * Get all experts with optionals filters options (String name, String tag, String modality, String status, String score, String pag, String limit)
     * Service Method
     * @param map1 map with all filters options (String name, String tag, String modality, String status, String score, String pag, String limit)
     * @return List of experts from database
     */
    @Override
    public List<Expert> findAll(Map<String, String> map1) {

        try {

            return this.expertDao.findAllByFilters(map1);

        }catch (Exception e){

            log.error(e.getMessage());
            e.printStackTrace();
            List<Expert> expertsError = new ArrayList<>();
            expertsError.add(new Expert().setId(-500L));

            return expertsError;
        }
    }

    /**
     * Get expert by ID - Service
     * @param id Primary key of Expert: Long
     * @return Optional<Expert> from database
     */
    @Override
    public Optional<Expert> findOne(Long id) {
        try {

            if(id == null)
                return Optional.empty();

            return this.repository.findById(id);

        }catch (Exception e){

            log.error(e.getMessage());
            Expert expertError = new Expert().setId(-500L);

            return Optional.of(expertError);
        }
    }

    /**
     * Create a new expert in database - Service
     * @param expert Expert to create
     * @return expert created in database
     */
    @Override
    public Expert createExpert(Expert expert) {

        try {

            Expert expertExist = repository.findOneByNif(expert.getNif());

            if (!ObjectUtils.isEmpty(expertExist))
                return new Expert();

            expert.setCreatedAt(LocalDateTime.now());
            expert.setStatus(ExpertStatus.pendiente);
            return repository.save(expert);

        }catch (Exception e){

            log.error(e.getMessage());
            return new Expert().setId(-1L);
        }
    }

    /**
     * It update a expert of database - Service
     * @param expert to update
     * @return expert updated in database
     */
    @Override
    public Expert updateExpert(Long id, Expert expert) {

        try {

            Optional<Expert> beforeExpert = repository.findById(id);

            if (beforeExpert.isPresent()){
                return new Expert().setId(-404L);
            }

            expert.setId(id);
            expert.setCreatedAt(beforeExpert.get().getCreatedAt());
            expert.setUpdatedAt(LocalDateTime.now());
            return repository.save(expert);

        }catch (Exception e){

            log.error(e.getMessage());
            return new Expert().setId(-500L);

        }
    }

    /**
     * Get total experts
     * @return total experts from database
     */
    @Override
    public Long getTotalCount() {

        try {

            return expertDao.getTotalCount();

        }catch (Exception e){
            log.error(e.getMessage());
            return -1L;
        }
    }

    /**
     * Delete expert of database by ID - Service
     * @param id expert primary key that you want to delete
     * @return Optional<Boolean>
     */
    @Override
    public Optional<Boolean> deleteOne(Long id) {

        try {
            if (id != null && repository.existsById(id)) {

                Optional<Boolean> result = expertDao.deleteExpertsTagsRelation(id);

                if (Objects.equals(result, Optional.of(true))){
                    repository.deleteById(id);
                }

                return Optional.of(true);
            }
            return Optional.of(false);
        }catch (Exception e){
            log.error(e.getMessage());
            return Optional.empty();
        }

    }

}
