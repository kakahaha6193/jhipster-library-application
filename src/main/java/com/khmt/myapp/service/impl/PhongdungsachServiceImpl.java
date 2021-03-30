package com.khmt.myapp.service.impl;

import com.khmt.myapp.domain.Phongdungsach;
import com.khmt.myapp.repository.PhongdungsachRepository;
import com.khmt.myapp.service.PhongdungsachService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Phongdungsach}.
 */
@Service
@Transactional
public class PhongdungsachServiceImpl implements PhongdungsachService {

    private final Logger log = LoggerFactory.getLogger(PhongdungsachServiceImpl.class);

    private final PhongdungsachRepository phongdungsachRepository;

    public PhongdungsachServiceImpl(PhongdungsachRepository phongdungsachRepository) {
        this.phongdungsachRepository = phongdungsachRepository;
    }

    @Override
    public Phongdungsach save(Phongdungsach phongdungsach) {
        log.debug("Request to save Phongdungsach : {}", phongdungsach);
        return phongdungsachRepository.save(phongdungsach);
    }

    @Override
    public Optional<Phongdungsach> partialUpdate(Phongdungsach phongdungsach) {
        log.debug("Request to partially update Phongdungsach : {}", phongdungsach);

        return phongdungsachRepository
            .findById(phongdungsach.getId())
            .map(
                existingPhongdungsach -> {
                    if (phongdungsach.getTenPhong() != null) {
                        existingPhongdungsach.setTenPhong(phongdungsach.getTenPhong());
                    }
                    if (phongdungsach.getViTri() != null) {
                        existingPhongdungsach.setViTri(phongdungsach.getViTri());
                    }

                    return existingPhongdungsach;
                }
            )
            .map(phongdungsachRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Phongdungsach> findAll() {
        log.debug("Request to get all Phongdungsaches");
        return phongdungsachRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Phongdungsach> findOne(Long id) {
        log.debug("Request to get Phongdungsach : {}", id);
        return phongdungsachRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Phongdungsach : {}", id);
        phongdungsachRepository.deleteById(id);
    }
}
