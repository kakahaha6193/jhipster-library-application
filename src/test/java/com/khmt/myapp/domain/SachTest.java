package com.khmt.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.khmt.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SachTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sach.class);
        Sach sach1 = new Sach();
        sach1.setId(1L);
        Sach sach2 = new Sach();
        sach2.setId(sach1.getId());
        assertThat(sach1).isEqualTo(sach2);
        sach2.setId(2L);
        assertThat(sach1).isNotEqualTo(sach2);
        sach1.setId(null);
        assertThat(sach1).isNotEqualTo(sach2);
    }
}
