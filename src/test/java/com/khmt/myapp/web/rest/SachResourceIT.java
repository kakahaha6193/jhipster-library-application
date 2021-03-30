package com.khmt.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.khmt.myapp.IntegrationTest;
import com.khmt.myapp.domain.Sach;
import com.khmt.myapp.repository.SachRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SachResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class SachResourceIT {

    private static final String DEFAULT_TEN_SACH = "AAAAAAAAAA";
    private static final String UPDATED_TEN_SACH = "BBBBBBBBBB";

    private static final Integer DEFAULT_GIA_NIEM_YET = 1;
    private static final Integer UPDATED_GIA_NIEM_YET = 2;

    private static final String DEFAULT_TACGIA = "AAAAAAAAAA";
    private static final String UPDATED_TACGIA = "BBBBBBBBBB";

    private static final Long DEFAULT_GIA_THUE = 1L;
    private static final Long UPDATED_GIA_THUE = 2L;

    private static final String DEFAULT_NGAN_XEP = "AAAAAAAAAA";
    private static final String UPDATED_NGAN_XEP = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/saches";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SachRepository sachRepository;

    @Mock
    private SachRepository sachRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSachMockMvc;

    private Sach sach;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sach createEntity(EntityManager em) {
        Sach sach = new Sach()
            .tenSach(DEFAULT_TEN_SACH)
            .giaNiemYet(DEFAULT_GIA_NIEM_YET)
            .tacgia(DEFAULT_TACGIA)
            .giaThue(DEFAULT_GIA_THUE)
            .nganXep(DEFAULT_NGAN_XEP);
        return sach;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sach createUpdatedEntity(EntityManager em) {
        Sach sach = new Sach()
            .tenSach(UPDATED_TEN_SACH)
            .giaNiemYet(UPDATED_GIA_NIEM_YET)
            .tacgia(UPDATED_TACGIA)
            .giaThue(UPDATED_GIA_THUE)
            .nganXep(UPDATED_NGAN_XEP);
        return sach;
    }

    @BeforeEach
    public void initTest() {
        sach = createEntity(em);
    }

    @Test
    @Transactional
    void createSach() throws Exception {
        int databaseSizeBeforeCreate = sachRepository.findAll().size();
        // Create the Sach
        restSachMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sach)))
            .andExpect(status().isCreated());

        // Validate the Sach in the database
        List<Sach> sachList = sachRepository.findAll();
        assertThat(sachList).hasSize(databaseSizeBeforeCreate + 1);
        Sach testSach = sachList.get(sachList.size() - 1);
        assertThat(testSach.getTenSach()).isEqualTo(DEFAULT_TEN_SACH);
        assertThat(testSach.getGiaNiemYet()).isEqualTo(DEFAULT_GIA_NIEM_YET);
        assertThat(testSach.getTacgia()).isEqualTo(DEFAULT_TACGIA);
        assertThat(testSach.getGiaThue()).isEqualTo(DEFAULT_GIA_THUE);
        assertThat(testSach.getNganXep()).isEqualTo(DEFAULT_NGAN_XEP);
    }

    @Test
    @Transactional
    void createSachWithExistingId() throws Exception {
        // Create the Sach with an existing ID
        sach.setId(1L);

        int databaseSizeBeforeCreate = sachRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSachMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sach)))
            .andExpect(status().isBadRequest());

        // Validate the Sach in the database
        List<Sach> sachList = sachRepository.findAll();
        assertThat(sachList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSaches() throws Exception {
        // Initialize the database
        sachRepository.saveAndFlush(sach);

        // Get all the sachList
        restSachMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sach.getId().intValue())))
            .andExpect(jsonPath("$.[*].tenSach").value(hasItem(DEFAULT_TEN_SACH)))
            .andExpect(jsonPath("$.[*].giaNiemYet").value(hasItem(DEFAULT_GIA_NIEM_YET)))
            .andExpect(jsonPath("$.[*].tacgia").value(hasItem(DEFAULT_TACGIA)))
            .andExpect(jsonPath("$.[*].giaThue").value(hasItem(DEFAULT_GIA_THUE.intValue())))
            .andExpect(jsonPath("$.[*].nganXep").value(hasItem(DEFAULT_NGAN_XEP)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSachesWithEagerRelationshipsIsEnabled() throws Exception {
        when(sachRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSachMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(sachRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSachesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(sachRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSachMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(sachRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getSach() throws Exception {
        // Initialize the database
        sachRepository.saveAndFlush(sach);

        // Get the sach
        restSachMockMvc
            .perform(get(ENTITY_API_URL_ID, sach.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sach.getId().intValue()))
            .andExpect(jsonPath("$.tenSach").value(DEFAULT_TEN_SACH))
            .andExpect(jsonPath("$.giaNiemYet").value(DEFAULT_GIA_NIEM_YET))
            .andExpect(jsonPath("$.tacgia").value(DEFAULT_TACGIA))
            .andExpect(jsonPath("$.giaThue").value(DEFAULT_GIA_THUE.intValue()))
            .andExpect(jsonPath("$.nganXep").value(DEFAULT_NGAN_XEP));
    }

    @Test
    @Transactional
    void getNonExistingSach() throws Exception {
        // Get the sach
        restSachMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSach() throws Exception {
        // Initialize the database
        sachRepository.saveAndFlush(sach);

        int databaseSizeBeforeUpdate = sachRepository.findAll().size();

        // Update the sach
        Sach updatedSach = sachRepository.findById(sach.getId()).get();
        // Disconnect from session so that the updates on updatedSach are not directly saved in db
        em.detach(updatedSach);
        updatedSach
            .tenSach(UPDATED_TEN_SACH)
            .giaNiemYet(UPDATED_GIA_NIEM_YET)
            .tacgia(UPDATED_TACGIA)
            .giaThue(UPDATED_GIA_THUE)
            .nganXep(UPDATED_NGAN_XEP);

        restSachMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSach.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSach))
            )
            .andExpect(status().isOk());

        // Validate the Sach in the database
        List<Sach> sachList = sachRepository.findAll();
        assertThat(sachList).hasSize(databaseSizeBeforeUpdate);
        Sach testSach = sachList.get(sachList.size() - 1);
        assertThat(testSach.getTenSach()).isEqualTo(UPDATED_TEN_SACH);
        assertThat(testSach.getGiaNiemYet()).isEqualTo(UPDATED_GIA_NIEM_YET);
        assertThat(testSach.getTacgia()).isEqualTo(UPDATED_TACGIA);
        assertThat(testSach.getGiaThue()).isEqualTo(UPDATED_GIA_THUE);
        assertThat(testSach.getNganXep()).isEqualTo(UPDATED_NGAN_XEP);
    }

    @Test
    @Transactional
    void putNonExistingSach() throws Exception {
        int databaseSizeBeforeUpdate = sachRepository.findAll().size();
        sach.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSachMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sach.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sach))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sach in the database
        List<Sach> sachList = sachRepository.findAll();
        assertThat(sachList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSach() throws Exception {
        int databaseSizeBeforeUpdate = sachRepository.findAll().size();
        sach.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSachMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sach))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sach in the database
        List<Sach> sachList = sachRepository.findAll();
        assertThat(sachList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSach() throws Exception {
        int databaseSizeBeforeUpdate = sachRepository.findAll().size();
        sach.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSachMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sach)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Sach in the database
        List<Sach> sachList = sachRepository.findAll();
        assertThat(sachList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSachWithPatch() throws Exception {
        // Initialize the database
        sachRepository.saveAndFlush(sach);

        int databaseSizeBeforeUpdate = sachRepository.findAll().size();

        // Update the sach using partial update
        Sach partialUpdatedSach = new Sach();
        partialUpdatedSach.setId(sach.getId());

        partialUpdatedSach.tacgia(UPDATED_TACGIA);

        restSachMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSach.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSach))
            )
            .andExpect(status().isOk());

        // Validate the Sach in the database
        List<Sach> sachList = sachRepository.findAll();
        assertThat(sachList).hasSize(databaseSizeBeforeUpdate);
        Sach testSach = sachList.get(sachList.size() - 1);
        assertThat(testSach.getTenSach()).isEqualTo(DEFAULT_TEN_SACH);
        assertThat(testSach.getGiaNiemYet()).isEqualTo(DEFAULT_GIA_NIEM_YET);
        assertThat(testSach.getTacgia()).isEqualTo(UPDATED_TACGIA);
        assertThat(testSach.getGiaThue()).isEqualTo(DEFAULT_GIA_THUE);
        assertThat(testSach.getNganXep()).isEqualTo(DEFAULT_NGAN_XEP);
    }

    @Test
    @Transactional
    void fullUpdateSachWithPatch() throws Exception {
        // Initialize the database
        sachRepository.saveAndFlush(sach);

        int databaseSizeBeforeUpdate = sachRepository.findAll().size();

        // Update the sach using partial update
        Sach partialUpdatedSach = new Sach();
        partialUpdatedSach.setId(sach.getId());

        partialUpdatedSach
            .tenSach(UPDATED_TEN_SACH)
            .giaNiemYet(UPDATED_GIA_NIEM_YET)
            .tacgia(UPDATED_TACGIA)
            .giaThue(UPDATED_GIA_THUE)
            .nganXep(UPDATED_NGAN_XEP);

        restSachMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSach.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSach))
            )
            .andExpect(status().isOk());

        // Validate the Sach in the database
        List<Sach> sachList = sachRepository.findAll();
        assertThat(sachList).hasSize(databaseSizeBeforeUpdate);
        Sach testSach = sachList.get(sachList.size() - 1);
        assertThat(testSach.getTenSach()).isEqualTo(UPDATED_TEN_SACH);
        assertThat(testSach.getGiaNiemYet()).isEqualTo(UPDATED_GIA_NIEM_YET);
        assertThat(testSach.getTacgia()).isEqualTo(UPDATED_TACGIA);
        assertThat(testSach.getGiaThue()).isEqualTo(UPDATED_GIA_THUE);
        assertThat(testSach.getNganXep()).isEqualTo(UPDATED_NGAN_XEP);
    }

    @Test
    @Transactional
    void patchNonExistingSach() throws Exception {
        int databaseSizeBeforeUpdate = sachRepository.findAll().size();
        sach.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSachMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, sach.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sach))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sach in the database
        List<Sach> sachList = sachRepository.findAll();
        assertThat(sachList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSach() throws Exception {
        int databaseSizeBeforeUpdate = sachRepository.findAll().size();
        sach.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSachMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sach))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sach in the database
        List<Sach> sachList = sachRepository.findAll();
        assertThat(sachList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSach() throws Exception {
        int databaseSizeBeforeUpdate = sachRepository.findAll().size();
        sach.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSachMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(sach)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Sach in the database
        List<Sach> sachList = sachRepository.findAll();
        assertThat(sachList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSach() throws Exception {
        // Initialize the database
        sachRepository.saveAndFlush(sach);

        int databaseSizeBeforeDelete = sachRepository.findAll().size();

        // Delete the sach
        restSachMockMvc
            .perform(delete(ENTITY_API_URL_ID, sach.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Sach> sachList = sachRepository.findAll();
        assertThat(sachList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
