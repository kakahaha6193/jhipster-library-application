package com.khmt.myapp.service.impl;

import com.khmt.myapp.domain.Nhaxuatban;
import com.khmt.myapp.repository.NhaxuatbanRepository;
import com.khmt.myapp.service.NhaxuatbanService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Nhaxuatban}.
 */
@Service
@Transactional
public class NhaxuatbanServiceImpl implements NhaxuatbanService {

    private final Logger log = LoggerFactory.getLogger(NhaxuatbanServiceImpl.class);

    private final NhaxuatbanRepository nhaxuatbanRepository;

    public NhaxuatbanServiceImpl(NhaxuatbanRepository nhaxuatbanRepository) {
        this.nhaxuatbanRepository = nhaxuatbanRepository;
    }

    @Override
    public Nhaxuatban save(Nhaxuatban nhaxuatban) {
        log.debug("Request to save Nhaxuatban : {}", nhaxuatban);
        return nhaxuatbanRepository.save(nhaxuatban);
    }

    @Override
    public Optional<Nhaxuatban> partialUpdate(Nhaxuatban nhaxuatban) {
        log.debug("Request to partially update Nhaxuatban : {}", nhaxuatban);

        return nhaxuatbanRepository
            .findById(nhaxuatban.getId())
            .map(
                existingNhaxuatban -> {
                    if (nhaxuatban.getTenNXB() != null) {
                        existingNhaxuatban.setTenNXB(nhaxuatban.getTenNXB());
                    }
                    if (nhaxuatban.getDiaChi() != null) {
                        existingNhaxuatban.setDiaChi(nhaxuatban.getDiaChi());
                    }

                    return existingNhaxuatban;
                }
            )
            .map(nhaxuatbanRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Nhaxuatban> findAll() {
        log.debug("Request to get all Nhaxuatbans");
        return nhaxuatbanRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Nhaxuatban> findOne(Long id) {
        log.debug("Request to get Nhaxuatban : {}", id);
        return nhaxuatbanRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Nhaxuatban : {}", id);
        nhaxuatbanRepository.deleteById(id);
    }
}
