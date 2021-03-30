package com.khmt.myapp.web.rest;

import com.khmt.myapp.domain.Giasach;
import com.khmt.myapp.repository.GiasachRepository;
import com.khmt.myapp.service.GiasachService;
import com.khmt.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.khmt.myapp.domain.Giasach}.
 */
@RestController
@RequestMapping("/api")
public class GiasachResource {

    private final Logger log = LoggerFactory.getLogger(GiasachResource.class);

    private static final String ENTITY_NAME = "giasach";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GiasachService giasachService;

    private final GiasachRepository giasachRepository;

    public GiasachResource(GiasachService giasachService, GiasachRepository giasachRepository) {
        this.giasachService = giasachService;
        this.giasachRepository = giasachRepository;
    }

    /**
     * {@code POST  /giasaches} : Create a new giasach.
     *
     * @param giasach the giasach to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new giasach, or with status {@code 400 (Bad Request)} if the giasach has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/giasaches")
    public ResponseEntity<Giasach> createGiasach(@RequestBody Giasach giasach) throws URISyntaxException {
        log.debug("REST request to save Giasach : {}", giasach);
        if (giasach.getId() != null) {
            throw new BadRequestAlertException("A new giasach cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Giasach result = giasachService.save(giasach);
        return ResponseEntity
            .created(new URI("/api/giasaches/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /giasaches/:id} : Updates an existing giasach.
     *
     * @param id the id of the giasach to save.
     * @param giasach the giasach to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated giasach,
     * or with status {@code 400 (Bad Request)} if the giasach is not valid,
     * or with status {@code 500 (Internal Server Error)} if the giasach couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/giasaches/{id}")
    public ResponseEntity<Giasach> updateGiasach(@PathVariable(value = "id", required = false) final Long id, @RequestBody Giasach giasach)
        throws URISyntaxException {
        log.debug("REST request to update Giasach : {}, {}", id, giasach);
        if (giasach.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, giasach.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!giasachRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Giasach result = giasachService.save(giasach);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, giasach.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /giasaches/:id} : Partial updates given fields of an existing giasach, field will ignore if it is null
     *
     * @param id the id of the giasach to save.
     * @param giasach the giasach to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated giasach,
     * or with status {@code 400 (Bad Request)} if the giasach is not valid,
     * or with status {@code 404 (Not Found)} if the giasach is not found,
     * or with status {@code 500 (Internal Server Error)} if the giasach couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/giasaches/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Giasach> partialUpdateGiasach(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Giasach giasach
    ) throws URISyntaxException {
        log.debug("REST request to partial update Giasach partially : {}, {}", id, giasach);
        if (giasach.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, giasach.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!giasachRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Giasach> result = giasachService.partialUpdate(giasach);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, giasach.getId().toString())
        );
    }

    /**
     * {@code GET  /giasaches} : get all the giasaches.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of giasaches in body.
     */
    @GetMapping("/giasaches")
    public List<Giasach> getAllGiasaches() {
        log.debug("REST request to get all Giasaches");
        return giasachService.findAll();
    }

    /**
     * {@code GET  /giasaches/:id} : get the "id" giasach.
     *
     * @param id the id of the giasach to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the giasach, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/giasaches/{id}")
    public ResponseEntity<Giasach> getGiasach(@PathVariable Long id) {
        log.debug("REST request to get Giasach : {}", id);
        Optional<Giasach> giasach = giasachService.findOne(id);
        return ResponseUtil.wrapOrNotFound(giasach);
    }

    /**
     * {@code DELETE  /giasaches/:id} : delete the "id" giasach.
     *
     * @param id the id of the giasach to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/giasaches/{id}")
    public ResponseEntity<Void> deleteGiasach(@PathVariable Long id) {
        log.debug("REST request to delete Giasach : {}", id);
        giasachService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
