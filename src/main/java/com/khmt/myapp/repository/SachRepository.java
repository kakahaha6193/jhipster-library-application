package com.khmt.myapp.repository;

import com.khmt.myapp.domain.Sach;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Sach entity.
 */
@Repository
public interface SachRepository extends JpaRepository<Sach, Long> {
    @Query(
        value = "select distinct sach from Sach sach left join fetch sach.nhapsaches",
        countQuery = "select count(distinct sach) from Sach sach"
    )
    Page<Sach> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct sach from Sach sach left join fetch sach.nhapsaches")
    List<Sach> findAllWithEagerRelationships();

    @Query("select sach from Sach sach left join fetch sach.nhapsaches where sach.id =:id")
    Optional<Sach> findOneWithEagerRelationships(@Param("id") Long id);
}
