package com.khmt.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.khmt.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ThuthuTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Thuthu.class);
        Thuthu thuthu1 = new Thuthu();
        thuthu1.setId(1L);
        Thuthu thuthu2 = new Thuthu();
        thuthu2.setId(thuthu1.getId());
        assertThat(thuthu1).isEqualTo(thuthu2);
        thuthu2.setId(2L);
        assertThat(thuthu1).isNotEqualTo(thuthu2);
        thuthu1.setId(null);
        assertThat(thuthu1).isNotEqualTo(thuthu2);
    }
}
