package com.khmt.myapp.web.rest;

import com.khmt.myapp.domain.Nhapsach;
import com.khmt.myapp.repository.NhapsachRepository;
import com.khmt.myapp.service.NhapsachService;
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
 * REST controller for managing {@link com.khmt.myapp.domain.Nhapsach}.
 */
@RestController
@RequestMapping("/api")
public class NhapsachResource {

    private final Logger log = LoggerFactory.getLogger(NhapsachResource.class);

    private static final String ENTITY_NAME = "nhapsach";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NhapsachService nhapsachService;

    private final NhapsachRepository nhapsachRepository;

    public NhapsachResource(NhapsachService nhapsachService, NhapsachRepository nhapsachRepository) {
        this.nhapsachService = nhapsachService;
        this.nhapsachRepository = nhapsachRepository;
    }

    /**
     * {@code POST  /nhapsaches} : Create a new nhapsach.
     *
     * @param nhapsach the nhapsach to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new nhapsach, or with status {@code 400 (Bad Request)} if the nhapsach has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/nhapsaches")
    public ResponseEntity<Nhapsach> createNhapsach(@RequestBody Nhapsach nhapsach) throws URISyntaxException {
        log.debug("REST request to save Nhapsach : {}", nhapsach);
        if (nhapsach.getId() != null) {
            throw new BadRequestAlertException("A new nhapsach cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Nhapsach result = nhapsachService.save(nhapsach);
        return ResponseEntity
            .created(new URI("/api/nhapsaches/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /nhapsaches/:id} : Updates an existing nhapsach.
     *
     * @param id the id of the nhapsach to save.
     * @param nhapsach the nhapsach to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nhapsach,
     * or with status {@code 400 (Bad Request)} if the nhapsach is not valid,
     * or with status {@code 500 (Internal Server Error)} if the nhapsach couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/nhapsaches/{id}")
    public ResponseEntity<Nhapsach> updateNhapsach(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Nhapsach nhapsach
    ) throws URISyntaxException {
        log.debug("REST request to update Nhapsach : {}, {}", id, nhapsach);
        if (nhapsach.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nhapsach.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nhapsachRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Nhapsach result = nhapsachService.save(nhapsach);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nhapsach.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /nhapsaches/:id} : Partial updates given fields of an existing nhapsach, field will ignore if it is null
     *
     * @param id the id of the nhapsach to save.
     * @param nhapsach the nhapsach to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nhapsach,
     * or with status {@code 400 (Bad Request)} if the nhapsach is not valid,
     * or with status {@code 404 (Not Found)} if the nhapsach is not found,
     * or with status {@code 500 (Internal Server Error)} if the nhapsach couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/nhapsaches/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Nhapsach> partialUpdateNhapsach(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Nhapsach nhapsach
    ) throws URISyntaxException {
        log.debug("REST request to partial update Nhapsach partially : {}, {}", id, nhapsach);
        if (nhapsach.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nhapsach.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nhapsachRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Nhapsach> result = nhapsachService.partialUpdate(nhapsach);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nhapsach.getId().toString())
        );
    }

    /**
     * {@code GET  /nhapsaches} : get all the nhapsaches.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of nhapsaches in body.
     */
    @GetMapping("/nhapsaches")
    public List<Nhapsach> getAllNhapsaches() {
        log.debug("REST request to get all Nhapsaches");
        return nhapsachService.findAll();
    }

    /**
     * {@code GET  /nhapsaches/:id} : get the "id" nhapsach.
     *
     * @param id the id of the nhapsach to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the nhapsach, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/nhapsaches/{id}")
    public ResponseEntity<Nhapsach> getNhapsach(@PathVariable Long id) {
        log.debug("REST request to get Nhapsach : {}", id);
        Optional<Nhapsach> nhapsach = nhapsachService.findOne(id);
        return ResponseUtil.wrapOrNotFound(nhapsach);
    }

    /**
     * {@code DELETE  /nhapsaches/:id} : delete the "id" nhapsach.
     *
     * @param id the id of the nhapsach to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/nhapsaches/{id}")
    public ResponseEntity<Void> deleteNhapsach(@PathVariable Long id) {
        log.debug("REST request to delete Nhapsach : {}", id);
        nhapsachService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
