package com.khmt.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.khmt.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NhaxuatbanTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Nhaxuatban.class);
        Nhaxuatban nhaxuatban1 = new Nhaxuatban();
        nhaxuatban1.setId(1L);
        Nhaxuatban nhaxuatban2 = new Nhaxuatban();
        nhaxuatban2.setId(nhaxuatban1.getId());
        assertThat(nhaxuatban1).isEqualTo(nhaxuatban2);
        nhaxuatban2.setId(2L);
        assertThat(nhaxuatban1).isNotEqualTo(nhaxuatban2);
        nhaxuatban1.setId(null);
        assertThat(nhaxuatban1).isNotEqualTo(nhaxuatban2);
    }
}
