package com.khmt.myapp.web.rest;

import com.khmt.myapp.domain.Muonsach;
import com.khmt.myapp.repository.MuonsachRepository;
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
 * REST controller for managing {@link com.khmt.myapp.domain.Muonsach}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class MuonsachResource {

    private final Logger log = LoggerFactory.getLogger(MuonsachResource.class);

    private static final String ENTITY_NAME = "muonsach";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MuonsachRepository muonsachRepository;

    public MuonsachResource(MuonsachRepository muonsachRepository) {
        this.muonsachRepository = muonsachRepository;
    }

    /**
     * {@code POST  /muonsaches} : Create a new muonsach.
     *
     * @param muonsach the muonsach to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new muonsach, or with status {@code 400 (Bad Request)} if the muonsach has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/muonsaches")
    public ResponseEntity<Muonsach> createMuonsach(@RequestBody Muonsach muonsach) throws URISyntaxException {
        log.debug("REST request to save Muonsach : {}", muonsach);
        if (muonsach.getId() != null) {
            throw new BadRequestAlertException("A new muonsach cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Muonsach result = muonsachRepository.save(muonsach);
        return ResponseEntity
            .created(new URI("/api/muonsaches/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /muonsaches/:id} : Updates an existing muonsach.
     *
     * @param id the id of the muonsach to save.
     * @param muonsach the muonsach to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated muonsach,
     * or with status {@code 400 (Bad Request)} if the muonsach is not valid,
     * or with status {@code 500 (Internal Server Error)} if the muonsach couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/muonsaches/{id}")
    public ResponseEntity<Muonsach> updateMuonsach(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Muonsach muonsach
    ) throws URISyntaxException {
        log.debug("REST request to update Muonsach : {}, {}", id, muonsach);
        if (muonsach.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, muonsach.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!muonsachRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Muonsach result = muonsachRepository.save(muonsach);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, muonsach.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /muonsaches/:id} : Partial updates given fields of an existing muonsach, field will ignore if it is null
     *
     * @param id the id of the muonsach to save.
     * @param muonsach the muonsach to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated muonsach,
     * or with status {@code 400 (Bad Request)} if the muonsach is not valid,
     * or with status {@code 404 (Not Found)} if the muonsach is not found,
     * or with status {@code 500 (Internal Server Error)} if the muonsach couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/muonsaches/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Muonsach> partialUpdateMuonsach(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Muonsach muonsach
    ) throws URISyntaxException {
        log.debug("REST request to partial update Muonsach partially : {}, {}", id, muonsach);
        if (muonsach.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, muonsach.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!muonsachRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Muonsach> result = muonsachRepository
            .findById(muonsach.getId())
            .map(
                existingMuonsach -> {
                    if (muonsach.getNgayMuon() != null) {
                        existingMuonsach.setNgayMuon(muonsach.getNgayMuon());
                    }
                    if (muonsach.getHanTra() != null) {
                        existingMuonsach.setHanTra(muonsach.getHanTra());
                    }
                    if (muonsach.getNgayTra() != null) {
                        existingMuonsach.setNgayTra(muonsach.getNgayTra());
                    }
                    if (muonsach.getTrangThai() != null) {
                        existingMuonsach.setTrangThai(muonsach.getTrangThai());
                    }

                    return existingMuonsach;
                }
            )
            .map(muonsachRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, muonsach.getId().toString())
        );
    }

    /**
     * {@code GET  /muonsaches} : get all the muonsaches.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of muonsaches in body.
     */
    @GetMapping("/muonsaches")
    public ResponseEntity<List<Muonsach>> getAllMuonsaches(Pageable pageable) {
        log.debug("REST request to get a page of Muonsaches");
        Page<Muonsach> page = muonsachRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /muonsaches/:id} : get the "id" muonsach.
     *
     * @param id the id of the muonsach to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the muonsach, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/muonsaches/{id}")
    public ResponseEntity<Muonsach> getMuonsach(@PathVariable Long id) {
        log.debug("REST request to get Muonsach : {}", id);
        Optional<Muonsach> muonsach = muonsachRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(muonsach);
    }

    /**
     * {@code DELETE  /muonsaches/:id} : delete the "id" muonsach.
     *
     * @param id the id of the muonsach to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/muonsaches/{id}")
    public ResponseEntity<Void> deleteMuonsach(@PathVariable Long id) {
        log.debug("REST request to delete Muonsach : {}", id);
        muonsachRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
