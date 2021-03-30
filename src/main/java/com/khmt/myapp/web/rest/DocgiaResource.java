package com.khmt.myapp.web.rest;

import com.khmt.myapp.domain.Docgia;
import com.khmt.myapp.repository.DocgiaRepository;
import com.khmt.myapp.service.DocgiaService;
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
 * REST controller for managing {@link com.khmt.myapp.domain.Docgia}.
 */
@RestController
@RequestMapping("/api")
public class DocgiaResource {

    private final Logger log = LoggerFactory.getLogger(DocgiaResource.class);

    private static final String ENTITY_NAME = "docgia";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DocgiaService docgiaService;

    private final DocgiaRepository docgiaRepository;

    public DocgiaResource(DocgiaService docgiaService, DocgiaRepository docgiaRepository) {
        this.docgiaService = docgiaService;
        this.docgiaRepository = docgiaRepository;
    }

    /**
     * {@code POST  /docgias} : Create a new docgia.
     *
     * @param docgia the docgia to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new docgia, or with status {@code 400 (Bad Request)} if the docgia has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/docgias")
    public ResponseEntity<Docgia> createDocgia(@RequestBody Docgia docgia) throws URISyntaxException {
        log.debug("REST request to save Docgia : {}", docgia);
        if (docgia.getId() != null) {
            throw new BadRequestAlertException("A new docgia cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Docgia result = docgiaService.save(docgia);
        return ResponseEntity
            .created(new URI("/api/docgias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /docgias/:id} : Updates an existing docgia.
     *
     * @param id the id of the docgia to save.
     * @param docgia the docgia to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated docgia,
     * or with status {@code 400 (Bad Request)} if the docgia is not valid,
     * or with status {@code 500 (Internal Server Error)} if the docgia couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/docgias/{id}")
    public ResponseEntity<Docgia> updateDocgia(@PathVariable(value = "id", required = false) final Long id, @RequestBody Docgia docgia)
        throws URISyntaxException {
        log.debug("REST request to update Docgia : {}, {}", id, docgia);
        if (docgia.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, docgia.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!docgiaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Docgia result = docgiaService.save(docgia);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, docgia.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /docgias/:id} : Partial updates given fields of an existing docgia, field will ignore if it is null
     *
     * @param id the id of the docgia to save.
     * @param docgia the docgia to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated docgia,
     * or with status {@code 400 (Bad Request)} if the docgia is not valid,
     * or with status {@code 404 (Not Found)} if the docgia is not found,
     * or with status {@code 500 (Internal Server Error)} if the docgia couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/docgias/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Docgia> partialUpdateDocgia(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Docgia docgia
    ) throws URISyntaxException {
        log.debug("REST request to partial update Docgia partially : {}, {}", id, docgia);
        if (docgia.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, docgia.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!docgiaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Docgia> result = docgiaService.partialUpdate(docgia);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, docgia.getId().toString())
        );
    }

    /**
     * {@code GET  /docgias} : get all the docgias.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of docgias in body.
     */
    @GetMapping("/docgias")
    public List<Docgia> getAllDocgias() {
        log.debug("REST request to get all Docgias");
        return docgiaService.findAll();
    }

    /**
     * {@code GET  /docgias/:id} : get the "id" docgia.
     *
     * @param id the id of the docgia to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the docgia, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/docgias/{id}")
    public ResponseEntity<Docgia> getDocgia(@PathVariable Long id) {
        log.debug("REST request to get Docgia : {}", id);
        Optional<Docgia> docgia = docgiaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(docgia);
    }

    /**
     * {@code DELETE  /docgias/:id} : delete the "id" docgia.
     *
     * @param id the id of the docgia to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/docgias/{id}")
    public ResponseEntity<Void> deleteDocgia(@PathVariable Long id) {
        log.debug("REST request to delete Docgia : {}", id);
        docgiaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
