package com.khmt.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.khmt.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CuonsachTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cuonsach.class);
        Cuonsach cuonsach1 = new Cuonsach();
        cuonsach1.setId(1L);
        Cuonsach cuonsach2 = new Cuonsach();
        cuonsach2.setId(cuonsach1.getId());
        assertThat(cuonsach1).isEqualTo(cuonsach2);
        cuonsach2.setId(2L);
        assertThat(cuonsach1).isNotEqualTo(cuonsach2);
        cuonsach1.setId(null);
        assertThat(cuonsach1).isNotEqualTo(cuonsach2);
    }
}
