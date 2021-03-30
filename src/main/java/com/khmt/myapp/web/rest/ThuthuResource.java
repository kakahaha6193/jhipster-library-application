package com.khmt.myapp.web.rest;

import com.khmt.myapp.domain.Thuthu;
import com.khmt.myapp.repository.ThuthuRepository;
import com.khmt.myapp.service.ThuthuService;
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
 * REST controller for managing {@link com.khmt.myapp.domain.Thuthu}.
 */
@RestController
@RequestMapping("/api")
public class ThuthuResource {

    private final Logger log = LoggerFactory.getLogger(ThuthuResource.class);

    private static final String ENTITY_NAME = "thuthu";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ThuthuService thuthuService;

    private final ThuthuRepository thuthuRepository;

    public ThuthuResource(ThuthuService thuthuService, ThuthuRepository thuthuRepository) {
        this.thuthuService = thuthuService;
        this.thuthuRepository = thuthuRepository;
    }

    /**
     * {@code POST  /thuthus} : Create a new thuthu.
     *
     * @param thuthu the thuthu to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new thuthu, or with status {@code 400 (Bad Request)} if the thuthu has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/thuthus")
    public ResponseEntity<Thuthu> createThuthu(@RequestBody Thuthu thuthu) throws URISyntaxException {
        log.debug("REST request to save Thuthu : {}", thuthu);
        if (thuthu.getId() != null) {
            throw new BadRequestAlertException("A new thuthu cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Thuthu result = thuthuService.save(thuthu);
        return ResponseEntity
            .created(new URI("/api/thuthus/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /thuthus/:id} : Updates an existing thuthu.
     *
     * @param id the id of the thuthu to save.
     * @param thuthu the thuthu to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated thuthu,
     * or with status {@code 400 (Bad Request)} if the thuthu is not valid,
     * or with status {@code 500 (Internal Server Error)} if the thuthu couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/thuthus/{id}")
    public ResponseEntity<Thuthu> updateThuthu(@PathVariable(value = "id", required = false) final Long id, @RequestBody Thuthu thuthu)
        throws URISyntaxException {
        log.debug("REST request to update Thuthu : {}, {}", id, thuthu);
        if (thuthu.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, thuthu.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!thuthuRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Thuthu result = thuthuService.save(thuthu);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, thuthu.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /thuthus/:id} : Partial updates given fields of an existing thuthu, field will ignore if it is null
     *
     * @param id the id of the thuthu to save.
     * @param thuthu the thuthu to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated thuthu,
     * or with status {@code 400 (Bad Request)} if the thuthu is not valid,
     * or with status {@code 404 (Not Found)} if the thuthu is not found,
     * or with status {@code 500 (Internal Server Error)} if the thuthu couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/thuthus/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Thuthu> partialUpdateThuthu(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Thuthu thuthu
    ) throws URISyntaxException {
        log.debug("REST request to partial update Thuthu partially : {}, {}", id, thuthu);
        if (thuthu.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, thuthu.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!thuthuRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Thuthu> result = thuthuService.partialUpdate(thuthu);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, thuthu.getId().toString())
        );
    }

    /**
     * {@code GET  /thuthus} : get all the thuthus.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of thuthus in body.
     */
    @GetMapping("/thuthus")
    public List<Thuthu> getAllThuthus() {
        log.debug("REST request to get all Thuthus");
        return thuthuService.findAll();
    }

    /**
     * {@code GET  /thuthus/:id} : get the "id" thuthu.
     *
     * @param id the id of the thuthu to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the thuthu, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/thuthus/{id}")
    public ResponseEntity<Thuthu> getThuthu(@PathVariable Long id) {
        log.debug("REST request to get Thuthu : {}", id);
        Optional<Thuthu> thuthu = thuthuService.findOne(id);
        return ResponseUtil.wrapOrNotFound(thuthu);
    }

    /**
     * {@code DELETE  /thuthus/:id} : delete the "id" thuthu.
     *
     * @param id the id of the thuthu to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/thuthus/{id}")
    public ResponseEntity<Void> deleteThuthu(@PathVariable Long id) {
        log.debug("REST request to delete Thuthu : {}", id);
        thuthuService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
