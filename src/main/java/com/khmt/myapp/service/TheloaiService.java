package com.khmt.myapp.service;

import com.khmt.myapp.domain.Theloai;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Theloai}.
 */
public interface TheloaiService {
    /**
     * Save a theloai.
     *
     * @param theloai the entity to save.
     * @return the persisted entity.
     */
    Theloai save(Theloai theloai);

    /**
     * Partially updates a theloai.
     *
     * @param theloai the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Theloai> partialUpdate(Theloai theloai);

    /**
     * Get all the theloais.
     *
     * @return the list of entities.
     */
    List<Theloai> findAll();

    /**
     * Get the "id" theloai.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Theloai> findOne(Long id);

    /**
     * Delete the "id" theloai.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
