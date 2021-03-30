package com.khmt.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.khmt.myapp.IntegrationTest;
import com.khmt.myapp.domain.Phongdocsach;
import com.khmt.myapp.repository.PhongdocsachRepository;
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
 * Integration tests for the {@link PhongdocsachResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PhongdocsachResourceIT {

    private static final String DEFAULT_TEN_PHONG = "AAAAAAAAAA";
    private static final String UPDATED_TEN_PHONG = "BBBBBBBBBB";

    private static final String DEFAULT_VI_TRI = "AAAAAAAAAA";
    private static final String UPDATED_VI_TRI = "BBBBBBBBBB";

    private static final Integer DEFAULT_SUC_CHUA = 1;
    private static final Integer UPDATED_SUC_CHUA = 2;

    private static final Long DEFAULT_GIA_THUE = 1L;
    private static final Long UPDATED_GIA_THUE = 2L;

    private static final String ENTITY_API_URL = "/api/phongdocsaches";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PhongdocsachRepository phongdocsachRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPhongdocsachMockMvc;

    private Phongdocsach phongdocsach;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Phongdocsach createEntity(EntityManager em) {
        Phongdocsach phongdocsach = new Phongdocsach()
            .tenPhong(DEFAULT_TEN_PHONG)
            .viTri(DEFAULT_VI_TRI)
            .sucChua(DEFAULT_SUC_CHUA)
            .giaThue(DEFAULT_GIA_THUE);
        return phongdocsach;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Phongdocsach createUpdatedEntity(EntityManager em) {
        Phongdocsach phongdocsach = new Phongdocsach()
            .tenPhong(UPDATED_TEN_PHONG)
            .viTri(UPDATED_VI_TRI)
            .sucChua(UPDATED_SUC_CHUA)
            .giaThue(UPDATED_GIA_THUE);
        return phongdocsach;
    }

    @BeforeEach
    public void initTest() {
        phongdocsach = createEntity(em);
    }

    @Test
    @Transactional
    void createPhongdocsach() throws Exception {
        int databaseSizeBeforeCreate = phongdocsachRepository.findAll().size();
        // Create the Phongdocsach
        restPhongdocsachMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(phongdocsach)))
            .andExpect(status().isCreated());

        // Validate the Phongdocsach in the database
        List<Phongdocsach> phongdocsachList = phongdocsachRepository.findAll();
        assertThat(phongdocsachList).hasSize(databaseSizeBeforeCreate + 1);
        Phongdocsach testPhongdocsach = phongdocsachList.get(phongdocsachList.size() - 1);
        assertThat(testPhongdocsach.getTenPhong()).isEqualTo(DEFAULT_TEN_PHONG);
        assertThat(testPhongdocsach.getViTri()).isEqualTo(DEFAULT_VI_TRI);
        assertThat(testPhongdocsach.getSucChua()).isEqualTo(DEFAULT_SUC_CHUA);
        assertThat(testPhongdocsach.getGiaThue()).isEqualTo(DEFAULT_GIA_THUE);
    }

    @Test
    @Transactional
    void createPhongdocsachWithExistingId() throws Exception {
        // Create the Phongdocsach with an existing ID
        phongdocsach.setId(1L);

        int databaseSizeBeforeCreate = phongdocsachRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPhongdocsachMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(phongdocsach)))
            .andExpect(status().isBadRequest());

        // Validate the Phongdocsach in the database
        List<Phongdocsach> phongdocsachList = phongdocsachRepository.findAll();
        assertThat(phongdocsachList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPhongdocsaches() throws Exception {
        // Initialize the database
        phongdocsachRepository.saveAndFlush(phongdocsach);

        // Get all the phongdocsachList
        restPhongdocsachMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(phongdocsach.getId().intValue())))
            .andExpect(jsonPath("$.[*].tenPhong").value(hasItem(DEFAULT_TEN_PHONG)))
            .andExpect(jsonPath("$.[*].viTri").value(hasItem(DEFAULT_VI_TRI)))
            .andExpect(jsonPath("$.[*].sucChua").value(hasItem(DEFAULT_SUC_CHUA)))
            .andExpect(jsonPath("$.[*].giaThue").value(hasItem(DEFAULT_GIA_THUE.intValue())));
    }

    @Test
    @Transactional
    void getPhongdocsach() throws Exception {
        // Initialize the database
        phongdocsachRepository.saveAndFlush(phongdocsach);

        // Get the phongdocsach
        restPhongdocsachMockMvc
            .perform(get(ENTITY_API_URL_ID, phongdocsach.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(phongdocsach.getId().intValue()))
            .andExpect(jsonPath("$.tenPhong").value(DEFAULT_TEN_PHONG))
            .andExpect(jsonPath("$.viTri").value(DEFAULT_VI_TRI))
            .andExpect(jsonPath("$.sucChua").value(DEFAULT_SUC_CHUA))
            .andExpect(jsonPath("$.giaThue").value(DEFAULT_GIA_THUE.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingPhongdocsach() throws Exception {
        // Get the phongdocsach
        restPhongdocsachMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPhongdocsach() throws Exception {
        // Initialize the database
        phongdocsachRepository.saveAndFlush(phongdocsach);

        int databaseSizeBeforeUpdate = phongdocsachRepository.findAll().size();

        // Update the phongdocsach
        Phongdocsach updatedPhongdocsach = phongdocsachRepository.findById(phongdocsach.getId()).get();
        // Disconnect from session so that the updates on updatedPhongdocsach are not directly saved in db
        em.detach(updatedPhongdocsach);
        updatedPhongdocsach.tenPhong(UPDATED_TEN_PHONG).viTri(UPDATED_VI_TRI).sucChua(UPDATED_SUC_CHUA).giaThue(UPDATED_GIA_THUE);

        restPhongdocsachMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPhongdocsach.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPhongdocsach))
            )
            .andExpect(status().isOk());

        // Validate the Phongdocsach in the database
        List<Phongdocsach> phongdocsachList = phongdocsachRepository.findAll();
        assertThat(phongdocsachList).hasSize(databaseSizeBeforeUpdate);
        Phongdocsach testPhongdocsach = phongdocsachList.get(phongdocsachList.size() - 1);
        assertThat(testPhongdocsach.getTenPhong()).isEqualTo(UPDATED_TEN_PHONG);
        assertThat(testPhongdocsach.getViTri()).isEqualTo(UPDATED_VI_TRI);
        assertThat(testPhongdocsach.getSucChua()).isEqualTo(UPDATED_SUC_CHUA);
        assertThat(testPhongdocsach.getGiaThue()).isEqualTo(UPDATED_GIA_THUE);
    }

    @Test
    @Transactional
    void putNonExistingPhongdocsach() throws Exception {
        int databaseSizeBeforeUpdate = phongdocsachRepository.findAll().size();
        phongdocsach.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPhongdocsachMockMvc
            .perform(
                put(ENTITY_API_URL_ID, phongdocsach.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(phongdocsach))
            )
            .andExpect(status().isBadRequest());

        // Validate the Phongdocsach in the database
        List<Phongdocsach> phongdocsachList = phongdocsachRepository.findAll();
        assertThat(phongdocsachList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPhongdocsach() throws Exception {
        int databaseSizeBeforeUpdate = phongdocsachRepository.findAll().size();
        phongdocsach.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPhongdocsachMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(phongdocsach))
            )
            .andExpect(status().isBadRequest());

        // Validate the Phongdocsach in the database
        List<Phongdocsach> phongdocsachList = phongdocsachRepository.findAll();
        assertThat(phongdocsachList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPhongdocsach() throws Exception {
        int databaseSizeBeforeUpdate = phongdocsachRepository.findAll().size();
        phongdocsach.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPhongdocsachMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(phongdocsach)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Phongdocsach in the database
        List<Phongdocsach> phongdocsachList = phongdocsachRepository.findAll();
        assertThat(phongdocsachList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePhongdocsachWithPatch() throws Exception {
        // Initialize the database
        phongdocsachRepository.saveAndFlush(phongdocsach);

        int databaseSizeBeforeUpdate = phongdocsachRepository.findAll().size();

        // Update the phongdocsach using partial update
        Phongdocsach partialUpdatedPhongdocsach = new Phongdocsach();
        partialUpdatedPhongdocsach.setId(phongdocsach.getId());

        partialUpdatedPhongdocsach.sucChua(UPDATED_SUC_CHUA).giaThue(UPDATED_GIA_THUE);

        restPhongdocsachMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPhongdocsach.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPhongdocsach))
            )
            .andExpect(status().isOk());

        // Validate the Phongdocsach in the database
        List<Phongdocsach> phongdocsachList = phongdocsachRepository.findAll();
        assertThat(phongdocsachList).hasSize(databaseSizeBeforeUpdate);
        Phongdocsach testPhongdocsach = phongdocsachList.get(phongdocsachList.size() - 1);
        assertThat(testPhongdocsach.getTenPhong()).isEqualTo(DEFAULT_TEN_PHONG);
        assertThat(testPhongdocsach.getViTri()).isEqualTo(DEFAULT_VI_TRI);
        assertThat(testPhongdocsach.getSucChua()).isEqualTo(UPDATED_SUC_CHUA);
        assertThat(testPhongdocsach.getGiaThue()).isEqualTo(UPDATED_GIA_THUE);
    }

    @Test
    @Transactional
    void fullUpdatePhongdocsachWithPatch() throws Exception {
        // Initialize the database
        phongdocsachRepository.saveAndFlush(phongdocsach);

        int databaseSizeBeforeUpdate = phongdocsachRepository.findAll().size();

        // Update the phongdocsach using partial update
        Phongdocsach partialUpdatedPhongdocsach = new Phongdocsach();
        partialUpdatedPhongdocsach.setId(phongdocsach.getId());

        partialUpdatedPhongdocsach.tenPhong(UPDATED_TEN_PHONG).viTri(UPDATED_VI_TRI).sucChua(UPDATED_SUC_CHUA).giaThue(UPDATED_GIA_THUE);

        restPhongdocsachMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPhongdocsach.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPhongdocsach))
            )
            .andExpect(status().isOk());

        // Validate the Phongdocsach in the database
        List<Phongdocsach> phongdocsachList = phongdocsachRepository.findAll();
        assertThat(phongdocsachList).hasSize(databaseSizeBeforeUpdate);
        Phongdocsach testPhongdocsach = phongdocsachList.get(phongdocsachList.size() - 1);
        assertThat(testPhongdocsach.getTenPhong()).isEqualTo(UPDATED_TEN_PHONG);
        assertThat(testPhongdocsach.getViTri()).isEqualTo(UPDATED_VI_TRI);
        assertThat(testPhongdocsach.getSucChua()).isEqualTo(UPDATED_SUC_CHUA);
        assertThat(testPhongdocsach.getGiaThue()).isEqualTo(UPDATED_GIA_THUE);
    }

    @Test
    @Transactional
    void patchNonExistingPhongdocsach() throws Exception {
        int databaseSizeBeforeUpdate = phongdocsachRepository.findAll().size();
        phongdocsach.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPhongdocsachMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, phongdocsach.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(phongdocsach))
            )
            .andExpect(status().isBadRequest());

        // Validate the Phongdocsach in the database
        List<Phongdocsach> phongdocsachList = phongdocsachRepository.findAll();
        assertThat(phongdocsachList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPhongdocsach() throws Exception {
        int databaseSizeBeforeUpdate = phongdocsachRepository.findAll().size();
        phongdocsach.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPhongdocsachMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(phongdocsach))
            )
            .andExpect(status().isBadRequest());

        // Validate the Phongdocsach in the database
        List<Phongdocsach> phongdocsachList = phongdocsachRepository.findAll();
        assertThat(phongdocsachList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPhongdocsach() throws Exception {
        int databaseSizeBeforeUpdate = phongdocsachRepository.findAll().size();
        phongdocsach.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPhongdocsachMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(phongdocsach))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Phongdocsach in the database
        List<Phongdocsach> phongdocsachList = phongdocsachRepository.findAll();
        assertThat(phongdocsachList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePhongdocsach() throws Exception {
        // Initialize the database
        phongdocsachRepository.saveAndFlush(phongdocsach);

        int databaseSizeBeforeDelete = phongdocsachRepository.findAll().size();

        // Delete the phongdocsach
        restPhongdocsachMockMvc
            .perform(delete(ENTITY_API_URL_ID, phongdocsach.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Phongdocsach> phongdocsachList = phongdocsachRepository.findAll();
        assertThat(phongdocsachList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
