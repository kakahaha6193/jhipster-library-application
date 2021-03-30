package com.khmt.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.khmt.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ThuephongTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Thuephong.class);
        Thuephong thuephong1 = new Thuephong();
        thuephong1.setId(1L);
        Thuephong thuephong2 = new Thuephong();
        thuephong2.setId(thuephong1.getId());
        assertThat(thuephong1).isEqualTo(thuephong2);
        thuephong2.setId(2L);
        assertThat(thuephong1).isNotEqualTo(thuephong2);
        thuephong1.setId(null);
        assertThat(thuephong1).isNotEqualTo(thuephong2);
    }
}
