package com.scarrasco.expertosbackend.dao.impl;

import com.scarrasco.expertosbackend.dao.ExpertDao;
import com.scarrasco.expertosbackend.model.Expert.Expert;
import com.scarrasco.expertosbackend.model.Expert.ExpertStatus;
import com.scarrasco.expertosbackend.model.Tag.Tag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class ExpertDaoImpl implements ExpertDao {

    @PersistenceContext
    private EntityManager manager;

    private final Logger log = LoggerFactory.getLogger(ExpertDaoImpl.class);

    /**
     * Get all experts with optionals filters options
     * RepositoryDAO
     * @param map1 map with all filters options (String name, String tag, String modality, String status, String score, String pag, String limit)
     * @return List of expers
     */
    @Override
    public List<Expert> findAllByFilters(Map<String, String> map1){

        try {

            CriteriaBuilder builder = manager.getCriteriaBuilder();
            CriteriaQuery<Expert> criteria = builder.createQuery(Expert.class);
            Root<Expert> root = criteria.from(Expert.class);

            List<Predicate> predicates = buildPredicatesFilter(map1, builder, root);

            criteria.distinct(true).select(root).where(builder.and(predicates.toArray(new Predicate[0])));

            TypedQuery<Expert> tagsQuery = manager.createQuery(criteria);

            if(map1.get("page")!=null && map1.get("limit")!=null){
                tagsQuery.setFirstResult(Integer.parseInt(map1.get("page")));
                tagsQuery.setMaxResults(Integer.parseInt(map1.get("limit")));
            }

            return tagsQuery.getResultList();

        }catch (Exception e){

            log.error(e.getMessage());
            List<Expert> expertsError = new ArrayList<>();
            expertsError.add(new Expert().setId(-500L));

            return expertsError;
        }
    }

    /**
     * Get total experts
     * @return total experts from database
     */
    @Override
    public Long getTotalCount() {

        try {

            CriteriaBuilder builder = manager.getCriteriaBuilder();
            CriteriaQuery<Long> criteriaCount = builder.createQuery(Long.class);
            Root<Expert> rootCount = criteriaCount.from(Expert.class);
            criteriaCount.select((builder.countDistinct(rootCount)));

            return manager.createQuery(criteriaCount).getSingleResult();

        }catch (Exception e){
            log.error(e.getMessage());
            return -1L;
        }
    }


    /**
     * Build the predicates needed to create the filter search sql query
     * @param map1 Filters of
     * @param builder
     * @param root
     * @return predicates to sql search
     */
    private List<Predicate> buildPredicatesFilter(Map<String, String> map1,CriteriaBuilder builder, Root<Expert> root){

        List<Predicate> predicates = new ArrayList<>();

        if (map1.get("name") != null)
            predicates.add(builder.like(root.get("name"), map1.get("name") + "%"));

        if (map1.get("tag") != null){
            Join<Expert, Tag> rootTags = root.join("tags");
            predicates.add(builder.equal(rootTags.get("id"), map1.get("tag")));
        }

        if (map1.get("modality") != null)
            predicates.add(builder.equal(root.get("modality"), map1.get("modality")));

        if (map1.get("status") != null)
            predicates.add(builder.equal(root.get("status"), ExpertStatus.valueOf(map1.get("status"))));

        if (map1.get("score") != null)
            predicates.add(builder.equal(root.get("score"), Integer.parseInt(map1.get("score"))));


        return predicates;
    }

    /**
     * Delete all register relationated with Experts and Tags
     * @param idExpert primary key of Expert need to delete of the relation
     * @return Boolean
     */
    @Override
    @Transactional
    public Optional<Boolean> deleteExpertsTagsRelation(Long idExpert) {
        try {
            if (idExpert != null) {

                Query queryNative = manager.createNativeQuery("delete from experts_tags where id_expert = "+ idExpert);
                queryNative.executeUpdate();

                return Optional.of(true);
            }
            return Optional.of(false);

        }catch (Exception e){
            log.error(e.getMessage());
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
