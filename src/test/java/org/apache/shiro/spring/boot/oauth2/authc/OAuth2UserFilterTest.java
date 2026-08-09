package org.apache.shiro.spring.boot.oauth2.authc;

import com.github.scribejava.core.oauth.OAuth20Service;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.assertj.core.api.Assertions.assertThat;

class OAuth2UserFilterTest {
    @Test
    void defaultConstructorShouldWork() {
        OAuth2UserFilter filter = new OAuth2UserFilter();
        assertThat(filter).isNotNull();
    }
    @Test
    void oauth20ServiceShouldBeSettable() {
        OAuth2UserFilter filter = new OAuth2UserFilter();
        OAuth20Service service = Mockito.mock(OAuth20Service.class);
        filter.setOauth20Service(service);
        assertThat(filter.getOauth20Service()).isSameAs(service);
    }
}
