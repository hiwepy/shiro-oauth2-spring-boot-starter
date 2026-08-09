package org.apache.shiro.spring.boot.oauth2.authz;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class OAuth2AuthorizationFilterTest {
    @Test
    void defaultConstructorShouldWork() {
        OAuth2AuthorizationFilter filter = new OAuth2AuthorizationFilter();
        assertThat(filter).isNotNull();
    }
}
