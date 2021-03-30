package com.khmt.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.khmt.myapp.IntegrationTest;
import com.khmt.myapp.domain.Giasach;
import com.khmt.myapp.repository.GiasachRepository;
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
 * Integration tests for the {@link GiasachResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GiasachResourceIT {

    private static final Integer DEFAULT_THU_TU = 1;
    private static final Integer UPDATED_THU_TU = 2;

    private static final String ENTITY_API_URL = "/api/giasaches";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private GiasachRepository giasachRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGiasachMockMvc;

    private Giasach giasach;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Giasach createEntity(EntityManager em) {
        Giasach giasach = new Giasach().thuTu(DEFAULT_THU_TU);
        return giasach;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Giasach createUpdatedEntity(EntityManager em) {
        Giasach giasach = new Giasach().thuTu(UPDATED_THU_TU);
        return giasach;
    }

    @BeforeEach
    public void initTest() {
        giasach = createEntity(em);
    }

    @Test
    @Transactional
    void createGiasach() throws Exception {
        int databaseSizeBeforeCreate = giasachRepository.findAll().size();
        // Create the Giasach
        restGiasachMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(giasach)))
            .andExpect(status().isCreated());

        // Validate the Giasach in the database
        List<Giasach> giasachList = giasachRepository.findAll();
        assertThat(giasachList).hasSize(databaseSizeBeforeCreate + 1);
        Giasach testGiasach = giasachList.get(giasachList.size() - 1);
        assertThat(testGiasach.getThuTu()).isEqualTo(DEFAULT_THU_TU);
    }

    @Test
    @Transactional
    void createGiasachWithExistingId() throws Exception {
        // Create the Giasach with an existing ID
        giasach.setId(1L);

        int databaseSizeBeforeCreate = giasachRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGiasachMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(giasach)))
            .andExpect(status().isBadRequest());

        // Validate the Giasach in the database
        List<Giasach> giasachList = giasachRepository.findAll();
        assertThat(giasachList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllGiasaches() throws Exception {
        // Initialize the database
        giasachRepository.saveAndFlush(giasach);

        // Get all the giasachList
        restGiasachMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(giasach.getId().intValue())))
            .andExpect(jsonPath("$.[*].thuTu").value(hasItem(DEFAULT_THU_TU)));
    }

    @Test
    @Transactional
    void getGiasach() throws Exception {
        // Initialize the database
        giasachRepository.saveAndFlush(giasach);

        // Get the giasach
        restGiasachMockMvc
            .perform(get(ENTITY_API_URL_ID, giasach.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(giasach.getId().intValue()))
            .andExpect(jsonPath("$.thuTu").value(DEFAULT_THU_TU));
    }

    @Test
    @Transactional
    void getNonExistingGiasach() throws Exception {
        // Get the giasach
        restGiasachMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewGiasach() throws Exception {
        // Initialize the database
        giasachRepository.saveAndFlush(giasach);

        int databaseSizeBeforeUpdate = giasachRepository.findAll().size();

        // Update the giasach
        Giasach updatedGiasach = giasachRepository.findById(giasach.getId()).get();
        // Disconnect from session so that the updates on updatedGiasach are not directly saved in db
        em.detach(updatedGiasach);
        updatedGiasach.thuTu(UPDATED_THU_TU);

        restGiasachMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedGiasach.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedGiasach))
            )
            .andExpect(status().isOk());

        // Validate the Giasach in the database
        List<Giasach> giasachList = giasachRepository.findAll();
        assertThat(giasachList).hasSize(databaseSizeBeforeUpdate);
        Giasach testGiasach = giasachList.get(giasachList.size() - 1);
        assertThat(testGiasach.getThuTu()).isEqualTo(UPDATED_THU_TU);
    }

    @Test
    @Transactional
    void putNonExistingGiasach() throws Exception {
        int databaseSizeBeforeUpdate = giasachRepository.findAll().size();
        giasach.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGiasachMockMvc
            .perform(
                put(ENTITY_API_URL_ID, giasach.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(giasach))
            )
            .andExpect(status().isBadRequest());

        // Validate the Giasach in the database
        List<Giasach> giasachList = giasachRepository.findAll();
        assertThat(giasachList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGiasach() throws Exception {
        int databaseSizeBeforeUpdate = giasachRepository.findAll().size();
        giasach.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGiasachMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(giasach))
            )
            .andExpect(status().isBadRequest());

        // Validate the Giasach in the database
        List<Giasach> giasachList = giasachRepository.findAll();
        assertThat(giasachList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGiasach() throws Exception {
        int databaseSizeBeforeUpdate = giasachRepository.findAll().size();
        giasach.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGiasachMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(giasach)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Giasach in the database
        List<Giasach> giasachList = giasachRepository.findAll();
        assertThat(giasachList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGiasachWithPatch() throws Exception {
        // Initialize the database
        giasachRepository.saveAndFlush(giasach);

        int databaseSizeBeforeUpdate = giasachRepository.findAll().size();

        // Update the giasach using partial update
        Giasach partialUpdatedGiasach = new Giasach();
        partialUpdatedGiasach.setId(giasach.getId());

        restGiasachMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGiasach.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGiasach))
            )
            .andExpect(status().isOk());

        // Validate the Giasach in the database
        List<Giasach> giasachList = giasachRepository.findAll();
        assertThat(giasachList).hasSize(databaseSizeBeforeUpdate);
        Giasach testGiasach = giasachList.get(giasachList.size() - 1);
        assertThat(testGiasach.getThuTu()).isEqualTo(DEFAULT_THU_TU);
    }

    @Test
    @Transactional
    void fullUpdateGiasachWithPatch() throws Exception {
        // Initialize the database
        giasachRepository.saveAndFlush(giasach);

        int databaseSizeBeforeUpdate = giasachRepository.findAll().size();

        // Update the giasach using partial update
        Giasach partialUpdatedGiasach = new Giasach();
        partialUpdatedGiasach.setId(giasach.getId());

        partialUpdatedGiasach.thuTu(UPDATED_THU_TU);

        restGiasachMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGiasach.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGiasach))
            )
            .andExpect(status().isOk());

        // Validate the Giasach in the database
        List<Giasach> giasachList = giasachRepository.findAll();
        assertThat(giasachList).hasSize(databaseSizeBeforeUpdate);
        Giasach testGiasach = giasachList.get(giasachList.size() - 1);
        assertThat(testGiasach.getThuTu()).isEqualTo(UPDATED_THU_TU);
    }

    @Test
    @Transactional
    void patchNonExistingGiasach() throws Exception {
        int databaseSizeBeforeUpdate = giasachRepository.findAll().size();
        giasach.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGiasachMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, giasach.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(giasach))
            )
            .andExpect(status().isBadRequest());

        // Validate the Giasach in the database
        List<Giasach> giasachList = giasachRepository.findAll();
        assertThat(giasachList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGiasach() throws Exception {
        int databaseSizeBeforeUpdate = giasachRepository.findAll().size();
        giasach.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGiasachMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(giasach))
            )
            .andExpect(status().isBadRequest());

        // Validate the Giasach in the database
        List<Giasach> giasachList = giasachRepository.findAll();
        assertThat(giasachList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGiasach() throws Exception {
        int databaseSizeBeforeUpdate = giasachRepository.findAll().size();
        giasach.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGiasachMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(giasach)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Giasach in the database
        List<Giasach> giasachList = giasachRepository.findAll();
        assertThat(giasachList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGiasach() throws Exception {
        // Initialize the database
        giasachRepository.saveAndFlush(giasach);

        int databaseSizeBeforeDelete = giasachRepository.findAll().size();

        // Delete the giasach
        restGiasachMockMvc
            .perform(delete(ENTITY_API_URL_ID, giasach.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Giasach> giasachList = giasachRepository.findAll();
        assertThat(giasachList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
