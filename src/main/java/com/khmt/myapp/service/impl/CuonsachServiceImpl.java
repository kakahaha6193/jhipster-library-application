package com.khmt.myapp.service.impl;

import com.khmt.myapp.domain.Cuonsach;
import com.khmt.myapp.repository.CuonsachRepository;
import com.khmt.myapp.service.CuonsachService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Cuonsach}.
 */
@Service
@Transactional
public class CuonsachServiceImpl implements CuonsachService {

    private final Logger log = LoggerFactory.getLogger(CuonsachServiceImpl.class);

    private final CuonsachRepository cuonsachRepository;

    public CuonsachServiceImpl(CuonsachRepository cuonsachRepository) {
        this.cuonsachRepository = cuonsachRepository;
    }

    @Override
    public Cuonsach save(Cuonsach cuonsach) {
        log.debug("Request to save Cuonsach : {}", cuonsach);
        return cuonsachRepository.save(cuonsach);
    }

    @Override
    public Optional<Cuonsach> partialUpdate(Cuonsach cuonsach) {
        log.debug("Request to partially update Cuonsach : {}", cuonsach);

        return cuonsachRepository
            .findById(cuonsach.getId())
            .map(
                existingCuonsach -> {
                    if (cuonsach.getNgayHetHan() != null) {
                        existingCuonsach.setNgayHetHan(cuonsach.getNgayHetHan());
                    }
                    if (cuonsach.getTrangThai() != null) {
                        existingCuonsach.setTrangThai(cuonsach.getTrangThai());
                    }

                    return existingCuonsach;
                }
            )
            .map(cuonsachRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cuonsach> findAll() {
        log.debug("Request to get all Cuonsaches");
        return cuonsachRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Cuonsach> findOne(Long id) {
        log.debug("Request to get Cuonsach : {}", id);
        return cuonsachRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Cuonsach : {}", id);
        cuonsachRepository.deleteById(id);
    }
}
