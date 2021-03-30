package com.khmt.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.khmt.myapp.IntegrationTest;
import com.khmt.myapp.domain.Thuephong;
import com.khmt.myapp.repository.ThuephongRepository;
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
 * Integration tests for the {@link ThuephongResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ThuephongResourceIT {

    private static final Instant DEFAULT_NGAY_THUE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_NGAY_THUE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_CA = 1;
    private static final Integer UPDATED_CA = 2;

    private static final String ENTITY_API_URL = "/api/thuephongs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ThuephongRepository thuephongRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restThuephongMockMvc;

    private Thuephong thuephong;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Thuephong createEntity(EntityManager em) {
        Thuephong thuephong = new Thuephong().ngayThue(DEFAULT_NGAY_THUE).ca(DEFAULT_CA);
        return thuephong;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Thuephong createUpdatedEntity(EntityManager em) {
        Thuephong thuephong = new Thuephong().ngayThue(UPDATED_NGAY_THUE).ca(UPDATED_CA);
        return thuephong;
    }

    @BeforeEach
    public void initTest() {
        thuephong = createEntity(em);
    }

    @Test
    @Transactional
    void createThuephong() throws Exception {
        int databaseSizeBeforeCreate = thuephongRepository.findAll().size();
        // Create the Thuephong
        restThuephongMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(thuephong)))
            .andExpect(status().isCreated());

        // Validate the Thuephong in the database
        List<Thuephong> thuephongList = thuephongRepository.findAll();
        assertThat(thuephongList).hasSize(databaseSizeBeforeCreate + 1);
        Thuephong testThuephong = thuephongList.get(thuephongList.size() - 1);
        assertThat(testThuephong.getNgayThue()).isEqualTo(DEFAULT_NGAY_THUE);
        assertThat(testThuephong.getCa()).isEqualTo(DEFAULT_CA);
    }

    @Test
    @Transactional
    void createThuephongWithExistingId() throws Exception {
        // Create the Thuephong with an existing ID
        thuephong.setId(1L);

        int databaseSizeBeforeCreate = thuephongRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restThuephongMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(thuephong)))
            .andExpect(status().isBadRequest());

        // Validate the Thuephong in the database
        List<Thuephong> thuephongList = thuephongRepository.findAll();
        assertThat(thuephongList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllThuephongs() throws Exception {
        // Initialize the database
        thuephongRepository.saveAndFlush(thuephong);

        // Get all the thuephongList
        restThuephongMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(thuephong.getId().intValue())))
            .andExpect(jsonPath("$.[*].ngayThue").value(hasItem(DEFAULT_NGAY_THUE.toString())))
            .andExpect(jsonPath("$.[*].ca").value(hasItem(DEFAULT_CA)));
    }

    @Test
    @Transactional
    void getThuephong() throws Exception {
        // Initialize the database
        thuephongRepository.saveAndFlush(thuephong);

        // Get the thuephong
        restThuephongMockMvc
            .perform(get(ENTITY_API_URL_ID, thuephong.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(thuephong.getId().intValue()))
            .andExpect(jsonPath("$.ngayThue").value(DEFAULT_NGAY_THUE.toString()))
            .andExpect(jsonPath("$.ca").value(DEFAULT_CA));
    }

    @Test
    @Transactional
    void getNonExistingThuephong() throws Exception {
        // Get the thuephong
        restThuephongMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewThuephong() throws Exception {
        // Initialize the database
        thuephongRepository.saveAndFlush(thuephong);

        int databaseSizeBeforeUpdate = thuephongRepository.findAll().size();

        // Update the thuephong
        Thuephong updatedThuephong = thuephongRepository.findById(thuephong.getId()).get();
        // Disconnect from session so that the updates on updatedThuephong are not directly saved in db
        em.detach(updatedThuephong);
        updatedThuephong.ngayThue(UPDATED_NGAY_THUE).ca(UPDATED_CA);

        restThuephongMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedThuephong.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedThuephong))
            )
            .andExpect(status().isOk());

        // Validate the Thuephong in the database
        List<Thuephong> thuephongList = thuephongRepository.findAll();
        assertThat(thuephongList).hasSize(databaseSizeBeforeUpdate);
        Thuephong testThuephong = thuephongList.get(thuephongList.size() - 1);
        assertThat(testThuephong.getNgayThue()).isEqualTo(UPDATED_NGAY_THUE);
        assertThat(testThuephong.getCa()).isEqualTo(UPDATED_CA);
    }

    @Test
    @Transactional
    void putNonExistingThuephong() throws Exception {
        int databaseSizeBeforeUpdate = thuephongRepository.findAll().size();
        thuephong.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restThuephongMockMvc
            .perform(
                put(ENTITY_API_URL_ID, thuephong.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(thuephong))
            )
            .andExpect(status().isBadRequest());

        // Validate the Thuephong in the database
        List<Thuephong> thuephongList = thuephongRepository.findAll();
        assertThat(thuephongList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchThuephong() throws Exception {
        int databaseSizeBeforeUpdate = thuephongRepository.findAll().size();
        thuephong.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restThuephongMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(thuephong))
            )
            .andExpect(status().isBadRequest());

        // Validate the Thuephong in the database
        List<Thuephong> thuephongList = thuephongRepository.findAll();
        assertThat(thuephongList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamThuephong() throws Exception {
        int databaseSizeBeforeUpdate = thuephongRepository.findAll().size();
        thuephong.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restThuephongMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(thuephong)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Thuephong in the database
        List<Thuephong> thuephongList = thuephongRepository.findAll();
        assertThat(thuephongList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateThuephongWithPatch() throws Exception {
        // Initialize the database
        thuephongRepository.saveAndFlush(thuephong);

        int databaseSizeBeforeUpdate = thuephongRepository.findAll().size();

        // Update the thuephong using partial update
        Thuephong partialUpdatedThuephong = new Thuephong();
        partialUpdatedThuephong.setId(thuephong.getId());

        partialUpdatedThuephong.ngayThue(UPDATED_NGAY_THUE).ca(UPDATED_CA);

        restThuephongMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedThuephong.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedThuephong))
            )
            .andExpect(status().isOk());

        // Validate the Thuephong in the database
        List<Thuephong> thuephongList = thuephongRepository.findAll();
        assertThat(thuephongList).hasSize(databaseSizeBeforeUpdate);
        Thuephong testThuephong = thuephongList.get(thuephongList.size() - 1);
        assertThat(testThuephong.getNgayThue()).isEqualTo(UPDATED_NGAY_THUE);
        assertThat(testThuephong.getCa()).isEqualTo(UPDATED_CA);
    }

    @Test
    @Transactional
    void fullUpdateThuephongWithPatch() throws Exception {
        // Initialize the database
        thuephongRepository.saveAndFlush(thuephong);

        int databaseSizeBeforeUpdate = thuephongRepository.findAll().size();

        // Update the thuephong using partial update
        Thuephong partialUpdatedThuephong = new Thuephong();
        partialUpdatedThuephong.setId(thuephong.getId());

        partialUpdatedThuephong.ngayThue(UPDATED_NGAY_THUE).ca(UPDATED_CA);

        restThuephongMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedThuephong.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedThuephong))
            )
            .andExpect(status().isOk());

        // Validate the Thuephong in the database
        List<Thuephong> thuephongList = thuephongRepository.findAll();
        assertThat(thuephongList).hasSize(databaseSizeBeforeUpdate);
        Thuephong testThuephong = thuephongList.get(thuephongList.size() - 1);
        assertThat(testThuephong.getNgayThue()).isEqualTo(UPDATED_NGAY_THUE);
        assertThat(testThuephong.getCa()).isEqualTo(UPDATED_CA);
    }

    @Test
    @Transactional
    void patchNonExistingThuephong() throws Exception {
        int databaseSizeBeforeUpdate = thuephongRepository.findAll().size();
        thuephong.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restThuephongMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, thuephong.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(thuephong))
            )
            .andExpect(status().isBadRequest());

        // Validate the Thuephong in the database
        List<Thuephong> thuephongList = thuephongRepository.findAll();
        assertThat(thuephongList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchThuephong() throws Exception {
        int databaseSizeBeforeUpdate = thuephongRepository.findAll().size();
        thuephong.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restThuephongMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(thuephong))
            )
            .andExpect(status().isBadRequest());

        // Validate the Thuephong in the database
        List<Thuephong> thuephongList = thuephongRepository.findAll();
        assertThat(thuephongList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamThuephong() throws Exception {
        int databaseSizeBeforeUpdate = thuephongRepository.findAll().size();
        thuephong.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restThuephongMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(thuephong))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Thuephong in the database
        List<Thuephong> thuephongList = thuephongRepository.findAll();
        assertThat(thuephongList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteThuephong() throws Exception {
        // Initialize the database
        thuephongRepository.saveAndFlush(thuephong);

        int databaseSizeBeforeDelete = thuephongRepository.findAll().size();

        // Delete the thuephong
        restThuephongMockMvc
            .perform(delete(ENTITY_API_URL_ID, thuephong.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Thuephong> thuephongList = thuephongRepository.findAll();
        assertThat(thuephongList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
