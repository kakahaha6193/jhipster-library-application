package com.khmt.myapp.service.impl;

import com.khmt.myapp.domain.Giasach;
import com.khmt.myapp.repository.GiasachRepository;
import com.khmt.myapp.service.GiasachService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Giasach}.
 */
@Service
@Transactional
public class GiasachServiceImpl implements GiasachService {

    private final Logger log = LoggerFactory.getLogger(GiasachServiceImpl.class);

    private final GiasachRepository giasachRepository;

    public GiasachServiceImpl(GiasachRepository giasachRepository) {
        this.giasachRepository = giasachRepository;
    }

    @Override
    public Giasach save(Giasach giasach) {
        log.debug("Request to save Giasach : {}", giasach);
        return giasachRepository.save(giasach);
    }

    @Override
    public Optional<Giasach> partialUpdate(Giasach giasach) {
        log.debug("Request to partially update Giasach : {}", giasach);

        return giasachRepository
            .findById(giasach.getId())
            .map(
                existingGiasach -> {
                    if (giasach.getThuTu() != null) {
                        existingGiasach.setThuTu(giasach.getThuTu());
                    }

                    return existingGiasach;
                }
            )
            .map(giasachRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Giasach> findAll() {
        log.debug("Request to get all Giasaches");
        return giasachRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Giasach> findOne(Long id) {
        log.debug("Request to get Giasach : {}", id);
        return giasachRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Giasach : {}", id);
        giasachRepository.deleteById(id);
    }
}
