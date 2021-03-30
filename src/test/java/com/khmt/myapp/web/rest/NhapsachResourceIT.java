package com.khmt.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.khmt.myapp.IntegrationTest;
import com.khmt.myapp.domain.Nhapsach;
import com.khmt.myapp.repository.NhapsachRepository;
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
 * Integration tests for the {@link NhapsachResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NhapsachResourceIT {

    private static final Instant DEFAULT_NGAY_GIO_NHAP = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_NGAY_GIO_NHAP = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_SO_LUONG = 1;
    private static final Integer UPDATED_SO_LUONG = 2;

    private static final String ENTITY_API_URL = "/api/nhapsaches";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NhapsachRepository nhapsachRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNhapsachMockMvc;

    private Nhapsach nhapsach;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Nhapsach createEntity(EntityManager em) {
        Nhapsach nhapsach = new Nhapsach().ngayGioNhap(DEFAULT_NGAY_GIO_NHAP).soLuong(DEFAULT_SO_LUONG);
        return nhapsach;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Nhapsach createUpdatedEntity(EntityManager em) {
        Nhapsach nhapsach = new Nhapsach().ngayGioNhap(UPDATED_NGAY_GIO_NHAP).soLuong(UPDATED_SO_LUONG);
        return nhapsach;
    }

    @BeforeEach
    public void initTest() {
        nhapsach = createEntity(em);
    }

    @Test
    @Transactional
    void createNhapsach() throws Exception {
        int databaseSizeBeforeCreate = nhapsachRepository.findAll().size();
        // Create the Nhapsach
        restNhapsachMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nhapsach)))
            .andExpect(status().isCreated());

        // Validate the Nhapsach in the database
        List<Nhapsach> nhapsachList = nhapsachRepository.findAll();
        assertThat(nhapsachList).hasSize(databaseSizeBeforeCreate + 1);
        Nhapsach testNhapsach = nhapsachList.get(nhapsachList.size() - 1);
        assertThat(testNhapsach.getNgayGioNhap()).isEqualTo(DEFAULT_NGAY_GIO_NHAP);
        assertThat(testNhapsach.getSoLuong()).isEqualTo(DEFAULT_SO_LUONG);
    }

    @Test
    @Transactional
    void createNhapsachWithExistingId() throws Exception {
        // Create the Nhapsach with an existing ID
        nhapsach.setId(1L);

        int databaseSizeBeforeCreate = nhapsachRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNhapsachMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nhapsach)))
            .andExpect(status().isBadRequest());

        // Validate the Nhapsach in the database
        List<Nhapsach> nhapsachList = nhapsachRepository.findAll();
        assertThat(nhapsachList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllNhapsaches() throws Exception {
        // Initialize the database
        nhapsachRepository.saveAndFlush(nhapsach);

        // Get all the nhapsachList
        restNhapsachMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nhapsach.getId().intValue())))
            .andExpect(jsonPath("$.[*].ngayGioNhap").value(hasItem(DEFAULT_NGAY_GIO_NHAP.toString())))
            .andExpect(jsonPath("$.[*].soLuong").value(hasItem(DEFAULT_SO_LUONG)));
    }

    @Test
    @Transactional
    void getNhapsach() throws Exception {
        // Initialize the database
        nhapsachRepository.saveAndFlush(nhapsach);

        // Get the nhapsach
        restNhapsachMockMvc
            .perform(get(ENTITY_API_URL_ID, nhapsach.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(nhapsach.getId().intValue()))
            .andExpect(jsonPath("$.ngayGioNhap").value(DEFAULT_NGAY_GIO_NHAP.toString()))
            .andExpect(jsonPath("$.soLuong").value(DEFAULT_SO_LUONG));
    }

    @Test
    @Transactional
    void getNonExistingNhapsach() throws Exception {
        // Get the nhapsach
        restNhapsachMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewNhapsach() throws Exception {
        // Initialize the database
        nhapsachRepository.saveAndFlush(nhapsach);

        int databaseSizeBeforeUpdate = nhapsachRepository.findAll().size();

        // Update the nhapsach
        Nhapsach updatedNhapsach = nhapsachRepository.findById(nhapsach.getId()).get();
        // Disconnect from session so that the updates on updatedNhapsach are not directly saved in db
        em.detach(updatedNhapsach);
        updatedNhapsach.ngayGioNhap(UPDATED_NGAY_GIO_NHAP).soLuong(UPDATED_SO_LUONG);

        restNhapsachMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedNhapsach.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedNhapsach))
            )
            .andExpect(status().isOk());

        // Validate the Nhapsach in the database
        List<Nhapsach> nhapsachList = nhapsachRepository.findAll();
        assertThat(nhapsachList).hasSize(databaseSizeBeforeUpdate);
        Nhapsach testNhapsach = nhapsachList.get(nhapsachList.size() - 1);
        assertThat(testNhapsach.getNgayGioNhap()).isEqualTo(UPDATED_NGAY_GIO_NHAP);
        assertThat(testNhapsach.getSoLuong()).isEqualTo(UPDATED_SO_LUONG);
    }

    @Test
    @Transactional
    void putNonExistingNhapsach() throws Exception {
        int databaseSizeBeforeUpdate = nhapsachRepository.findAll().size();
        nhapsach.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNhapsachMockMvc
            .perform(
                put(ENTITY_API_URL_ID, nhapsach.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nhapsach))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nhapsach in the database
        List<Nhapsach> nhapsachList = nhapsachRepository.findAll();
        assertThat(nhapsachList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNhapsach() throws Exception {
        int databaseSizeBeforeUpdate = nhapsachRepository.findAll().size();
        nhapsach.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNhapsachMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nhapsach))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nhapsach in the database
        List<Nhapsach> nhapsachList = nhapsachRepository.findAll();
        assertThat(nhapsachList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNhapsach() throws Exception {
        int databaseSizeBeforeUpdate = nhapsachRepository.findAll().size();
        nhapsach.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNhapsachMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nhapsach)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Nhapsach in the database
        List<Nhapsach> nhapsachList = nhapsachRepository.findAll();
        assertThat(nhapsachList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNhapsachWithPatch() throws Exception {
        // Initialize the database
        nhapsachRepository.saveAndFlush(nhapsach);

        int databaseSizeBeforeUpdate = nhapsachRepository.findAll().size();

        // Update the nhapsach using partial update
        Nhapsach partialUpdatedNhapsach = new Nhapsach();
        partialUpdatedNhapsach.setId(nhapsach.getId());

        restNhapsachMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNhapsach.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNhapsach))
            )
            .andExpect(status().isOk());

        // Validate the Nhapsach in the database
        List<Nhapsach> nhapsachList = nhapsachRepository.findAll();
        assertThat(nhapsachList).hasSize(databaseSizeBeforeUpdate);
        Nhapsach testNhapsach = nhapsachList.get(nhapsachList.size() - 1);
        assertThat(testNhapsach.getNgayGioNhap()).isEqualTo(DEFAULT_NGAY_GIO_NHAP);
        assertThat(testNhapsach.getSoLuong()).isEqualTo(DEFAULT_SO_LUONG);
    }

    @Test
    @Transactional
    void fullUpdateNhapsachWithPatch() throws Exception {
        // Initialize the database
        nhapsachRepository.saveAndFlush(nhapsach);

        int databaseSizeBeforeUpdate = nhapsachRepository.findAll().size();

        // Update the nhapsach using partial update
        Nhapsach partialUpdatedNhapsach = new Nhapsach();
        partialUpdatedNhapsach.setId(nhapsach.getId());

        partialUpdatedNhapsach.ngayGioNhap(UPDATED_NGAY_GIO_NHAP).soLuong(UPDATED_SO_LUONG);

        restNhapsachMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNhapsach.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNhapsach))
            )
            .andExpect(status().isOk());

        // Validate the Nhapsach in the database
        List<Nhapsach> nhapsachList = nhapsachRepository.findAll();
        assertThat(nhapsachList).hasSize(databaseSizeBeforeUpdate);
        Nhapsach testNhapsach = nhapsachList.get(nhapsachList.size() - 1);
        assertThat(testNhapsach.getNgayGioNhap()).isEqualTo(UPDATED_NGAY_GIO_NHAP);
        assertThat(testNhapsach.getSoLuong()).isEqualTo(UPDATED_SO_LUONG);
    }

    @Test
    @Transactional
    void patchNonExistingNhapsach() throws Exception {
        int databaseSizeBeforeUpdate = nhapsachRepository.findAll().size();
        nhapsach.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNhapsachMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, nhapsach.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nhapsach))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nhapsach in the database
        List<Nhapsach> nhapsachList = nhapsachRepository.findAll();
        assertThat(nhapsachList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNhapsach() throws Exception {
        int databaseSizeBeforeUpdate = nhapsachRepository.findAll().size();
        nhapsach.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNhapsachMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nhapsach))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nhapsach in the database
        List<Nhapsach> nhapsachList = nhapsachRepository.findAll();
        assertThat(nhapsachList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNhapsach() throws Exception {
        int databaseSizeBeforeUpdate = nhapsachRepository.findAll().size();
        nhapsach.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNhapsachMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(nhapsach)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Nhapsach in the database
        List<Nhapsach> nhapsachList = nhapsachRepository.findAll();
        assertThat(nhapsachList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNhapsach() throws Exception {
        // Initialize the database
        nhapsachRepository.saveAndFlush(nhapsach);

        int databaseSizeBeforeDelete = nhapsachRepository.findAll().size();

        // Delete the nhapsach
        restNhapsachMockMvc
            .perform(delete(ENTITY_API_URL_ID, nhapsach.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Nhapsach> nhapsachList = nhapsachRepository.findAll();
        assertThat(nhapsachList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
