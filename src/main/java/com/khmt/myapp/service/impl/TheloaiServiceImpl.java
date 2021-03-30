package com.khmt.myapp.service.impl;

import com.khmt.myapp.domain.Theloai;
import com.khmt.myapp.repository.TheloaiRepository;
import com.khmt.myapp.service.TheloaiService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Theloai}.
 */
@Service
@Transactional
public class TheloaiServiceImpl implements TheloaiService {

    private final Logger log = LoggerFactory.getLogger(TheloaiServiceImpl.class);

    private final TheloaiRepository theloaiRepository;

    public TheloaiServiceImpl(TheloaiRepository theloaiRepository) {
        this.theloaiRepository = theloaiRepository;
    }

    @Override
    public Theloai save(Theloai theloai) {
        log.debug("Request to save Theloai : {}", theloai);
        return theloaiRepository.save(theloai);
    }

    @Override
    public Optional<Theloai> partialUpdate(Theloai theloai) {
        log.debug("Request to partially update Theloai : {}", theloai);

        return theloaiRepository
            .findById(theloai.getId())
            .map(
                existingTheloai -> {
                    if (theloai.getTenTheLoai() != null) {
                        existingTheloai.setTenTheLoai(theloai.getTenTheLoai());
                    }

                    return existingTheloai;
                }
            )
            .map(theloaiRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Theloai> findAll() {
        log.debug("Request to get all Theloais");
        return theloaiRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Theloai> findOne(Long id) {
        log.debug("Request to get Theloai : {}", id);
        return theloaiRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Theloai : {}", id);
        theloaiRepository.deleteById(id);
    }
}
