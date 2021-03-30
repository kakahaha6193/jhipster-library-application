package com.khmt.myapp.web.rest;

import com.khmt.myapp.domain.Phongdungsach;
import com.khmt.myapp.repository.PhongdungsachRepository;
import com.khmt.myapp.service.PhongdungsachService;
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
 * REST controller for managing {@link com.khmt.myapp.domain.Phongdungsach}.
 */
@RestController
@RequestMapping("/api")
public class PhongdungsachResource {

    private final Logger log = LoggerFactory.getLogger(PhongdungsachResource.class);

    private static final String ENTITY_NAME = "phongdungsach";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PhongdungsachService phongdungsachService;

    private final PhongdungsachRepository phongdungsachRepository;

    public PhongdungsachResource(PhongdungsachService phongdungsachService, PhongdungsachRepository phongdungsachRepository) {
        this.phongdungsachService = phongdungsachService;
        this.phongdungsachRepository = phongdungsachRepository;
    }

    /**
     * {@code POST  /phongdungsaches} : Create a new phongdungsach.
     *
     * @param phongdungsach the phongdungsach to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new phongdungsach, or with status {@code 400 (Bad Request)} if the phongdungsach has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/phongdungsaches")
    public ResponseEntity<Phongdungsach> createPhongdungsach(@RequestBody Phongdungsach phongdungsach) throws URISyntaxException {
        log.debug("REST request to save Phongdungsach : {}", phongdungsach);
        if (phongdungsach.getId() != null) {
            throw new BadRequestAlertException("A new phongdungsach cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Phongdungsach result = phongdungsachService.save(phongdungsach);
        return ResponseEntity
            .created(new URI("/api/phongdungsaches/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /phongdungsaches/:id} : Updates an existing phongdungsach.
     *
     * @param id the id of the phongdungsach to save.
     * @param phongdungsach the phongdungsach to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated phongdungsach,
     * or with status {@code 400 (Bad Request)} if the phongdungsach is not valid,
     * or with status {@code 500 (Internal Server Error)} if the phongdungsach couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/phongdungsaches/{id}")
    public ResponseEntity<Phongdungsach> updatePhongdungsach(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Phongdungsach phongdungsach
    ) throws URISyntaxException {
        log.debug("REST request to update Phongdungsach : {}, {}", id, phongdungsach);
        if (phongdungsach.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, phongdungsach.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!phongdungsachRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Phongdungsach result = phongdungsachService.save(phongdungsach);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, phongdungsach.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /phongdungsaches/:id} : Partial updates given fields of an existing phongdungsach, field will ignore if it is null
     *
     * @param id the id of the phongdungsach to save.
     * @param phongdungsach the phongdungsach to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated phongdungsach,
     * or with status {@code 400 (Bad Request)} if the phongdungsach is not valid,
     * or with status {@code 404 (Not Found)} if the phongdungsach is not found,
     * or with status {@code 500 (Internal Server Error)} if the phongdungsach couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/phongdungsaches/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Phongdungsach> partialUpdatePhongdungsach(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Phongdungsach phongdungsach
    ) throws URISyntaxException {
        log.debug("REST request to partial update Phongdungsach partially : {}, {}", id, phongdungsach);
        if (phongdungsach.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, phongdungsach.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!phongdungsachRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Phongdungsach> result = phongdungsachService.partialUpdate(phongdungsach);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, phongdungsach.getId().toString())
        );
    }

    /**
     * {@code GET  /phongdungsaches} : get all the phongdungsaches.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of phongdungsaches in body.
     */
    @GetMapping("/phongdungsaches")
    public List<Phongdungsach> getAllPhongdungsaches() {
        log.debug("REST request to get all Phongdungsaches");
        return phongdungsachService.findAll();
    }

    /**
     * {@code GET  /phongdungsaches/:id} : get the "id" phongdungsach.
     *
     * @param id the id of the phongdungsach to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the phongdungsach, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/phongdungsaches/{id}")
    public ResponseEntity<Phongdungsach> getPhongdungsach(@PathVariable Long id) {
        log.debug("REST request to get Phongdungsach : {}", id);
        Optional<Phongdungsach> phongdungsach = phongdungsachService.findOne(id);
        return ResponseUtil.wrapOrNotFound(phongdungsach);
    }

    /**
     * {@code DELETE  /phongdungsaches/:id} : delete the "id" phongdungsach.
     *
     * @param id the id of the phongdungsach to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/phongdungsaches/{id}")
    public ResponseEntity<Void> deletePhongdungsach(@PathVariable Long id) {
        log.debug("REST request to delete Phongdungsach : {}", id);
        phongdungsachService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
