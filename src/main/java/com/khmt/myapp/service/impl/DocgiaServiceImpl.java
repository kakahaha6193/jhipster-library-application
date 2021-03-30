package com.khmt.myapp.service.impl;

import com.khmt.myapp.domain.Docgia;
import com.khmt.myapp.repository.DocgiaRepository;
import com.khmt.myapp.service.DocgiaService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Docgia}.
 */
@Service
@Transactional
public class DocgiaServiceImpl implements DocgiaService {

    private final Logger log = LoggerFactory.getLogger(DocgiaServiceImpl.class);

    private final DocgiaRepository docgiaRepository;

    public DocgiaServiceImpl(DocgiaRepository docgiaRepository) {
        this.docgiaRepository = docgiaRepository;
    }

    @Override
    public Docgia save(Docgia docgia) {
        log.debug("Request to save Docgia : {}", docgia);
        return docgiaRepository.save(docgia);
    }

    @Override
    public Optional<Docgia> partialUpdate(Docgia docgia) {
        log.debug("Request to partially update Docgia : {}", docgia);

        return docgiaRepository
            .findById(docgia.getId())
            .map(
                existingDocgia -> {
                    if (docgia.getHoTen() != null) {
                        existingDocgia.setHoTen(docgia.getHoTen());
                    }
                    if (docgia.getNgaySinh() != null) {
                        existingDocgia.setNgaySinh(docgia.getNgaySinh());
                    }
                    if (docgia.getDiaChi() != null) {
                        existingDocgia.setDiaChi(docgia.getDiaChi());
                    }
                    if (docgia.getCmt() != null) {
                        existingDocgia.setCmt(docgia.getCmt());
                    }
                    if (docgia.getTrangThai() != null) {
                        existingDocgia.setTrangThai(docgia.getTrangThai());
                    }
                    if (docgia.getTienCoc() != null) {
                        existingDocgia.setTienCoc(docgia.getTienCoc());
                    }

                    return existingDocgia;
                }
            )
            .map(docgiaRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Docgia> findAll() {
        log.debug("Request to get all Docgias");
        return docgiaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Docgia> findOne(Long id) {
        log.debug("Request to get Docgia : {}", id);
        return docgiaRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Docgia : {}", id);
        docgiaRepository.deleteById(id);
    }
}
