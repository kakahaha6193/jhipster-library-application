package com.khmt.myapp.service.impl;

import com.khmt.myapp.domain.Phongdocsach;
import com.khmt.myapp.repository.PhongdocsachRepository;
import com.khmt.myapp.service.PhongdocsachService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Phongdocsach}.
 */
@Service
@Transactional
public class PhongdocsachServiceImpl implements PhongdocsachService {

    private final Logger log = LoggerFactory.getLogger(PhongdocsachServiceImpl.class);

    private final PhongdocsachRepository phongdocsachRepository;

    public PhongdocsachServiceImpl(PhongdocsachRepository phongdocsachRepository) {
        this.phongdocsachRepository = phongdocsachRepository;
    }

    @Override
    public Phongdocsach save(Phongdocsach phongdocsach) {
        log.debug("Request to save Phongdocsach : {}", phongdocsach);
        return phongdocsachRepository.save(phongdocsach);
    }

    @Override
    public Optional<Phongdocsach> partialUpdate(Phongdocsach phongdocsach) {
        log.debug("Request to partially update Phongdocsach : {}", phongdocsach);

        return phongdocsachRepository
            .findById(phongdocsach.getId())
            .map(
                existingPhongdocsach -> {
                    if (phongdocsach.getTenPhong() != null) {
                        existingPhongdocsach.setTenPhong(phongdocsach.getTenPhong());
                    }
                    if (phongdocsach.getViTri() != null) {
                        existingPhongdocsach.setViTri(phongdocsach.getViTri());
                    }
                    if (phongdocsach.getSucChua() != null) {
                        existingPhongdocsach.setSucChua(phongdocsach.getSucChua());
                    }
                    if (phongdocsach.getGiaThue() != null) {
                        existingPhongdocsach.setGiaThue(phongdocsach.getGiaThue());
                    }

                    return existingPhongdocsach;
                }
            )
            .map(phongdocsachRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Phongdocsach> findAll() {
        log.debug("Request to get all Phongdocsaches");
        return phongdocsachRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Phongdocsach> findOne(Long id) {
        log.debug("Request to get Phongdocsach : {}", id);
        return phongdocsachRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Phongdocsach : {}", id);
        phongdocsachRepository.deleteById(id);
    }
}
