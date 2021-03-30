package com.khmt.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.khmt.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PhongdungsachTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Phongdungsach.class);
        Phongdungsach phongdungsach1 = new Phongdungsach();
        phongdungsach1.setId(1L);
        Phongdungsach phongdungsach2 = new Phongdungsach();
        phongdungsach2.setId(phongdungsach1.getId());
        assertThat(phongdungsach1).isEqualTo(phongdungsach2);
        phongdungsach2.setId(2L);
        assertThat(phongdungsach1).isNotEqualTo(phongdungsach2);
        phongdungsach1.setId(null);
        assertThat(phongdungsach1).isNotEqualTo(phongdungsach2);
    }
}
