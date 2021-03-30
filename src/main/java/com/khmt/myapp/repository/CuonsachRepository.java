package com.khmt.myapp.repository;

import com.khmt.myapp.domain.Cuonsach;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Cuonsach entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CuonsachRepository extends JpaRepository<Cuonsach, Long> {}
