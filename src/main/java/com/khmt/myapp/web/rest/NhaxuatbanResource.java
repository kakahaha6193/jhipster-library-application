package com.khmt.myapp.web.rest;

import com.khmt.myapp.domain.Nhaxuatban;
import com.khmt.myapp.repository.NhaxuatbanRepository;
import com.khmt.myapp.service.NhaxuatbanService;
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
 * REST controller for managing {@link com.khmt.myapp.domain.Nhaxuatban}.
 */
@RestController
@RequestMapping("/api")
public class NhaxuatbanResource {

    private final Logger log = LoggerFactory.getLogger(NhaxuatbanResource.class);

    private static final String ENTITY_NAME = "nhaxuatban";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NhaxuatbanService nhaxuatbanService;

    private final NhaxuatbanRepository nhaxuatbanRepository;

    public NhaxuatbanResource(NhaxuatbanService nhaxuatbanService, NhaxuatbanRepository nhaxuatbanRepository) {
        this.nhaxuatbanService = nhaxuatbanService;
        this.nhaxuatbanRepository = nhaxuatbanRepository;
    }

    /**
     * {@code POST  /nhaxuatbans} : Create a new nhaxuatban.
     *
     * @param nhaxuatban the nhaxuatban to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new nhaxuatban, or with status {@code 400 (Bad Request)} if the nhaxuatban has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/nhaxuatbans")
    public ResponseEntity<Nhaxuatban> createNhaxuatban(@RequestBody Nhaxuatban nhaxuatban) throws URISyntaxException {
        log.debug("REST request to save Nhaxuatban : {}", nhaxuatban);
        if (nhaxuatban.getId() != null) {
            throw new BadRequestAlertException("A new nhaxuatban cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Nhaxuatban result = nhaxuatbanService.save(nhaxuatban);
        return ResponseEntity
            .created(new URI("/api/nhaxuatbans/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /nhaxuatbans/:id} : Updates an existing nhaxuatban.
     *
     * @param id the id of the nhaxuatban to save.
     * @param nhaxuatban the nhaxuatban to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nhaxuatban,
     * or with status {@code 400 (Bad Request)} if the nhaxuatban is not valid,
     * or with status {@code 500 (Internal Server Error)} if the nhaxuatban couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/nhaxuatbans/{id}")
    public ResponseEntity<Nhaxuatban> updateNhaxuatban(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Nhaxuatban nhaxuatban
    ) throws URISyntaxException {
        log.debug("REST request to update Nhaxuatban : {}, {}", id, nhaxuatban);
        if (nhaxuatban.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nhaxuatban.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nhaxuatbanRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Nhaxuatban result = nhaxuatbanService.save(nhaxuatban);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nhaxuatban.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /nhaxuatbans/:id} : Partial updates given fields of an existing nhaxuatban, field will ignore if it is null
     *
     * @param id the id of the nhaxuatban to save.
     * @param nhaxuatban the nhaxuatban to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nhaxuatban,
     * or with status {@code 400 (Bad Request)} if the nhaxuatban is not valid,
     * or with status {@code 404 (Not Found)} if the nhaxuatban is not found,
     * or with status {@code 500 (Internal Server Error)} if the nhaxuatban couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/nhaxuatbans/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Nhaxuatban> partialUpdateNhaxuatban(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Nhaxuatban nhaxuatban
    ) throws URISyntaxException {
        log.debug("REST request to partial update Nhaxuatban partially : {}, {}", id, nhaxuatban);
        if (nhaxuatban.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nhaxuatban.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nhaxuatbanRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Nhaxuatban> result = nhaxuatbanService.partialUpdate(nhaxuatban);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nhaxuatban.getId().toString())
        );
    }

    /**
     * {@code GET  /nhaxuatbans} : get all the nhaxuatbans.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of nhaxuatbans in body.
     */
    @GetMapping("/nhaxuatbans")
    public List<Nhaxuatban> getAllNhaxuatbans() {
        log.debug("REST request to get all Nhaxuatbans");
        return nhaxuatbanService.findAll();
    }

    /**
     * {@code GET  /nhaxuatbans/:id} : get the "id" nhaxuatban.
     *
     * @param id the id of the nhaxuatban to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the nhaxuatban, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/nhaxuatbans/{id}")
    public ResponseEntity<Nhaxuatban> getNhaxuatban(@PathVariable Long id) {
        log.debug("REST request to get Nhaxuatban : {}", id);
        Optional<Nhaxuatban> nhaxuatban = nhaxuatbanService.findOne(id);
        return ResponseUtil.wrapOrNotFound(nhaxuatban);
    }

    /**
     * {@code DELETE  /nhaxuatbans/:id} : delete the "id" nhaxuatban.
     *
     * @param id the id of the nhaxuatban to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/nhaxuatbans/{id}")
    public ResponseEntity<Void> deleteNhaxuatban(@PathVariable Long id) {
        log.debug("REST request to delete Nhaxuatban : {}", id);
        nhaxuatbanService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
