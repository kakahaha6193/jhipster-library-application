package com.khmt.myapp.repository;

import com.khmt.myapp.domain.Phongdocsach;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Phongdocsach entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PhongdocsachRepository extends JpaRepository<Phongdocsach, Long> {}
