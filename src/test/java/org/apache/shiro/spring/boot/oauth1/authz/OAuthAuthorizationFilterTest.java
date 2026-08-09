package org.apache.shiro.spring.boot.oauth1.authz;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class OAuthAuthorizationFilterTest {
    @Test
    void defaultConstructorShouldWork() {
        OAuthAuthorizationFilter filter = new OAuthAuthorizationFilter();
        assertThat(filter).isNotNull();
    }
}
