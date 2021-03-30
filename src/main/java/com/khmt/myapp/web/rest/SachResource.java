package com.khmt.myapp.web.rest;

import com.khmt.myapp.domain.Sach;
import com.khmt.myapp.repository.SachRepository;
import com.khmt.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.khmt.myapp.domain.Sach}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SachResource {

    private final Logger log = LoggerFactory.getLogger(SachResource.class);

    private static final String ENTITY_NAME = "sach";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SachRepository sachRepository;

    public SachResource(SachRepository sachRepository) {
        this.sachRepository = sachRepository;
    }

    /**
     * {@code POST  /saches} : Create a new sach.
     *
     * @param sach the sach to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sach, or with status {@code 400 (Bad Request)} if the sach has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/saches")
    public ResponseEntity<Sach> createSach(@RequestBody Sach sach) throws URISyntaxException {
        log.debug("REST request to save Sach : {}", sach);
        if (sach.getId() != null) {
            throw new BadRequestAlertException("A new sach cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Sach result = sachRepository.save(sach);
        return ResponseEntity
            .created(new URI("/api/saches/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /saches/:id} : Updates an existing sach.
     *
     * @param id the id of the sach to save.
     * @param sach the sach to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sach,
     * or with status {@code 400 (Bad Request)} if the sach is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sach couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/saches/{id}")
    public ResponseEntity<Sach> updateSach(@PathVariable(value = "id", required = false) final Long id, @RequestBody Sach sach)
        throws URISyntaxException {
        log.debug("REST request to update Sach : {}, {}", id, sach);
        if (sach.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sach.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sachRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Sach result = sachRepository.save(sach);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sach.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /saches/:id} : Partial updates given fields of an existing sach, field will ignore if it is null
     *
     * @param id the id of the sach to save.
     * @param sach the sach to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sach,
     * or with status {@code 400 (Bad Request)} if the sach is not valid,
     * or with status {@code 404 (Not Found)} if the sach is not found,
     * or with status {@code 500 (Internal Server Error)} if the sach couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/saches/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Sach> partialUpdateSach(@PathVariable(value = "id", required = false) final Long id, @RequestBody Sach sach)
        throws URISyntaxException {
        log.debug("REST request to partial update Sach partially : {}, {}", id, sach);
        if (sach.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sach.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sachRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Sach> result = sachRepository
            .findById(sach.getId())
            .map(
                existingSach -> {
                    if (sach.getTenSach() != null) {
                        existingSach.setTenSach(sach.getTenSach());
                    }
                    if (sach.getGiaNiemYet() != null) {
                        existingSach.setGiaNiemYet(sach.getGiaNiemYet());
                    }
                    if (sach.getTacgia() != null) {
                        existingSach.setTacgia(sach.getTacgia());
                    }
                    if (sach.getGiaThue() != null) {
                        existingSach.setGiaThue(sach.getGiaThue());
                    }
                    if (sach.getNganXep() != null) {
                        existingSach.setNganXep(sach.getNganXep());
                    }

                    return existingSach;
                }
            )
            .map(sachRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sach.getId().toString())
        );
    }

    /**
     * {@code GET  /saches} : get all the saches.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of saches in body.
     */
    @GetMapping("/saches")
    public ResponseEntity<List<Sach>> getAllSaches(
        Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of Saches");
        Page<Sach> page;
        if (eagerload) {
            page = sachRepository.findAllWithEagerRelationships(pageable);
        } else {
            page = sachRepository.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /saches/:id} : get the "id" sach.
     *
     * @param id the id of the sach to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sach, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/saches/{id}")
    public ResponseEntity<Sach> getSach(@PathVariable Long id) {
        log.debug("REST request to get Sach : {}", id);
        Optional<Sach> sach = sachRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(sach);
    }

    /**
     * {@code DELETE  /saches/:id} : delete the "id" sach.
     *
     * @param id the id of the sach to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/saches/{id}")
    public ResponseEntity<Void> deleteSach(@PathVariable Long id) {
        log.debug("REST request to delete Sach : {}", id);
        sachRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
