package com.bowerstechnologies.seamstressapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.bowerstechnologies.seamstressapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class CustOrderTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustOrder.class);
        CustOrder custOrder1 = new CustOrder();
        custOrder1.setId(1L);
        CustOrder custOrder2 = new CustOrder();
        custOrder2.setId(custOrder1.getId());
        assertThat(custOrder1).isEqualTo(custOrder2);
        custOrder2.setId(2L);
        assertThat(custOrder1).isNotEqualTo(custOrder2);
        custOrder1.setId(null);
        assertThat(custOrder1).isNotEqualTo(custOrder2);
    }
}
