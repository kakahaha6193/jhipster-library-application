package com.khmt.myapp.repository;

import com.khmt.myapp.domain.Docgia;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Docgia entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DocgiaRepository extends JpaRepository<Docgia, Long> {}
