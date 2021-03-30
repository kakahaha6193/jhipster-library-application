package com.khmt.myapp.service.impl;

import com.khmt.myapp.domain.Nhapsach;
import com.khmt.myapp.repository.NhapsachRepository;
import com.khmt.myapp.service.NhapsachService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Nhapsach}.
 */
@Service
@Transactional
public class NhapsachServiceImpl implements NhapsachService {

    private final Logger log = LoggerFactory.getLogger(NhapsachServiceImpl.class);

    private final NhapsachRepository nhapsachRepository;

    public NhapsachServiceImpl(NhapsachRepository nhapsachRepository) {
        this.nhapsachRepository = nhapsachRepository;
    }

    @Override
    public Nhapsach save(Nhapsach nhapsach) {
        log.debug("Request to save Nhapsach : {}", nhapsach);
        return nhapsachRepository.save(nhapsach);
    }

    @Override
    public Optional<Nhapsach> partialUpdate(Nhapsach nhapsach) {
        log.debug("Request to partially update Nhapsach : {}", nhapsach);

        return nhapsachRepository
            .findById(nhapsach.getId())
            .map(
                existingNhapsach -> {
                    if (nhapsach.getNgayGioNhap() != null) {
                        existingNhapsach.setNgayGioNhap(nhapsach.getNgayGioNhap());
                    }
                    if (nhapsach.getSoLuong() != null) {
                        existingNhapsach.setSoLuong(nhapsach.getSoLuong());
                    }

                    return existingNhapsach;
                }
            )
            .map(nhapsachRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Nhapsach> findAll() {
        log.debug("Request to get all Nhapsaches");
        return nhapsachRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Nhapsach> findOne(Long id) {
        log.debug("Request to get Nhapsach : {}", id);
        return nhapsachRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Nhapsach : {}", id);
        nhapsachRepository.deleteById(id);
    }
}
