package com.khmt.myapp.service;

import com.khmt.myapp.domain.Nhapsach;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Nhapsach}.
 */
public interface NhapsachService {
    /**
     * Save a nhapsach.
     *
     * @param nhapsach the entity to save.
     * @return the persisted entity.
     */
    Nhapsach save(Nhapsach nhapsach);

    /**
     * Partially updates a nhapsach.
     *
     * @param nhapsach the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Nhapsach> partialUpdate(Nhapsach nhapsach);

    /**
     * Get all the nhapsaches.
     *
     * @return the list of entities.
     */
    List<Nhapsach> findAll();

    /**
     * Get the "id" nhapsach.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Nhapsach> findOne(Long id);

    /**
     * Delete the "id" nhapsach.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
