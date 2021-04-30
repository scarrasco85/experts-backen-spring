package com.scarrasco.expertosbackend.controller;

import com.scarrasco.expertosbackend.model.Expert.Expert;
import com.scarrasco.expertosbackend.service.ExpertService;
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
// Permitir en producción
@CrossOrigin(origins = "https://proyecto-experts.vercel.app", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.DELETE})
//Permitir en local
//@CrossOrigin(origins = "http://localhost:4200", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.DELETE})
public class ExpertController {

    private final ExpertService expertService;

    public ExpertController(ExpertService expertService) {
        this.expertService = expertService;
    }

    /**
     * Get all experts
     * @return List of experts from database
     */
    @GetMapping("/expertos")
    @ApiOperation("Get all experts")
    public ResponseEntity<List<Expert>> findAll(
            @ApiParam("Expert name for search (optional): String") @QueryParam("name") String name,
            @ApiParam("Tag name for search (optional): String") @QueryParam("tag") String tag,
            @ApiParam("Expert modality for search (optional): String") @QueryParam("modality") String modality,
            @ApiParam("Expert status for search (optional): ExpertStatus (pendiente / validado)") @QueryParam("status") String status,
            @ApiParam("Expert score for search (optional): Integer") @QueryParam("score") String score,
            @ApiParam("Pagination: page from which the records start to be displayed (optional): Integer") @QueryParam("page") String page,
            @ApiParam("Pagination: number of records displayed per page (optional): Integer") @QueryParam("limit") String limit
        ) {

        Map<String, String> map1 = new HashMap<>();
        map1.put("name", name);
        map1.put("tag", tag);
        map1.put("modality", modality);
        map1.put("status", status);
        map1.put("score", score);
        map1.put("page", page);
        map1.put("limit", limit);

        List<Expert> expertsDB = expertService.findAll(map1);

        if (expertsDB.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        if (expertsDB.get(0).getId()== -500L)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        return ResponseEntity.ok().body(expertsDB);
    }

    /**
     * Get expert by ID
     * @param id Primary key of Expert: Long
     * @return Expert from database
     */
    @GetMapping("/expertos/{id}")
    @ApiOperation("Get expert by Id")
    public ResponseEntity<Expert> findOne(@ApiParam("Primary key of expert: Long") @PathVariable Long id){

        Optional<Expert> expertOpt = expertService.findOne(id);

        if(!expertOpt.isPresent())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        if (expertOpt.get().getId() == -500L)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        return ResponseEntity.ok().body(expertOpt.get());
    }

    /**
     * Get total experts
     * @return total experts from database
     */
    @GetMapping("/expertos/total")
    @ApiOperation("Get total experts from database")
    public ResponseEntity<Long> getTotalCount(){

        Long result = expertService.getTotalCount();

        if(result == -1)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        return ResponseEntity.ok().body(result);
    }

    /**
     * Create a new expert in database
     * @param expert Expert to create
     * @return expert Expert created
     * @throws URISyntaxException
     */
    @PostMapping("/expertos")
    @ApiOperation("Create a new expert in DB")
    public ResponseEntity<Expert> createExpert(@ApiParam("Expert that you want to create: Expert") @RequestBody Expert expert) throws URISyntaxException {

        if(expert.getId() != null || ObjectUtils.isEmpty(expert))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Expert expertDB = expertService.createExpert(expert);

        if (expertDB.getId() == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        if (expertDB.getId() == -1L)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        return ResponseEntity
                .created(new URI("/api/expertos/" + expertDB.getId()))
                .body(expertDB);
    }

    /**
     * It update a expert of database
     * @param expert to update
     * @return expert updated in database
     */
    @PutMapping("/expertos/{id}")
    @ApiOperation("Update an existing expert in DB")
    public ResponseEntity<Expert> updateExpert(
            @ApiParam("id of Expert that you want to update: Long") @PathVariable Long id,
            @ApiParam("Expert that you want to update: Expert") @RequestBody Expert expert
    ){

//        if(id == null || ObjectUtils.isEmpty(expert) || expert.getId() != null || !ObjectUtils.isEmpty(expert.getCreatedAt()))
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

         if(id == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Expert expertUpdated = expertService.updateExpert(id, expert);

        if (expertUpdated.getId() == -404L)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        if (expertUpdated.getId() == -500L)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        return ResponseEntity.ok().body(expertUpdated);
    }

    /**
     * Delete expert of database by ID
     * @param id expert primary key that you want to delete
     * @return void
     */
    @DeleteMapping("/expertos/{id}")
    @ApiOperation("Delete expert of DB by Id")
    public ResponseEntity<Void> deleteExpert(@ApiParam("Primary key of expert: Long") @PathVariable Long id){

        if (id == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Optional<Boolean> result = expertService.deleteOne(id);

        if (Objects.equals(result, Optional.of(false)))
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        if (!result.isPresent())
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        return ResponseEntity.noContent().build();
    }
}
