package org.apache.shiro.spring.boot.oauth1.authz;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class OAuthRolesAuthorizationFilterTest {
    @Test
    void defaultConstructorShouldWork() {
        OAuthRolesAuthorizationFilter filter = new OAuthRolesAuthorizationFilter();
        assertThat(filter).isNotNull();
    }
}
