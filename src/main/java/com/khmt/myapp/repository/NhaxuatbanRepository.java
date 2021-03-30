package com.khmt.myapp.repository;

import com.khmt.myapp.domain.Nhaxuatban;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Nhaxuatban entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NhaxuatbanRepository extends JpaRepository<Nhaxuatban, Long> {}
