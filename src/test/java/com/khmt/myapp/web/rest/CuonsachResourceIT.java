package com.khmt.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.khmt.myapp.IntegrationTest;
import com.khmt.myapp.domain.Cuonsach;
import com.khmt.myapp.repository.CuonsachRepository;
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
 * Integration tests for the {@link CuonsachResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CuonsachResourceIT {

    private static final Instant DEFAULT_NGAY_HET_HAN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_NGAY_HET_HAN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_TRANG_THAI = 1;
    private static final Integer UPDATED_TRANG_THAI = 2;

    private static final String ENTITY_API_URL = "/api/cuonsaches";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CuonsachRepository cuonsachRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCuonsachMockMvc;

    private Cuonsach cuonsach;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cuonsach createEntity(EntityManager em) {
        Cuonsach cuonsach = new Cuonsach().ngayHetHan(DEFAULT_NGAY_HET_HAN).trangThai(DEFAULT_TRANG_THAI);
        return cuonsach;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cuonsach createUpdatedEntity(EntityManager em) {
        Cuonsach cuonsach = new Cuonsach().ngayHetHan(UPDATED_NGAY_HET_HAN).trangThai(UPDATED_TRANG_THAI);
        return cuonsach;
    }

    @BeforeEach
    public void initTest() {
        cuonsach = createEntity(em);
    }

    @Test
    @Transactional
    void createCuonsach() throws Exception {
        int databaseSizeBeforeCreate = cuonsachRepository.findAll().size();
        // Create the Cuonsach
        restCuonsachMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cuonsach)))
            .andExpect(status().isCreated());

        // Validate the Cuonsach in the database
        List<Cuonsach> cuonsachList = cuonsachRepository.findAll();
        assertThat(cuonsachList).hasSize(databaseSizeBeforeCreate + 1);
        Cuonsach testCuonsach = cuonsachList.get(cuonsachList.size() - 1);
        assertThat(testCuonsach.getNgayHetHan()).isEqualTo(DEFAULT_NGAY_HET_HAN);
        assertThat(testCuonsach.getTrangThai()).isEqualTo(DEFAULT_TRANG_THAI);
    }

    @Test
    @Transactional
    void createCuonsachWithExistingId() throws Exception {
        // Create the Cuonsach with an existing ID
        cuonsach.setId(1L);

        int databaseSizeBeforeCreate = cuonsachRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCuonsachMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cuonsach)))
            .andExpect(status().isBadRequest());

        // Validate the Cuonsach in the database
        List<Cuonsach> cuonsachList = cuonsachRepository.findAll();
        assertThat(cuonsachList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCuonsaches() throws Exception {
        // Initialize the database
        cuonsachRepository.saveAndFlush(cuonsach);

        // Get all the cuonsachList
        restCuonsachMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cuonsach.getId().intValue())))
            .andExpect(jsonPath("$.[*].ngayHetHan").value(hasItem(DEFAULT_NGAY_HET_HAN.toString())))
            .andExpect(jsonPath("$.[*].trangThai").value(hasItem(DEFAULT_TRANG_THAI)));
    }

    @Test
    @Transactional
    void getCuonsach() throws Exception {
        // Initialize the database
        cuonsachRepository.saveAndFlush(cuonsach);

        // Get the cuonsach
        restCuonsachMockMvc
            .perform(get(ENTITY_API_URL_ID, cuonsach.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cuonsach.getId().intValue()))
            .andExpect(jsonPath("$.ngayHetHan").value(DEFAULT_NGAY_HET_HAN.toString()))
            .andExpect(jsonPath("$.trangThai").value(DEFAULT_TRANG_THAI));
    }

    @Test
    @Transactional
    void getNonExistingCuonsach() throws Exception {
        // Get the cuonsach
        restCuonsachMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCuonsach() throws Exception {
        // Initialize the database
        cuonsachRepository.saveAndFlush(cuonsach);

        int databaseSizeBeforeUpdate = cuonsachRepository.findAll().size();

        // Update the cuonsach
        Cuonsach updatedCuonsach = cuonsachRepository.findById(cuonsach.getId()).get();
        // Disconnect from session so that the updates on updatedCuonsach are not directly saved in db
        em.detach(updatedCuonsach);
        updatedCuonsach.ngayHetHan(UPDATED_NGAY_HET_HAN).trangThai(UPDATED_TRANG_THAI);

        restCuonsachMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCuonsach.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCuonsach))
            )
            .andExpect(status().isOk());

        // Validate the Cuonsach in the database
        List<Cuonsach> cuonsachList = cuonsachRepository.findAll();
        assertThat(cuonsachList).hasSize(databaseSizeBeforeUpdate);
        Cuonsach testCuonsach = cuonsachList.get(cuonsachList.size() - 1);
        assertThat(testCuonsach.getNgayHetHan()).isEqualTo(UPDATED_NGAY_HET_HAN);
        assertThat(testCuonsach.getTrangThai()).isEqualTo(UPDATED_TRANG_THAI);
    }

    @Test
    @Transactional
    void putNonExistingCuonsach() throws Exception {
        int databaseSizeBeforeUpdate = cuonsachRepository.findAll().size();
        cuonsach.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCuonsachMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cuonsach.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cuonsach))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cuonsach in the database
        List<Cuonsach> cuonsachList = cuonsachRepository.findAll();
        assertThat(cuonsachList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCuonsach() throws Exception {
        int databaseSizeBeforeUpdate = cuonsachRepository.findAll().size();
        cuonsach.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCuonsachMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cuonsach))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cuonsach in the database
        List<Cuonsach> cuonsachList = cuonsachRepository.findAll();
        assertThat(cuonsachList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCuonsach() throws Exception {
        int databaseSizeBeforeUpdate = cuonsachRepository.findAll().size();
        cuonsach.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCuonsachMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cuonsach)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Cuonsach in the database
        List<Cuonsach> cuonsachList = cuonsachRepository.findAll();
        assertThat(cuonsachList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCuonsachWithPatch() throws Exception {
        // Initialize the database
        cuonsachRepository.saveAndFlush(cuonsach);

        int databaseSizeBeforeUpdate = cuonsachRepository.findAll().size();

        // Update the cuonsach using partial update
        Cuonsach partialUpdatedCuonsach = new Cuonsach();
        partialUpdatedCuonsach.setId(cuonsach.getId());

        partialUpdatedCuonsach.ngayHetHan(UPDATED_NGAY_HET_HAN).trangThai(UPDATED_TRANG_THAI);

        restCuonsachMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCuonsach.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCuonsach))
            )
            .andExpect(status().isOk());

        // Validate the Cuonsach in the database
        List<Cuonsach> cuonsachList = cuonsachRepository.findAll();
        assertThat(cuonsachList).hasSize(databaseSizeBeforeUpdate);
        Cuonsach testCuonsach = cuonsachList.get(cuonsachList.size() - 1);
        assertThat(testCuonsach.getNgayHetHan()).isEqualTo(UPDATED_NGAY_HET_HAN);
        assertThat(testCuonsach.getTrangThai()).isEqualTo(UPDATED_TRANG_THAI);
    }

    @Test
    @Transactional
    void fullUpdateCuonsachWithPatch() throws Exception {
        // Initialize the database
        cuonsachRepository.saveAndFlush(cuonsach);

        int databaseSizeBeforeUpdate = cuonsachRepository.findAll().size();

        // Update the cuonsach using partial update
        Cuonsach partialUpdatedCuonsach = new Cuonsach();
        partialUpdatedCuonsach.setId(cuonsach.getId());

        partialUpdatedCuonsach.ngayHetHan(UPDATED_NGAY_HET_HAN).trangThai(UPDATED_TRANG_THAI);

        restCuonsachMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCuonsach.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCuonsach))
            )
            .andExpect(status().isOk());

        // Validate the Cuonsach in the database
        List<Cuonsach> cuonsachList = cuonsachRepository.findAll();
        assertThat(cuonsachList).hasSize(databaseSizeBeforeUpdate);
        Cuonsach testCuonsach = cuonsachList.get(cuonsachList.size() - 1);
        assertThat(testCuonsach.getNgayHetHan()).isEqualTo(UPDATED_NGAY_HET_HAN);
        assertThat(testCuonsach.getTrangThai()).isEqualTo(UPDATED_TRANG_THAI);
    }

    @Test
    @Transactional
    void patchNonExistingCuonsach() throws Exception {
        int databaseSizeBeforeUpdate = cuonsachRepository.findAll().size();
        cuonsach.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCuonsachMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cuonsach.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cuonsach))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cuonsach in the database
        List<Cuonsach> cuonsachList = cuonsachRepository.findAll();
        assertThat(cuonsachList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCuonsach() throws Exception {
        int databaseSizeBeforeUpdate = cuonsachRepository.findAll().size();
        cuonsach.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCuonsachMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cuonsach))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cuonsach in the database
        List<Cuonsach> cuonsachList = cuonsachRepository.findAll();
        assertThat(cuonsachList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCuonsach() throws Exception {
        int databaseSizeBeforeUpdate = cuonsachRepository.findAll().size();
        cuonsach.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCuonsachMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(cuonsach)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Cuonsach in the database
        List<Cuonsach> cuonsachList = cuonsachRepository.findAll();
        assertThat(cuonsachList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCuonsach() throws Exception {
        // Initialize the database
        cuonsachRepository.saveAndFlush(cuonsach);

        int databaseSizeBeforeDelete = cuonsachRepository.findAll().size();

        // Delete the cuonsach
        restCuonsachMockMvc
            .perform(delete(ENTITY_API_URL_ID, cuonsach.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Cuonsach> cuonsachList = cuonsachRepository.findAll();
        assertThat(cuonsachList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
