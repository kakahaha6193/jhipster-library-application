package com.khmt.myapp.service;

import com.khmt.myapp.domain.Phongdocsach;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Phongdocsach}.
 */
public interface PhongdocsachService {
    /**
     * Save a phongdocsach.
     *
     * @param phongdocsach the entity to save.
     * @return the persisted entity.
     */
    Phongdocsach save(Phongdocsach phongdocsach);

    /**
     * Partially updates a phongdocsach.
     *
     * @param phongdocsach the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Phongdocsach> partialUpdate(Phongdocsach phongdocsach);

    /**
     * Get all the phongdocsaches.
     *
     * @return the list of entities.
     */
    List<Phongdocsach> findAll();

    /**
     * Get the "id" phongdocsach.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Phongdocsach> findOne(Long id);

    /**
     * Delete the "id" phongdocsach.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
