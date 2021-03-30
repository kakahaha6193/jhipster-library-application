package com.khmt.myapp.service;

import com.khmt.myapp.domain.Nhaxuatban;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Nhaxuatban}.
 */
public interface NhaxuatbanService {
    /**
     * Save a nhaxuatban.
     *
     * @param nhaxuatban the entity to save.
     * @return the persisted entity.
     */
    Nhaxuatban save(Nhaxuatban nhaxuatban);

    /**
     * Partially updates a nhaxuatban.
     *
     * @param nhaxuatban the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Nhaxuatban> partialUpdate(Nhaxuatban nhaxuatban);

    /**
     * Get all the nhaxuatbans.
     *
     * @return the list of entities.
     */
    List<Nhaxuatban> findAll();

    /**
     * Get the "id" nhaxuatban.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Nhaxuatban> findOne(Long id);

    /**
     * Delete the "id" nhaxuatban.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
