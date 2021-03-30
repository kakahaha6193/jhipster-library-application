package com.khmt.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.khmt.myapp.IntegrationTest;
import com.khmt.myapp.domain.Phongdungsach;
import com.khmt.myapp.repository.PhongdungsachRepository;
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
 * Integration tests for the {@link PhongdungsachResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PhongdungsachResourceIT {

    private static final String DEFAULT_TEN_PHONG = "AAAAAAAAAA";
    private static final String UPDATED_TEN_PHONG = "BBBBBBBBBB";

    private static final String DEFAULT_VI_TRI = "AAAAAAAAAA";
    private static final String UPDATED_VI_TRI = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/phongdungsaches";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PhongdungsachRepository phongdungsachRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPhongdungsachMockMvc;

    private Phongdungsach phongdungsach;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Phongdungsach createEntity(EntityManager em) {
        Phongdungsach phongdungsach = new Phongdungsach().tenPhong(DEFAULT_TEN_PHONG).viTri(DEFAULT_VI_TRI);
        return phongdungsach;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Phongdungsach createUpdatedEntity(EntityManager em) {
        Phongdungsach phongdungsach = new Phongdungsach().tenPhong(UPDATED_TEN_PHONG).viTri(UPDATED_VI_TRI);
        return phongdungsach;
    }

    @BeforeEach
    public void initTest() {
        phongdungsach = createEntity(em);
    }

    @Test
    @Transactional
    void createPhongdungsach() throws Exception {
        int databaseSizeBeforeCreate = phongdungsachRepository.findAll().size();
        // Create the Phongdungsach
        restPhongdungsachMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(phongdungsach)))
            .andExpect(status().isCreated());

        // Validate the Phongdungsach in the database
        List<Phongdungsach> phongdungsachList = phongdungsachRepository.findAll();
        assertThat(phongdungsachList).hasSize(databaseSizeBeforeCreate + 1);
        Phongdungsach testPhongdungsach = phongdungsachList.get(phongdungsachList.size() - 1);
        assertThat(testPhongdungsach.getTenPhong()).isEqualTo(DEFAULT_TEN_PHONG);
        assertThat(testPhongdungsach.getViTri()).isEqualTo(DEFAULT_VI_TRI);
    }

    @Test
    @Transactional
    void createPhongdungsachWithExistingId() throws Exception {
        // Create the Phongdungsach with an existing ID
        phongdungsach.setId(1L);

        int databaseSizeBeforeCreate = phongdungsachRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPhongdungsachMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(phongdungsach)))
            .andExpect(status().isBadRequest());

        // Validate the Phongdungsach in the database
        List<Phongdungsach> phongdungsachList = phongdungsachRepository.findAll();
        assertThat(phongdungsachList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPhongdungsaches() throws Exception {
        // Initialize the database
        phongdungsachRepository.saveAndFlush(phongdungsach);

        // Get all the phongdungsachList
        restPhongdungsachMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(phongdungsach.getId().intValue())))
            .andExpect(jsonPath("$.[*].tenPhong").value(hasItem(DEFAULT_TEN_PHONG)))
            .andExpect(jsonPath("$.[*].viTri").value(hasItem(DEFAULT_VI_TRI)));
    }

    @Test
    @Transactional
    void getPhongdungsach() throws Exception {
        // Initialize the database
        phongdungsachRepository.saveAndFlush(phongdungsach);

        // Get the phongdungsach
        restPhongdungsachMockMvc
            .perform(get(ENTITY_API_URL_ID, phongdungsach.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(phongdungsach.getId().intValue()))
            .andExpect(jsonPath("$.tenPhong").value(DEFAULT_TEN_PHONG))
            .andExpect(jsonPath("$.viTri").value(DEFAULT_VI_TRI));
    }

    @Test
    @Transactional
    void getNonExistingPhongdungsach() throws Exception {
        // Get the phongdungsach
        restPhongdungsachMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPhongdungsach() throws Exception {
        // Initialize the database
        phongdungsachRepository.saveAndFlush(phongdungsach);

        int databaseSizeBeforeUpdate = phongdungsachRepository.findAll().size();

        // Update the phongdungsach
        Phongdungsach updatedPhongdungsach = phongdungsachRepository.findById(phongdungsach.getId()).get();
        // Disconnect from session so that the updates on updatedPhongdungsach are not directly saved in db
        em.detach(updatedPhongdungsach);
        updatedPhongdungsach.tenPhong(UPDATED_TEN_PHONG).viTri(UPDATED_VI_TRI);

        restPhongdungsachMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPhongdungsach.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPhongdungsach))
            )
            .andExpect(status().isOk());

        // Validate the Phongdungsach in the database
        List<Phongdungsach> phongdungsachList = phongdungsachRepository.findAll();
        assertThat(phongdungsachList).hasSize(databaseSizeBeforeUpdate);
        Phongdungsach testPhongdungsach = phongdungsachList.get(phongdungsachList.size() - 1);
        assertThat(testPhongdungsach.getTenPhong()).isEqualTo(UPDATED_TEN_PHONG);
        assertThat(testPhongdungsach.getViTri()).isEqualTo(UPDATED_VI_TRI);
    }

    @Test
    @Transactional
    void putNonExistingPhongdungsach() throws Exception {
        int databaseSizeBeforeUpdate = phongdungsachRepository.findAll().size();
        phongdungsach.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPhongdungsachMockMvc
            .perform(
                put(ENTITY_API_URL_ID, phongdungsach.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(phongdungsach))
            )
            .andExpect(status().isBadRequest());

        // Validate the Phongdungsach in the database
        List<Phongdungsach> phongdungsachList = phongdungsachRepository.findAll();
        assertThat(phongdungsachList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPhongdungsach() throws Exception {
        int databaseSizeBeforeUpdate = phongdungsachRepository.findAll().size();
        phongdungsach.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPhongdungsachMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(phongdungsach))
            )
            .andExpect(status().isBadRequest());

        // Validate the Phongdungsach in the database
        List<Phongdungsach> phongdungsachList = phongdungsachRepository.findAll();
        assertThat(phongdungsachList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPhongdungsach() throws Exception {
        int databaseSizeBeforeUpdate = phongdungsachRepository.findAll().size();
        phongdungsach.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPhongdungsachMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(phongdungsach)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Phongdungsach in the database
        List<Phongdungsach> phongdungsachList = phongdungsachRepository.findAll();
        assertThat(phongdungsachList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePhongdungsachWithPatch() throws Exception {
        // Initialize the database
        phongdungsachRepository.saveAndFlush(phongdungsach);

        int databaseSizeBeforeUpdate = phongdungsachRepository.findAll().size();

        // Update the phongdungsach using partial update
        Phongdungsach partialUpdatedPhongdungsach = new Phongdungsach();
        partialUpdatedPhongdungsach.setId(phongdungsach.getId());

        partialUpdatedPhongdungsach.tenPhong(UPDATED_TEN_PHONG).viTri(UPDATED_VI_TRI);

        restPhongdungsachMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPhongdungsach.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPhongdungsach))
            )
            .andExpect(status().isOk());

        // Validate the Phongdungsach in the database
        List<Phongdungsach> phongdungsachList = phongdungsachRepository.findAll();
        assertThat(phongdungsachList).hasSize(databaseSizeBeforeUpdate);
        Phongdungsach testPhongdungsach = phongdungsachList.get(phongdungsachList.size() - 1);
        assertThat(testPhongdungsach.getTenPhong()).isEqualTo(UPDATED_TEN_PHONG);
        assertThat(testPhongdungsach.getViTri()).isEqualTo(UPDATED_VI_TRI);
    }

    @Test
    @Transactional
    void fullUpdatePhongdungsachWithPatch() throws Exception {
        // Initialize the database
        phongdungsachRepository.saveAndFlush(phongdungsach);

        int databaseSizeBeforeUpdate = phongdungsachRepository.findAll().size();

        // Update the phongdungsach using partial update
        Phongdungsach partialUpdatedPhongdungsach = new Phongdungsach();
        partialUpdatedPhongdungsach.setId(phongdungsach.getId());

        partialUpdatedPhongdungsach.tenPhong(UPDATED_TEN_PHONG).viTri(UPDATED_VI_TRI);

        restPhongdungsachMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPhongdungsach.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPhongdungsach))
            )
            .andExpect(status().isOk());

        // Validate the Phongdungsach in the database
        List<Phongdungsach> phongdungsachList = phongdungsachRepository.findAll();
        assertThat(phongdungsachList).hasSize(databaseSizeBeforeUpdate);
        Phongdungsach testPhongdungsach = phongdungsachList.get(phongdungsachList.size() - 1);
        assertThat(testPhongdungsach.getTenPhong()).isEqualTo(UPDATED_TEN_PHONG);
        assertThat(testPhongdungsach.getViTri()).isEqualTo(UPDATED_VI_TRI);
    }

    @Test
    @Transactional
    void patchNonExistingPhongdungsach() throws Exception {
        int databaseSizeBeforeUpdate = phongdungsachRepository.findAll().size();
        phongdungsach.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPhongdungsachMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, phongdungsach.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(phongdungsach))
            )
            .andExpect(status().isBadRequest());

        // Validate the Phongdungsach in the database
        List<Phongdungsach> phongdungsachList = phongdungsachRepository.findAll();
        assertThat(phongdungsachList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPhongdungsach() throws Exception {
        int databaseSizeBeforeUpdate = phongdungsachRepository.findAll().size();
        phongdungsach.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPhongdungsachMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(phongdungsach))
            )
            .andExpect(status().isBadRequest());

        // Validate the Phongdungsach in the database
        List<Phongdungsach> phongdungsachList = phongdungsachRepository.findAll();
        assertThat(phongdungsachList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPhongdungsach() throws Exception {
        int databaseSizeBeforeUpdate = phongdungsachRepository.findAll().size();
        phongdungsach.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPhongdungsachMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(phongdungsach))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Phongdungsach in the database
        List<Phongdungsach> phongdungsachList = phongdungsachRepository.findAll();
        assertThat(phongdungsachList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePhongdungsach() throws Exception {
        // Initialize the database
        phongdungsachRepository.saveAndFlush(phongdungsach);

        int databaseSizeBeforeDelete = phongdungsachRepository.findAll().size();

        // Delete the phongdungsach
        restPhongdungsachMockMvc
            .perform(delete(ENTITY_API_URL_ID, phongdungsach.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Phongdungsach> phongdungsachList = phongdungsachRepository.findAll();
        assertThat(phongdungsachList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
