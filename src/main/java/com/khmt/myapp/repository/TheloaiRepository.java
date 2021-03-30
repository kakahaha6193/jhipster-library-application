package com.khmt.myapp.repository;

import com.khmt.myapp.domain.Theloai;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Theloai entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TheloaiRepository extends JpaRepository<Theloai, Long> {}
