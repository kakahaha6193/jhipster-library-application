package com.khmt.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.khmt.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MuonsachTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Muonsach.class);
        Muonsach muonsach1 = new Muonsach();
        muonsach1.setId(1L);
        Muonsach muonsach2 = new Muonsach();
        muonsach2.setId(muonsach1.getId());
        assertThat(muonsach1).isEqualTo(muonsach2);
        muonsach2.setId(2L);
        assertThat(muonsach1).isNotEqualTo(muonsach2);
        muonsach1.setId(null);
        assertThat(muonsach1).isNotEqualTo(muonsach2);
    }
}
