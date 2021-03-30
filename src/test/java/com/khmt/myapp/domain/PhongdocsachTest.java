package com.khmt.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.khmt.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PhongdocsachTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Phongdocsach.class);
        Phongdocsach phongdocsach1 = new Phongdocsach();
        phongdocsach1.setId(1L);
        Phongdocsach phongdocsach2 = new Phongdocsach();
        phongdocsach2.setId(phongdocsach1.getId());
        assertThat(phongdocsach1).isEqualTo(phongdocsach2);
        phongdocsach2.setId(2L);
        assertThat(phongdocsach1).isNotEqualTo(phongdocsach2);
        phongdocsach1.setId(null);
        assertThat(phongdocsach1).isNotEqualTo(phongdocsach2);
    }
}
