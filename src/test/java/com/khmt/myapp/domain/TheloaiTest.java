package com.khmt.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.khmt.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TheloaiTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Theloai.class);
        Theloai theloai1 = new Theloai();
        theloai1.setId(1L);
        Theloai theloai2 = new Theloai();
        theloai2.setId(theloai1.getId());
        assertThat(theloai1).isEqualTo(theloai2);
        theloai2.setId(2L);
        assertThat(theloai1).isNotEqualTo(theloai2);
        theloai1.setId(null);
        assertThat(theloai1).isNotEqualTo(theloai2);
    }
}
