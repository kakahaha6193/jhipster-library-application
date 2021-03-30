package com.khmt.myapp.repository;

import com.khmt.myapp.domain.Phongdungsach;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Phongdungsach entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PhongdungsachRepository extends JpaRepository<Phongdungsach, Long> {}
