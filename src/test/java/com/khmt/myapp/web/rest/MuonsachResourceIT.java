package com.khmt.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.khmt.myapp.IntegrationTest;
import com.khmt.myapp.domain.Muonsach;
import com.khmt.myapp.repository.MuonsachRepository;
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
 * Integration tests for the {@link MuonsachResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MuonsachResourceIT {

    private static final Instant DEFAULT_NGAY_MUON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_NGAY_MUON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_HAN_TRA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_HAN_TRA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_NGAY_TRA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_NGAY_TRA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_TRANG_THAI = 1;
    private static final Integer UPDATED_TRANG_THAI = 2;

    private static final String ENTITY_API_URL = "/api/muonsaches";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MuonsachRepository muonsachRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMuonsachMockMvc;

    private Muonsach muonsach;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Muonsach createEntity(EntityManager em) {
        Muonsach muonsach = new Muonsach()
            .ngayMuon(DEFAULT_NGAY_MUON)
            .hanTra(DEFAULT_HAN_TRA)
            .ngayTra(DEFAULT_NGAY_TRA)
            .trangThai(DEFAULT_TRANG_THAI);
        return muonsach;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Muonsach createUpdatedEntity(EntityManager em) {
        Muonsach muonsach = new Muonsach()
            .ngayMuon(UPDATED_NGAY_MUON)
            .hanTra(UPDATED_HAN_TRA)
            .ngayTra(UPDATED_NGAY_TRA)
            .trangThai(UPDATED_TRANG_THAI);
        return muonsach;
    }

    @BeforeEach
    public void initTest() {
        muonsach = createEntity(em);
    }

    @Test
    @Transactional
    void createMuonsach() throws Exception {
        int databaseSizeBeforeCreate = muonsachRepository.findAll().size();
        // Create the Muonsach
        restMuonsachMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(muonsach)))
            .andExpect(status().isCreated());

        // Validate the Muonsach in the database
        List<Muonsach> muonsachList = muonsachRepository.findAll();
        assertThat(muonsachList).hasSize(databaseSizeBeforeCreate + 1);
        Muonsach testMuonsach = muonsachList.get(muonsachList.size() - 1);
        assertThat(testMuonsach.getNgayMuon()).isEqualTo(DEFAULT_NGAY_MUON);
        assertThat(testMuonsach.getHanTra()).isEqualTo(DEFAULT_HAN_TRA);
        assertThat(testMuonsach.getNgayTra()).isEqualTo(DEFAULT_NGAY_TRA);
        assertThat(testMuonsach.getTrangThai()).isEqualTo(DEFAULT_TRANG_THAI);
    }

    @Test
    @Transactional
    void createMuonsachWithExistingId() throws Exception {
        // Create the Muonsach with an existing ID
        muonsach.setId(1L);

        int databaseSizeBeforeCreate = muonsachRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMuonsachMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(muonsach)))
            .andExpect(status().isBadRequest());

        // Validate the Muonsach in the database
        List<Muonsach> muonsachList = muonsachRepository.findAll();
        assertThat(muonsachList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMuonsaches() throws Exception {
        // Initialize the database
        muonsachRepository.saveAndFlush(muonsach);

        // Get all the muonsachList
        restMuonsachMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(muonsach.getId().intValue())))
            .andExpect(jsonPath("$.[*].ngayMuon").value(hasItem(DEFAULT_NGAY_MUON.toString())))
            .andExpect(jsonPath("$.[*].hanTra").value(hasItem(DEFAULT_HAN_TRA.toString())))
            .andExpect(jsonPath("$.[*].ngayTra").value(hasItem(DEFAULT_NGAY_TRA.toString())))
            .andExpect(jsonPath("$.[*].trangThai").value(hasItem(DEFAULT_TRANG_THAI)));
    }

    @Test
    @Transactional
    void getMuonsach() throws Exception {
        // Initialize the database
        muonsachRepository.saveAndFlush(muonsach);

        // Get the muonsach
        restMuonsachMockMvc
            .perform(get(ENTITY_API_URL_ID, muonsach.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(muonsach.getId().intValue()))
            .andExpect(jsonPath("$.ngayMuon").value(DEFAULT_NGAY_MUON.toString()))
            .andExpect(jsonPath("$.hanTra").value(DEFAULT_HAN_TRA.toString()))
            .andExpect(jsonPath("$.ngayTra").value(DEFAULT_NGAY_TRA.toString()))
            .andExpect(jsonPath("$.trangThai").value(DEFAULT_TRANG_THAI));
    }

    @Test
    @Transactional
    void getNonExistingMuonsach() throws Exception {
        // Get the muonsach
        restMuonsachMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewMuonsach() throws Exception {
        // Initialize the database
        muonsachRepository.saveAndFlush(muonsach);

        int databaseSizeBeforeUpdate = muonsachRepository.findAll().size();

        // Update the muonsach
        Muonsach updatedMuonsach = muonsachRepository.findById(muonsach.getId()).get();
        // Disconnect from session so that the updates on updatedMuonsach are not directly saved in db
        em.detach(updatedMuonsach);
        updatedMuonsach.ngayMuon(UPDATED_NGAY_MUON).hanTra(UPDATED_HAN_TRA).ngayTra(UPDATED_NGAY_TRA).trangThai(UPDATED_TRANG_THAI);

        restMuonsachMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMuonsach.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMuonsach))
            )
            .andExpect(status().isOk());

        // Validate the Muonsach in the database
        List<Muonsach> muonsachList = muonsachRepository.findAll();
        assertThat(muonsachList).hasSize(databaseSizeBeforeUpdate);
        Muonsach testMuonsach = muonsachList.get(muonsachList.size() - 1);
        assertThat(testMuonsach.getNgayMuon()).isEqualTo(UPDATED_NGAY_MUON);
        assertThat(testMuonsach.getHanTra()).isEqualTo(UPDATED_HAN_TRA);
        assertThat(testMuonsach.getNgayTra()).isEqualTo(UPDATED_NGAY_TRA);
        assertThat(testMuonsach.getTrangThai()).isEqualTo(UPDATED_TRANG_THAI);
    }

    @Test
    @Transactional
    void putNonExistingMuonsach() throws Exception {
        int databaseSizeBeforeUpdate = muonsachRepository.findAll().size();
        muonsach.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMuonsachMockMvc
            .perform(
                put(ENTITY_API_URL_ID, muonsach.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(muonsach))
            )
            .andExpect(status().isBadRequest());

        // Validate the Muonsach in the database
        List<Muonsach> muonsachList = muonsachRepository.findAll();
        assertThat(muonsachList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMuonsach() throws Exception {
        int databaseSizeBeforeUpdate = muonsachRepository.findAll().size();
        muonsach.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMuonsachMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(muonsach))
            )
            .andExpect(status().isBadRequest());

        // Validate the Muonsach in the database
        List<Muonsach> muonsachList = muonsachRepository.findAll();
        assertThat(muonsachList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMuonsach() throws Exception {
        int databaseSizeBeforeUpdate = muonsachRepository.findAll().size();
        muonsach.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMuonsachMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(muonsach)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Muonsach in the database
        List<Muonsach> muonsachList = muonsachRepository.findAll();
        assertThat(muonsachList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMuonsachWithPatch() throws Exception {
        // Initialize the database
        muonsachRepository.saveAndFlush(muonsach);

        int databaseSizeBeforeUpdate = muonsachRepository.findAll().size();

        // Update the muonsach using partial update
        Muonsach partialUpdatedMuonsach = new Muonsach();
        partialUpdatedMuonsach.setId(muonsach.getId());

        restMuonsachMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMuonsach.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMuonsach))
            )
            .andExpect(status().isOk());

        // Validate the Muonsach in the database
        List<Muonsach> muonsachList = muonsachRepository.findAll();
        assertThat(muonsachList).hasSize(databaseSizeBeforeUpdate);
        Muonsach testMuonsach = muonsachList.get(muonsachList.size() - 1);
        assertThat(testMuonsach.getNgayMuon()).isEqualTo(DEFAULT_NGAY_MUON);
        assertThat(testMuonsach.getHanTra()).isEqualTo(DEFAULT_HAN_TRA);
        assertThat(testMuonsach.getNgayTra()).isEqualTo(DEFAULT_NGAY_TRA);
        assertThat(testMuonsach.getTrangThai()).isEqualTo(DEFAULT_TRANG_THAI);
    }

    @Test
    @Transactional
    void fullUpdateMuonsachWithPatch() throws Exception {
        // Initialize the database
        muonsachRepository.saveAndFlush(muonsach);

        int databaseSizeBeforeUpdate = muonsachRepository.findAll().size();

        // Update the muonsach using partial update
        Muonsach partialUpdatedMuonsach = new Muonsach();
        partialUpdatedMuonsach.setId(muonsach.getId());

        partialUpdatedMuonsach.ngayMuon(UPDATED_NGAY_MUON).hanTra(UPDATED_HAN_TRA).ngayTra(UPDATED_NGAY_TRA).trangThai(UPDATED_TRANG_THAI);

        restMuonsachMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMuonsach.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMuonsach))
            )
            .andExpect(status().isOk());

        // Validate the Muonsach in the database
        List<Muonsach> muonsachList = muonsachRepository.findAll();
        assertThat(muonsachList).hasSize(databaseSizeBeforeUpdate);
        Muonsach testMuonsach = muonsachList.get(muonsachList.size() - 1);
        assertThat(testMuonsach.getNgayMuon()).isEqualTo(UPDATED_NGAY_MUON);
        assertThat(testMuonsach.getHanTra()).isEqualTo(UPDATED_HAN_TRA);
        assertThat(testMuonsach.getNgayTra()).isEqualTo(UPDATED_NGAY_TRA);
        assertThat(testMuonsach.getTrangThai()).isEqualTo(UPDATED_TRANG_THAI);
    }

    @Test
    @Transactional
    void patchNonExistingMuonsach() throws Exception {
        int databaseSizeBeforeUpdate = muonsachRepository.findAll().size();
        muonsach.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMuonsachMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, muonsach.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(muonsach))
            )
            .andExpect(status().isBadRequest());

        // Validate the Muonsach in the database
        List<Muonsach> muonsachList = muonsachRepository.findAll();
        assertThat(muonsachList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMuonsach() throws Exception {
        int databaseSizeBeforeUpdate = muonsachRepository.findAll().size();
        muonsach.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMuonsachMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(muonsach))
            )
            .andExpect(status().isBadRequest());

        // Validate the Muonsach in the database
        List<Muonsach> muonsachList = muonsachRepository.findAll();
        assertThat(muonsachList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMuonsach() throws Exception {
        int databaseSizeBeforeUpdate = muonsachRepository.findAll().size();
        muonsach.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMuonsachMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(muonsach)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Muonsach in the database
        List<Muonsach> muonsachList = muonsachRepository.findAll();
        assertThat(muonsachList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMuonsach() throws Exception {
        // Initialize the database
        muonsachRepository.saveAndFlush(muonsach);

        int databaseSizeBeforeDelete = muonsachRepository.findAll().size();

        // Delete the muonsach
        restMuonsachMockMvc
            .perform(delete(ENTITY_API_URL_ID, muonsach.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Muonsach> muonsachList = muonsachRepository.findAll();
        assertThat(muonsachList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
