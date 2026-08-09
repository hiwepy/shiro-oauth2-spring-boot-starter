package org.apache.shiro.spring.boot.oauth2.authz;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class OAuth2RolesAuthorizationFilterTest {
    @Test
    void defaultConstructorShouldWork() {
        OAuth2RolesAuthorizationFilter filter = new OAuth2RolesAuthorizationFilter();
        assertThat(filter).isNotNull();
    }
}
