package org.apache.shiro.spring.boot.oauth2.token;

import com.github.scribejava.core.model.OAuth2AccessToken;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class OAuth2TokenTest {
    @Test
    void hostShouldBeReturned() {
        OAuth2AccessToken accessToken = new OAuth2AccessToken("access-token");
        OAuth2Token token = new OAuth2Token("host", accessToken);
        assertThat(token.getHost()).isEqualTo("host");
    }
    @Test
    void credentialsShouldBeAccessToken() {
        OAuth2AccessToken accessToken = new OAuth2AccessToken("access-token");
        OAuth2Token token = new OAuth2Token("host", accessToken);
        assertThat(token.getCredentials()).isInstanceOf(OAuth2AccessToken.class);
    }
    @Test
    void principalShouldBeUserId() {
        OAuth2AccessToken accessToken = new OAuth2AccessToken("access-token");
        OAuth2Token token = new OAuth2Token("host", accessToken);
        token.setUserId("user1");
        assertThat(token.getPrincipal()).isEqualTo("user1");
    }
}
