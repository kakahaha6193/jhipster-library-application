package com.khmt.myapp.web.rest;

import com.khmt.myapp.domain.Cuonsach;
import com.khmt.myapp.repository.CuonsachRepository;
import com.khmt.myapp.service.CuonsachService;
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
 * REST controller for managing {@link com.khmt.myapp.domain.Cuonsach}.
 */
@RestController
@RequestMapping("/api")
public class CuonsachResource {

    private final Logger log = LoggerFactory.getLogger(CuonsachResource.class);

    private static final String ENTITY_NAME = "cuonsach";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CuonsachService cuonsachService;

    private final CuonsachRepository cuonsachRepository;

    public CuonsachResource(CuonsachService cuonsachService, CuonsachRepository cuonsachRepository) {
        this.cuonsachService = cuonsachService;
        this.cuonsachRepository = cuonsachRepository;
    }

    /**
     * {@code POST  /cuonsaches} : Create a new cuonsach.
     *
     * @param cuonsach the cuonsach to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cuonsach, or with status {@code 400 (Bad Request)} if the cuonsach has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cuonsaches")
    public ResponseEntity<Cuonsach> createCuonsach(@RequestBody Cuonsach cuonsach) throws URISyntaxException {
        log.debug("REST request to save Cuonsach : {}", cuonsach);
        if (cuonsach.getId() != null) {
            throw new BadRequestAlertException("A new cuonsach cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Cuonsach result = cuonsachService.save(cuonsach);
        return ResponseEntity
            .created(new URI("/api/cuonsaches/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cuonsaches/:id} : Updates an existing cuonsach.
     *
     * @param id the id of the cuonsach to save.
     * @param cuonsach the cuonsach to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cuonsach,
     * or with status {@code 400 (Bad Request)} if the cuonsach is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cuonsach couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cuonsaches/{id}")
    public ResponseEntity<Cuonsach> updateCuonsach(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Cuonsach cuonsach
    ) throws URISyntaxException {
        log.debug("REST request to update Cuonsach : {}, {}", id, cuonsach);
        if (cuonsach.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cuonsach.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cuonsachRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Cuonsach result = cuonsachService.save(cuonsach);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cuonsach.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /cuonsaches/:id} : Partial updates given fields of an existing cuonsach, field will ignore if it is null
     *
     * @param id the id of the cuonsach to save.
     * @param cuonsach the cuonsach to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cuonsach,
     * or with status {@code 400 (Bad Request)} if the cuonsach is not valid,
     * or with status {@code 404 (Not Found)} if the cuonsach is not found,
     * or with status {@code 500 (Internal Server Error)} if the cuonsach couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/cuonsaches/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Cuonsach> partialUpdateCuonsach(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Cuonsach cuonsach
    ) throws URISyntaxException {
        log.debug("REST request to partial update Cuonsach partially : {}, {}", id, cuonsach);
        if (cuonsach.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cuonsach.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cuonsachRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Cuonsach> result = cuonsachService.partialUpdate(cuonsach);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cuonsach.getId().toString())
        );
    }

    /**
     * {@code GET  /cuonsaches} : get all the cuonsaches.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cuonsaches in body.
     */
    @GetMapping("/cuonsaches")
    public List<Cuonsach> getAllCuonsaches() {
        log.debug("REST request to get all Cuonsaches");
        return cuonsachService.findAll();
    }

    /**
     * {@code GET  /cuonsaches/:id} : get the "id" cuonsach.
     *
     * @param id the id of the cuonsach to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cuonsach, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cuonsaches/{id}")
    public ResponseEntity<Cuonsach> getCuonsach(@PathVariable Long id) {
        log.debug("REST request to get Cuonsach : {}", id);
        Optional<Cuonsach> cuonsach = cuonsachService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cuonsach);
    }

    /**
     * {@code DELETE  /cuonsaches/:id} : delete the "id" cuonsach.
     *
     * @param id the id of the cuonsach to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cuonsaches/{id}")
    public ResponseEntity<Void> deleteCuonsach(@PathVariable Long id) {
        log.debug("REST request to delete Cuonsach : {}", id);
        cuonsachService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
