package com.khmt.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.khmt.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GiasachTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Giasach.class);
        Giasach giasach1 = new Giasach();
        giasach1.setId(1L);
        Giasach giasach2 = new Giasach();
        giasach2.setId(giasach1.getId());
        assertThat(giasach1).isEqualTo(giasach2);
        giasach2.setId(2L);
        assertThat(giasach1).isNotEqualTo(giasach2);
        giasach1.setId(null);
        assertThat(giasach1).isNotEqualTo(giasach2);
    }
}
