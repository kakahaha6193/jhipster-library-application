package com.khmt.myapp.web.rest;

import com.khmt.myapp.domain.Thuephong;
import com.khmt.myapp.repository.ThuephongRepository;
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
 * REST controller for managing {@link com.khmt.myapp.domain.Thuephong}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ThuephongResource {

    private final Logger log = LoggerFactory.getLogger(ThuephongResource.class);

    private static final String ENTITY_NAME = "thuephong";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ThuephongRepository thuephongRepository;

    public ThuephongResource(ThuephongRepository thuephongRepository) {
        this.thuephongRepository = thuephongRepository;
    }

    /**
     * {@code POST  /thuephongs} : Create a new thuephong.
     *
     * @param thuephong the thuephong to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new thuephong, or with status {@code 400 (Bad Request)} if the thuephong has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/thuephongs")
    public ResponseEntity<Thuephong> createThuephong(@RequestBody Thuephong thuephong) throws URISyntaxException {
        log.debug("REST request to save Thuephong : {}", thuephong);
        if (thuephong.getId() != null) {
            throw new BadRequestAlertException("A new thuephong cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Thuephong result = thuephongRepository.save(thuephong);
        return ResponseEntity
            .created(new URI("/api/thuephongs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /thuephongs/:id} : Updates an existing thuephong.
     *
     * @param id the id of the thuephong to save.
     * @param thuephong the thuephong to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated thuephong,
     * or with status {@code 400 (Bad Request)} if the thuephong is not valid,
     * or with status {@code 500 (Internal Server Error)} if the thuephong couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/thuephongs/{id}")
    public ResponseEntity<Thuephong> updateThuephong(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Thuephong thuephong
    ) throws URISyntaxException {
        log.debug("REST request to update Thuephong : {}, {}", id, thuephong);
        if (thuephong.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, thuephong.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!thuephongRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Thuephong result = thuephongRepository.save(thuephong);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, thuephong.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /thuephongs/:id} : Partial updates given fields of an existing thuephong, field will ignore if it is null
     *
     * @param id the id of the thuephong to save.
     * @param thuephong the thuephong to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated thuephong,
     * or with status {@code 400 (Bad Request)} if the thuephong is not valid,
     * or with status {@code 404 (Not Found)} if the thuephong is not found,
     * or with status {@code 500 (Internal Server Error)} if the thuephong couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/thuephongs/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Thuephong> partialUpdateThuephong(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Thuephong thuephong
    ) throws URISyntaxException {
        log.debug("REST request to partial update Thuephong partially : {}, {}", id, thuephong);
        if (thuephong.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, thuephong.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!thuephongRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Thuephong> result = thuephongRepository
            .findById(thuephong.getId())
            .map(
                existingThuephong -> {
                    if (thuephong.getNgayThue() != null) {
                        existingThuephong.setNgayThue(thuephong.getNgayThue());
                    }
                    if (thuephong.getCa() != null) {
                        existingThuephong.setCa(thuephong.getCa());
                    }

                    return existingThuephong;
                }
            )
            .map(thuephongRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, thuephong.getId().toString())
        );
    }

    /**
     * {@code GET  /thuephongs} : get all the thuephongs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of thuephongs in body.
     */
    @GetMapping("/thuephongs")
    public ResponseEntity<List<Thuephong>> getAllThuephongs(Pageable pageable) {
        log.debug("REST request to get a page of Thuephongs");
        Page<Thuephong> page = thuephongRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /thuephongs/:id} : get the "id" thuephong.
     *
     * @param id the id of the thuephong to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the thuephong, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/thuephongs/{id}")
    public ResponseEntity<Thuephong> getThuephong(@PathVariable Long id) {
        log.debug("REST request to get Thuephong : {}", id);
        Optional<Thuephong> thuephong = thuephongRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(thuephong);
    }

    /**
     * {@code DELETE  /thuephongs/:id} : delete the "id" thuephong.
     *
     * @param id the id of the thuephong to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/thuephongs/{id}")
    public ResponseEntity<Void> deleteThuephong(@PathVariable Long id) {
        log.debug("REST request to delete Thuephong : {}", id);
        thuephongRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
