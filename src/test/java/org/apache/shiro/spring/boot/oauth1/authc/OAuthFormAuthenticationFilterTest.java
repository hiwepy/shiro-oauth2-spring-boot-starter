package org.apache.shiro.spring.boot.oauth1.authc;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class OAuthFormAuthenticationFilterTest {
    @Test
    void classShouldBeLoadable() {
        assertThat(OAuthFormAuthenticationFilter.class).isNotNull();
        assertThat(OAuthFormAuthenticationFilter.class.getSuperclass().getSimpleName()).isEqualTo("FormAuthenticationFilter");
    }
}
