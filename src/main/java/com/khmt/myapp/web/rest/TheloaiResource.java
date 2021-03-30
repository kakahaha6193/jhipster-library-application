package com.khmt.myapp.web.rest;

import com.khmt.myapp.domain.Theloai;
import com.khmt.myapp.repository.TheloaiRepository;
import com.khmt.myapp.service.TheloaiService;
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
 * REST controller for managing {@link com.khmt.myapp.domain.Theloai}.
 */
@RestController
@RequestMapping("/api")
public class TheloaiResource {

    private final Logger log = LoggerFactory.getLogger(TheloaiResource.class);

    private static final String ENTITY_NAME = "theloai";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TheloaiService theloaiService;

    private final TheloaiRepository theloaiRepository;

    public TheloaiResource(TheloaiService theloaiService, TheloaiRepository theloaiRepository) {
        this.theloaiService = theloaiService;
        this.theloaiRepository = theloaiRepository;
    }

    /**
     * {@code POST  /theloais} : Create a new theloai.
     *
     * @param theloai the theloai to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new theloai, or with status {@code 400 (Bad Request)} if the theloai has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/theloais")
    public ResponseEntity<Theloai> createTheloai(@RequestBody Theloai theloai) throws URISyntaxException {
        log.debug("REST request to save Theloai : {}", theloai);
        if (theloai.getId() != null) {
            throw new BadRequestAlertException("A new theloai cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Theloai result = theloaiService.save(theloai);
        return ResponseEntity
            .created(new URI("/api/theloais/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /theloais/:id} : Updates an existing theloai.
     *
     * @param id the id of the theloai to save.
     * @param theloai the theloai to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated theloai,
     * or with status {@code 400 (Bad Request)} if the theloai is not valid,
     * or with status {@code 500 (Internal Server Error)} if the theloai couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/theloais/{id}")
    public ResponseEntity<Theloai> updateTheloai(@PathVariable(value = "id", required = false) final Long id, @RequestBody Theloai theloai)
        throws URISyntaxException {
        log.debug("REST request to update Theloai : {}, {}", id, theloai);
        if (theloai.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, theloai.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!theloaiRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Theloai result = theloaiService.save(theloai);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, theloai.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /theloais/:id} : Partial updates given fields of an existing theloai, field will ignore if it is null
     *
     * @param id the id of the theloai to save.
     * @param theloai the theloai to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated theloai,
     * or with status {@code 400 (Bad Request)} if the theloai is not valid,
     * or with status {@code 404 (Not Found)} if the theloai is not found,
     * or with status {@code 500 (Internal Server Error)} if the theloai couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/theloais/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Theloai> partialUpdateTheloai(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Theloai theloai
    ) throws URISyntaxException {
        log.debug("REST request to partial update Theloai partially : {}, {}", id, theloai);
        if (theloai.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, theloai.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!theloaiRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Theloai> result = theloaiService.partialUpdate(theloai);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, theloai.getId().toString())
        );
    }

    /**
     * {@code GET  /theloais} : get all the theloais.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of theloais in body.
     */
    @GetMapping("/theloais")
    public List<Theloai> getAllTheloais() {
        log.debug("REST request to get all Theloais");
        return theloaiService.findAll();
    }

    /**
     * {@code GET  /theloais/:id} : get the "id" theloai.
     *
     * @param id the id of the theloai to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the theloai, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/theloais/{id}")
    public ResponseEntity<Theloai> getTheloai(@PathVariable Long id) {
        log.debug("REST request to get Theloai : {}", id);
        Optional<Theloai> theloai = theloaiService.findOne(id);
        return ResponseUtil.wrapOrNotFound(theloai);
    }

    /**
     * {@code DELETE  /theloais/:id} : delete the "id" theloai.
     *
     * @param id the id of the theloai to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/theloais/{id}")
    public ResponseEntity<Void> deleteTheloai(@PathVariable Long id) {
        log.debug("REST request to delete Theloai : {}", id);
        theloaiService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
