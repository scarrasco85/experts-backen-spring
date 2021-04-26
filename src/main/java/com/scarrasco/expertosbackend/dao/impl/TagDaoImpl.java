package com.scarrasco.expertosbackend.dao.impl;

import com.scarrasco.expertosbackend.dao.TagDao;
import com.scarrasco.expertosbackend.model.Tag.Tag;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class TagDaoImpl implements TagDao {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private Session session;

    private final Logger log = LoggerFactory.getLogger(TagDaoImpl.class);

    /**
     * Get all tag with optionals filters options (String name, String pag, String limit)
     * Dao Method
     * @param map1 map with all filters options (String name, String pag, String limit)
     * @return List of tags from database
     */
    @Override
    public List<Tag> findAllByFilters(Map<String, String> map1) {

        try {

            CriteriaBuilder builder = manager.getCriteriaBuilder();
            CriteriaQuery<Tag> criteria = builder.createQuery(Tag.class);
            Root<Tag> root = criteria.from(Tag.class);

            if (map1.get("name") != null){
                criteria.select(root).where(builder.like(root.get("name"), map1.get("name") + "%"));
            }

            TypedQuery<Tag> tagsQuery = manager.createQuery(criteria);

            if(map1.get("page")!=null && map1.get("limit")!=null){
                tagsQuery.setFirstResult(Integer.parseInt(map1.get("page")));
                tagsQuery.setMaxResults(Integer.parseInt(map1.get("limit")));
            }

            return tagsQuery.getResultList();

        }catch (Exception e) {

            log.error(e.getMessage());
            List<Tag> tagsError = new ArrayList<>();
            tagsError.add(new Tag().setId(-500L));

            return tagsError;

        }
    }

    /**
     * Get total tags
     * @return total tags from database
     */
    @Override
    public Long getTotalCount() {

        try {

            CriteriaBuilder builder = manager.getCriteriaBuilder();
            CriteriaQuery<Long> criteriaCount = builder.createQuery(Long.class);
            Root<Tag> rootCount = criteriaCount.from(Tag.class);
            criteriaCount.select((builder.countDistinct(rootCount)));

            return manager.createQuery(criteriaCount).getSingleResult();

        }catch (Exception e){
            log.error(e.getMessage());
            return -1L;
        }
    }

    /**
     * Delete all register relationated with Tags and Experts
     * @param idTag primary key of Tag need to delete of the relation
     * @return Boolean
     */
    @Override
    @Transactional
    public Optional<Boolean> deleteTagsExpertsRelation(Long idTag){

        try {
            if (idTag != null) {

                Query queryNative = manager.createNativeQuery("delete from experts_tags where id_tag = "+ idTag);
                queryNative.executeUpdate();

                return Optional.of(true);
            }
            return Optional.of(false);

        }catch (Exception e){
            log.error(e.getMessage());
            return Optional.empty();
        }
    }
}
