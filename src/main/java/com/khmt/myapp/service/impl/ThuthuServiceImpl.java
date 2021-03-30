package com.khmt.myapp.service.impl;

import com.khmt.myapp.domain.Thuthu;
import com.khmt.myapp.repository.ThuthuRepository;
import com.khmt.myapp.service.ThuthuService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Thuthu}.
 */
@Service
@Transactional
public class ThuthuServiceImpl implements ThuthuService {

    private final Logger log = LoggerFactory.getLogger(ThuthuServiceImpl.class);

    private final ThuthuRepository thuthuRepository;

    public ThuthuServiceImpl(ThuthuRepository thuthuRepository) {
        this.thuthuRepository = thuthuRepository;
    }

    @Override
    public Thuthu save(Thuthu thuthu) {
        log.debug("Request to save Thuthu : {}", thuthu);
        return thuthuRepository.save(thuthu);
    }

    @Override
    public Optional<Thuthu> partialUpdate(Thuthu thuthu) {
        log.debug("Request to partially update Thuthu : {}", thuthu);

        return thuthuRepository
            .findById(thuthu.getId())
            .map(
                existingThuthu -> {
                    if (thuthu.getHoTen() != null) {
                        existingThuthu.setHoTen(thuthu.getHoTen());
                    }
                    if (thuthu.getUsername() != null) {
                        existingThuthu.setUsername(thuthu.getUsername());
                    }
                    if (thuthu.getPassword() != null) {
                        existingThuthu.setPassword(thuthu.getPassword());
                    }

                    return existingThuthu;
                }
            )
            .map(thuthuRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Thuthu> findAll() {
        log.debug("Request to get all Thuthus");
        return thuthuRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Thuthu> findOne(Long id) {
        log.debug("Request to get Thuthu : {}", id);
        return thuthuRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Thuthu : {}", id);
        thuthuRepository.deleteById(id);
    }
}
