package com.khmt.myapp.service;

import com.khmt.myapp.domain.Thuthu;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Thuthu}.
 */
public interface ThuthuService {
    /**
     * Save a thuthu.
     *
     * @param thuthu the entity to save.
     * @return the persisted entity.
     */
    Thuthu save(Thuthu thuthu);

    /**
     * Partially updates a thuthu.
     *
     * @param thuthu the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Thuthu> partialUpdate(Thuthu thuthu);

    /**
     * Get all the thuthus.
     *
     * @return the list of entities.
     */
    List<Thuthu> findAll();

    /**
     * Get the "id" thuthu.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Thuthu> findOne(Long id);

    /**
     * Delete the "id" thuthu.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
