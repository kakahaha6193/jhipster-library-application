package com.khmt.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.khmt.myapp.IntegrationTest;
import com.khmt.myapp.domain.Docgia;
import com.khmt.myapp.repository.DocgiaRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link DocgiaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DocgiaResourceIT {

    private static final String DEFAULT_HO_TEN = "AAAAAAAAAA";
    private static final String UPDATED_HO_TEN = "BBBBBBBBBB";

    private static final Instant DEFAULT_NGAY_SINH = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_NGAY_SINH = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_DIA_CHI = "AAAAAAAAAA";
    private static final String UPDATED_DIA_CHI = "BBBBBBBBBB";

    private static final String DEFAULT_CMT = "AAAAAAAAAA";
    private static final String UPDATED_CMT = "BBBBBBBBBB";

    private static final Integer DEFAULT_TRANG_THAI = 1;
    private static final Integer UPDATED_TRANG_THAI = 2;

    private static final Long DEFAULT_TIEN_COC = 1L;
    private static final Long UPDATED_TIEN_COC = 2L;

    private static final String ENTITY_API_URL = "/api/docgias";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DocgiaRepository docgiaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDocgiaMockMvc;

    private Docgia docgia;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Docgia createEntity(EntityManager em) {
        Docgia docgia = new Docgia()
            .hoTen(DEFAULT_HO_TEN)
            .ngaySinh(DEFAULT_NGAY_SINH)
            .diaChi(DEFAULT_DIA_CHI)
            .cmt(DEFAULT_CMT)
            .trangThai(DEFAULT_TRANG_THAI)
            .tienCoc(DEFAULT_TIEN_COC);
        return docgia;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Docgia createUpdatedEntity(EntityManager em) {
        Docgia docgia = new Docgia()
            .hoTen(UPDATED_HO_TEN)
            .ngaySinh(UPDATED_NGAY_SINH)
            .diaChi(UPDATED_DIA_CHI)
            .cmt(UPDATED_CMT)
            .trangThai(UPDATED_TRANG_THAI)
            .tienCoc(UPDATED_TIEN_COC);
        return docgia;
    }

    @BeforeEach
    public void initTest() {
        docgia = createEntity(em);
    }

    @Test
    @Transactional
    void createDocgia() throws Exception {
        int databaseSizeBeforeCreate = docgiaRepository.findAll().size();
        // Create the Docgia
        restDocgiaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(docgia)))
            .andExpect(status().isCreated());

        // Validate the Docgia in the database
        List<Docgia> docgiaList = docgiaRepository.findAll();
        assertThat(docgiaList).hasSize(databaseSizeBeforeCreate + 1);
        Docgia testDocgia = docgiaList.get(docgiaList.size() - 1);
        assertThat(testDocgia.getHoTen()).isEqualTo(DEFAULT_HO_TEN);
        assertThat(testDocgia.getNgaySinh()).isEqualTo(DEFAULT_NGAY_SINH);
        assertThat(testDocgia.getDiaChi()).isEqualTo(DEFAULT_DIA_CHI);
        assertThat(testDocgia.getCmt()).isEqualTo(DEFAULT_CMT);
        assertThat(testDocgia.getTrangThai()).isEqualTo(DEFAULT_TRANG_THAI);
        assertThat(testDocgia.getTienCoc()).isEqualTo(DEFAULT_TIEN_COC);
    }

    @Test
    @Transactional
    void createDocgiaWithExistingId() throws Exception {
        // Create the Docgia with an existing ID
        docgia.setId(1L);

        int databaseSizeBeforeCreate = docgiaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDocgiaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(docgia)))
            .andExpect(status().isBadRequest());

        // Validate the Docgia in the database
        List<Docgia> docgiaList = docgiaRepository.findAll();
        assertThat(docgiaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDocgias() throws Exception {
        // Initialize the database
        docgiaRepository.saveAndFlush(docgia);

        // Get all the docgiaList
        restDocgiaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(docgia.getId().intValue())))
            .andExpect(jsonPath("$.[*].hoTen").value(hasItem(DEFAULT_HO_TEN)))
            .andExpect(jsonPath("$.[*].ngaySinh").value(hasItem(DEFAULT_NGAY_SINH.toString())))
            .andExpect(jsonPath("$.[*].diaChi").value(hasItem(DEFAULT_DIA_CHI)))
            .andExpect(jsonPath("$.[*].cmt").value(hasItem(DEFAULT_CMT)))
            .andExpect(jsonPath("$.[*].trangThai").value(hasItem(DEFAULT_TRANG_THAI)))
            .andExpect(jsonPath("$.[*].tienCoc").value(hasItem(DEFAULT_TIEN_COC.intValue())));
    }

    @Test
    @Transactional
    void getDocgia() throws Exception {
        // Initialize the database
        docgiaRepository.saveAndFlush(docgia);

        // Get the docgia
        restDocgiaMockMvc
            .perform(get(ENTITY_API_URL_ID, docgia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(docgia.getId().intValue()))
            .andExpect(jsonPath("$.hoTen").value(DEFAULT_HO_TEN))
            .andExpect(jsonPath("$.ngaySinh").value(DEFAULT_NGAY_SINH.toString()))
            .andExpect(jsonPath("$.diaChi").value(DEFAULT_DIA_CHI))
            .andExpect(jsonPath("$.cmt").value(DEFAULT_CMT))
            .andExpect(jsonPath("$.trangThai").value(DEFAULT_TRANG_THAI))
            .andExpect(jsonPath("$.tienCoc").value(DEFAULT_TIEN_COC.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingDocgia() throws Exception {
        // Get the docgia
        restDocgiaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDocgia() throws Exception {
        // Initialize the database
        docgiaRepository.saveAndFlush(docgia);

        int databaseSizeBeforeUpdate = docgiaRepository.findAll().size();

        // Update the docgia
        Docgia updatedDocgia = docgiaRepository.findById(docgia.getId()).get();
        // Disconnect from session so that the updates on updatedDocgia are not directly saved in db
        em.detach(updatedDocgia);
        updatedDocgia
            .hoTen(UPDATED_HO_TEN)
            .ngaySinh(UPDATED_NGAY_SINH)
            .diaChi(UPDATED_DIA_CHI)
            .cmt(UPDATED_CMT)
            .trangThai(UPDATED_TRANG_THAI)
            .tienCoc(UPDATED_TIEN_COC);

        restDocgiaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDocgia.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDocgia))
            )
            .andExpect(status().isOk());

        // Validate the Docgia in the database
        List<Docgia> docgiaList = docgiaRepository.findAll();
        assertThat(docgiaList).hasSize(databaseSizeBeforeUpdate);
        Docgia testDocgia = docgiaList.get(docgiaList.size() - 1);
        assertThat(testDocgia.getHoTen()).isEqualTo(UPDATED_HO_TEN);
        assertThat(testDocgia.getNgaySinh()).isEqualTo(UPDATED_NGAY_SINH);
        assertThat(testDocgia.getDiaChi()).isEqualTo(UPDATED_DIA_CHI);
        assertThat(testDocgia.getCmt()).isEqualTo(UPDATED_CMT);
        assertThat(testDocgia.getTrangThai()).isEqualTo(UPDATED_TRANG_THAI);
        assertThat(testDocgia.getTienCoc()).isEqualTo(UPDATED_TIEN_COC);
    }

    @Test
    @Transactional
    void putNonExistingDocgia() throws Exception {
        int databaseSizeBeforeUpdate = docgiaRepository.findAll().size();
        docgia.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocgiaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, docgia.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(docgia))
            )
            .andExpect(status().isBadRequest());

        // Validate the Docgia in the database
        List<Docgia> docgiaList = docgiaRepository.findAll();
        assertThat(docgiaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDocgia() throws Exception {
        int databaseSizeBeforeUpdate = docgiaRepository.findAll().size();
        docgia.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocgiaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(docgia))
            )
            .andExpect(status().isBadRequest());

        // Validate the Docgia in the database
        List<Docgia> docgiaList = docgiaRepository.findAll();
        assertThat(docgiaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDocgia() throws Exception {
        int databaseSizeBeforeUpdate = docgiaRepository.findAll().size();
        docgia.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocgiaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(docgia)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Docgia in the database
        List<Docgia> docgiaList = docgiaRepository.findAll();
        assertThat(docgiaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDocgiaWithPatch() throws Exception {
        // Initialize the database
        docgiaRepository.saveAndFlush(docgia);

        int databaseSizeBeforeUpdate = docgiaRepository.findAll().size();

        // Update the docgia using partial update
        Docgia partialUpdatedDocgia = new Docgia();
        partialUpdatedDocgia.setId(docgia.getId());

        partialUpdatedDocgia.hoTen(UPDATED_HO_TEN).ngaySinh(UPDATED_NGAY_SINH).diaChi(UPDATED_DIA_CHI).tienCoc(UPDATED_TIEN_COC);

        restDocgiaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDocgia.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDocgia))
            )
            .andExpect(status().isOk());

        // Validate the Docgia in the database
        List<Docgia> docgiaList = docgiaRepository.findAll();
        assertThat(docgiaList).hasSize(databaseSizeBeforeUpdate);
        Docgia testDocgia = docgiaList.get(docgiaList.size() - 1);
        assertThat(testDocgia.getHoTen()).isEqualTo(UPDATED_HO_TEN);
        assertThat(testDocgia.getNgaySinh()).isEqualTo(UPDATED_NGAY_SINH);
        assertThat(testDocgia.getDiaChi()).isEqualTo(UPDATED_DIA_CHI);
        assertThat(testDocgia.getCmt()).isEqualTo(DEFAULT_CMT);
        assertThat(testDocgia.getTrangThai()).isEqualTo(DEFAULT_TRANG_THAI);
        assertThat(testDocgia.getTienCoc()).isEqualTo(UPDATED_TIEN_COC);
    }

    @Test
    @Transactional
    void fullUpdateDocgiaWithPatch() throws Exception {
        // Initialize the database
        docgiaRepository.saveAndFlush(docgia);

        int databaseSizeBeforeUpdate = docgiaRepository.findAll().size();

        // Update the docgia using partial update
        Docgia partialUpdatedDocgia = new Docgia();
        partialUpdatedDocgia.setId(docgia.getId());

        partialUpdatedDocgia
            .hoTen(UPDATED_HO_TEN)
            .ngaySinh(UPDATED_NGAY_SINH)
            .diaChi(UPDATED_DIA_CHI)
            .cmt(UPDATED_CMT)
            .trangThai(UPDATED_TRANG_THAI)
            .tienCoc(UPDATED_TIEN_COC);

        restDocgiaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDocgia.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDocgia))
            )
            .andExpect(status().isOk());

        // Validate the Docgia in the database
        List<Docgia> docgiaList = docgiaRepository.findAll();
        assertThat(docgiaList).hasSize(databaseSizeBeforeUpdate);
        Docgia testDocgia = docgiaList.get(docgiaList.size() - 1);
        assertThat(testDocgia.getHoTen()).isEqualTo(UPDATED_HO_TEN);
        assertThat(testDocgia.getNgaySinh()).isEqualTo(UPDATED_NGAY_SINH);
        assertThat(testDocgia.getDiaChi()).isEqualTo(UPDATED_DIA_CHI);
        assertThat(testDocgia.getCmt()).isEqualTo(UPDATED_CMT);
        assertThat(testDocgia.getTrangThai()).isEqualTo(UPDATED_TRANG_THAI);
        assertThat(testDocgia.getTienCoc()).isEqualTo(UPDATED_TIEN_COC);
    }

    @Test
    @Transactional
    void patchNonExistingDocgia() throws Exception {
        int databaseSizeBeforeUpdate = docgiaRepository.findAll().size();
        docgia.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocgiaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, docgia.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(docgia))
            )
            .andExpect(status().isBadRequest());

        // Validate the Docgia in the database
        List<Docgia> docgiaList = docgiaRepository.findAll();
        assertThat(docgiaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDocgia() throws Exception {
        int databaseSizeBeforeUpdate = docgiaRepository.findAll().size();
        docgia.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocgiaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(docgia))
            )
            .andExpect(status().isBadRequest());

        // Validate the Docgia in the database
        List<Docgia> docgiaList = docgiaRepository.findAll();
        assertThat(docgiaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDocgia() throws Exception {
        int databaseSizeBeforeUpdate = docgiaRepository.findAll().size();
        docgia.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocgiaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(docgia)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Docgia in the database
        List<Docgia> docgiaList = docgiaRepository.findAll();
        assertThat(docgiaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDocgia() throws Exception {
        // Initialize the database
        docgiaRepository.saveAndFlush(docgia);

        int databaseSizeBeforeDelete = docgiaRepository.findAll().size();

        // Delete the docgia
        restDocgiaMockMvc
            .perform(delete(ENTITY_API_URL_ID, docgia.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Docgia> docgiaList = docgiaRepository.findAll();
        assertThat(docgiaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
