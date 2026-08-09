package org.apache.shiro.spring.boot.oauth2.authc;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class OAuth2FormAuthenticationFilterTest {
    @Test
    void classShouldBeLoadable() {
        assertThat(OAuth2FormAuthenticationFilter.class).isNotNull();
        assertThat(OAuth2FormAuthenticationFilter.class.getSuperclass().getSimpleName()).isEqualTo("FormAuthenticationFilter");
    }
}
