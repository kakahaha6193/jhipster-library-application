package com.khmt.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.khmt.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NhapsachTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Nhapsach.class);
        Nhapsach nhapsach1 = new Nhapsach();
        nhapsach1.setId(1L);
        Nhapsach nhapsach2 = new Nhapsach();
        nhapsach2.setId(nhapsach1.getId());
        assertThat(nhapsach1).isEqualTo(nhapsach2);
        nhapsach2.setId(2L);
        assertThat(nhapsach1).isNotEqualTo(nhapsach2);
        nhapsach1.setId(null);
        assertThat(nhapsach1).isNotEqualTo(nhapsach2);
    }
}
