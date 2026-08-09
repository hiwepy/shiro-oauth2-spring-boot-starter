package org.apache.shiro.spring.boot.oauth1.authz;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class OAuthPermissionsAuthorizationFilterTest {
    @Test
    void defaultConstructorShouldWork() {
        OAuthPermissionsAuthorizationFilter filter = new OAuthPermissionsAuthorizationFilter();
        assertThat(filter).isNotNull();
    }
}
