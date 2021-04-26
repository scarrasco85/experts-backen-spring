package com.scarrasco.expertosbackend.service.impl;

import com.scarrasco.expertosbackend.dao.TagDao;
import com.scarrasco.expertosbackend.model.Tag.Tag;
import com.scarrasco.expertosbackend.repository.TagRepository;
import com.scarrasco.expertosbackend.service.TagService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class TagServiceImpl implements TagService {

    private final Logger log = LoggerFactory.getLogger(TagServiceImpl.class);

    private final TagRepository repository;

    private final TagDao tagDao;

    public TagServiceImpl(TagRepository repository, TagDao tagDao) {
        this.repository = repository;
        this.tagDao = tagDao;
    }

    /**
     * Get all tag with optionals filters options (String name, String pag, String limit)
     * Service Method
     * @param map1 map with all filters options (String name, String pag, String limit)
     * @return List of tags from database
     */
    @Override
    public List<Tag> findAll(Map<String, String> map1)  {

        try {

            return this.tagDao.findAllByFilters(map1);

        }catch (Exception e){

            log.error(e.getMessage());
            List<Tag> tagsError = new ArrayList<>();
            tagsError.add(new Tag().setId(-500L));

            return tagsError;
        }
    }


    /**
     * Get tag by ID - Service
     * @param id Primary key of Tag: Long
     * @return Optional<Tag> from database
     */
    @Override
    public Optional<Tag> findOne(Long id) {
        try {

            if(id == null)
                return Optional.empty();

            return this.repository.findById(id);

        }catch (Exception e){

            log.error(e.getMessage());
            Tag tagError = new Tag().setId(-500L);

            return Optional.of(tagError);
        }
    }

    /**
     * Create a new tag in database - Service
     * @param tag Tag to create
     * @return tag created in database
     */
    @Override
    public Tag createTag(Tag tag) {

        try {

            Tag tagExist = repository.findOneByName(tag.getName());

            if (!ObjectUtils.isEmpty(tagExist))
                return new Tag();

            tag.setCreatedAt(LocalDateTime.now());
            tag.setUpdatedAt(null);
            return repository.save(tag);

        }catch (Exception e){

            log.error(e.getMessage());
            return new Tag().setId(-500L);
        }
    }

    /**
     * It update a tag of database - Service
     * @param tag to update
     * @return tag updated in database
     */
    @Override
    public Tag updateTag(Long id, Tag tag) {

        try {

            Optional<Tag> beforeTag = repository.findById(id);

            if (beforeTag.isPresent()){
                return new Tag().setId(-404L);
            }

            tag.setId(id);
            tag.setCreatedAt(beforeTag.get().getCreatedAt());
            tag.setUpdatedAt(LocalDateTime.now());
            return repository.save(tag);

        }catch (Exception e){

            log.error(e.getMessage());
            return new Tag().setId(-500L);

        }
    }

    /**
     * Delete tag of database by ID - Service
     * @param id tag primary key that you want to delete
     * @return Optional<Boolean>
     */
    @Override
    public Optional<Boolean> deleteOne(Long id) {

        try {
            if (id != null && repository.existsById(id)) {

                Optional<Boolean> result = tagDao.deleteTagsExpertsRelation(id);

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

    /**
     * Get total tags
     * @return total tags from database
     */
    @Override
    public Long getTotalCount() {

        try {

            return tagDao.getTotalCount();

        }catch (Exception e){
            log.error(e.getMessage());
            return -1L;
        }
    }

}
