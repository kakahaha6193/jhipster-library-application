package com.khmt.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.khmt.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DocgiaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Docgia.class);
        Docgia docgia1 = new Docgia();
        docgia1.setId(1L);
        Docgia docgia2 = new Docgia();
        docgia2.setId(docgia1.getId());
        assertThat(docgia1).isEqualTo(docgia2);
        docgia2.setId(2L);
        assertThat(docgia1).isNotEqualTo(docgia2);
        docgia1.setId(null);
        assertThat(docgia1).isNotEqualTo(docgia2);
    }
}
