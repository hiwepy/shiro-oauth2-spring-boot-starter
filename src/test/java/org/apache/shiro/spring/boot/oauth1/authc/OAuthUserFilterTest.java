package org.apache.shiro.spring.boot.oauth1.authc;

import com.github.scribejava.core.oauth.OAuth10aService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.assertj.core.api.Assertions.assertThat;

class OAuthUserFilterTest {
    @Test
    void oauth10ServiceShouldBeSettable() {
        OAuthUserFilter filter = new OAuthUserFilter();
        OAuth10aService service = Mockito.mock(OAuth10aService.class);
        filter.setOauth10Service(service);
        assertThat(filter.getOauth10Service()).isSameAs(service);
    }
}
