package com.scarrasco.expertosbackend.controller;

import com.scarrasco.expertosbackend.model.Tag.Tag;
import com.scarrasco.expertosbackend.service.TagService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import javax.ws.rs.QueryParam;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "https://probando-expert-frontend-produccion2.vercel.app", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.DELETE})
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    /**
     * Get all tags
     * @return List of tags from database
     */
    @GetMapping("/etiquetas")
    @ApiOperation("Get all tags")
    public ResponseEntity<List<Tag>> findAll(
            @QueryParam("name") String name,
            @QueryParam("page") String page,
            @QueryParam("limit") String limit
            ) {

        Map<String, String> map1 = new HashMap<>();
        map1.put("name", name);
        map1.put("page", page);
        map1.put("limit", limit);


        List<Tag> tagsDB = tagService.findAll(map1);

        if (tagsDB.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        if (tagsDB.get(0).getId()== -500L)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        return ResponseEntity.ok().body(tagsDB);
    }

    /**
     * Get tag by ID
     * @param id Primary key of Tag: Long
     * @return Tag from database
     */
    @GetMapping("/etiquetas/{id}")
    @ApiOperation("Get tag by Id")
    public ResponseEntity<Tag> findOne(@ApiParam("Primary key of tag: Long") @PathVariable Long id){

        Optional<Tag> tagOpt = tagService.findOne(id);

        if(tagOpt.isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        if (tagOpt.get().getId() == -500L)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        return ResponseEntity.ok().body(tagOpt.get());
    }

    /**
     * Get total tags
     * @return total tags from database
     */
    @GetMapping("/etiquetas/total")
    @ApiOperation("Get total tags from database")
    public ResponseEntity<Long> getTotalCount(){

        Long result = tagService.getTotalCount();

        if(result == -1)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        return ResponseEntity.ok().body(result);
    }

    /**
     * Create a new tag in database
     * @param tag Tag to create
     * @return tag Tag created
     * @throws URISyntaxException
     */
    @PostMapping("/etiquetas")
    @ApiOperation("Create a new tag in DB")
    public ResponseEntity<Tag> createTag(@ApiParam("Tag that you want to create: Tag") @RequestBody Tag tag) throws URISyntaxException {

        if(tag.getId() != null || ObjectUtils.isEmpty(tag))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Tag tagDB = tagService.createTag(tag);

        if (tagDB.getId() == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        if (tagDB.getId() == -500L)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        return ResponseEntity
                .created(new URI("/api/etiquetas/" + tagDB.getId()))
                .body(tagDB);
    }

    /**
     * It update a tag of database
     * @param tag to update
     * @return tag updated in database
     */
    @PutMapping("/etiquetas/{id}")
    @ApiOperation("Update an existing tag in DB")
    public ResponseEntity<Tag> updateTag(
            @ApiParam("id of Tag that you want to update: Long") @PathVariable Long id,
            @ApiParam("Tag that you want to update: Tag") @RequestBody Tag tag
    ){

        if(id == null || ObjectUtils.isEmpty(tag) || tag.getId() != null || !ObjectUtils.isEmpty(tag.getCreatedAt()))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Tag tagUpdated = tagService.updateTag(id, tag);

        if (tagUpdated.getId() == -404L)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        if (tagUpdated.getId() == -500L)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        return ResponseEntity.ok().body(tagUpdated);
    }

    /**
     * Delete tag of database by ID
     * @param id tag primary key that you want to delete
     * @return void
     */

    @DeleteMapping("/etiquetas/{id}")
    @ApiOperation("Delete tag of DB by Id")
    public ResponseEntity<Void> deleteTag(@ApiParam("Primary key of tag: Long") @PathVariable Long id) {

            if (id == null)
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

            Optional<Boolean> result = tagService.deleteOne(id);

            if (Objects.equals(result, Optional.of(false)))
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            if (result.isPresent())
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

            return ResponseEntity.noContent().build();

    }
}
