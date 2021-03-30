package com.khmt.myapp.web.rest;

import com.khmt.myapp.domain.Phongdocsach;
import com.khmt.myapp.repository.PhongdocsachRepository;
import com.khmt.myapp.service.PhongdocsachService;
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
 * REST controller for managing {@link com.khmt.myapp.domain.Phongdocsach}.
 */
@RestController
@RequestMapping("/api")
public class PhongdocsachResource {

    private final Logger log = LoggerFactory.getLogger(PhongdocsachResource.class);

    private static final String ENTITY_NAME = "phongdocsach";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PhongdocsachService phongdocsachService;

    private final PhongdocsachRepository phongdocsachRepository;

    public PhongdocsachResource(PhongdocsachService phongdocsachService, PhongdocsachRepository phongdocsachRepository) {
        this.phongdocsachService = phongdocsachService;
        this.phongdocsachRepository = phongdocsachRepository;
    }

    /**
     * {@code POST  /phongdocsaches} : Create a new phongdocsach.
     *
     * @param phongdocsach the phongdocsach to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new phongdocsach, or with status {@code 400 (Bad Request)} if the phongdocsach has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/phongdocsaches")
    public ResponseEntity<Phongdocsach> createPhongdocsach(@RequestBody Phongdocsach phongdocsach) throws URISyntaxException {
        log.debug("REST request to save Phongdocsach : {}", phongdocsach);
        if (phongdocsach.getId() != null) {
            throw new BadRequestAlertException("A new phongdocsach cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Phongdocsach result = phongdocsachService.save(phongdocsach);
        return ResponseEntity
            .created(new URI("/api/phongdocsaches/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /phongdocsaches/:id} : Updates an existing phongdocsach.
     *
     * @param id the id of the phongdocsach to save.
     * @param phongdocsach the phongdocsach to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated phongdocsach,
     * or with status {@code 400 (Bad Request)} if the phongdocsach is not valid,
     * or with status {@code 500 (Internal Server Error)} if the phongdocsach couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/phongdocsaches/{id}")
    public ResponseEntity<Phongdocsach> updatePhongdocsach(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Phongdocsach phongdocsach
    ) throws URISyntaxException {
        log.debug("REST request to update Phongdocsach : {}, {}", id, phongdocsach);
        if (phongdocsach.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, phongdocsach.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!phongdocsachRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Phongdocsach result = phongdocsachService.save(phongdocsach);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, phongdocsach.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /phongdocsaches/:id} : Partial updates given fields of an existing phongdocsach, field will ignore if it is null
     *
     * @param id the id of the phongdocsach to save.
     * @param phongdocsach the phongdocsach to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated phongdocsach,
     * or with status {@code 400 (Bad Request)} if the phongdocsach is not valid,
     * or with status {@code 404 (Not Found)} if the phongdocsach is not found,
     * or with status {@code 500 (Internal Server Error)} if the phongdocsach couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/phongdocsaches/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Phongdocsach> partialUpdatePhongdocsach(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Phongdocsach phongdocsach
    ) throws URISyntaxException {
        log.debug("REST request to partial update Phongdocsach partially : {}, {}", id, phongdocsach);
        if (phongdocsach.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, phongdocsach.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!phongdocsachRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Phongdocsach> result = phongdocsachService.partialUpdate(phongdocsach);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, phongdocsach.getId().toString())
        );
    }

    /**
     * {@code GET  /phongdocsaches} : get all the phongdocsaches.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of phongdocsaches in body.
     */
    @GetMapping("/phongdocsaches")
    public List<Phongdocsach> getAllPhongdocsaches() {
        log.debug("REST request to get all Phongdocsaches");
        return phongdocsachService.findAll();
    }

    /**
     * {@code GET  /phongdocsaches/:id} : get the "id" phongdocsach.
     *
     * @param id the id of the phongdocsach to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the phongdocsach, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/phongdocsaches/{id}")
    public ResponseEntity<Phongdocsach> getPhongdocsach(@PathVariable Long id) {
        log.debug("REST request to get Phongdocsach : {}", id);
        Optional<Phongdocsach> phongdocsach = phongdocsachService.findOne(id);
        return ResponseUtil.wrapOrNotFound(phongdocsach);
    }

    /**
     * {@code DELETE  /phongdocsaches/:id} : delete the "id" phongdocsach.
     *
     * @param id the id of the phongdocsach to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/phongdocsaches/{id}")
    public ResponseEntity<Void> deletePhongdocsach(@PathVariable Long id) {
        log.debug("REST request to delete Phongdocsach : {}", id);
        phongdocsachService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
