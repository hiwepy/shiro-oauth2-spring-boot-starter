package org.apache.shiro.spring.boot.oauth2.authz;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class OAuth2PermissionsAuthorizationFilterTest {
    @Test
    void defaultConstructorShouldWork() {
        OAuth2PermissionsAuthorizationFilter filter = new OAuth2PermissionsAuthorizationFilter();
        assertThat(filter).isNotNull();
    }
}
