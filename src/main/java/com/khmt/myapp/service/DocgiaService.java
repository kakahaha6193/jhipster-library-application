package com.khmt.myapp.service;

import com.khmt.myapp.domain.Docgia;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Docgia}.
 */
public interface DocgiaService {
    /**
     * Save a docgia.
     *
     * @param docgia the entity to save.
     * @return the persisted entity.
     */
    Docgia save(Docgia docgia);

    /**
     * Partially updates a docgia.
     *
     * @param docgia the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Docgia> partialUpdate(Docgia docgia);

    /**
     * Get all the docgias.
     *
     * @return the list of entities.
     */
    List<Docgia> findAll();

    /**
     * Get the "id" docgia.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Docgia> findOne(Long id);

    /**
     * Delete the "id" docgia.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
