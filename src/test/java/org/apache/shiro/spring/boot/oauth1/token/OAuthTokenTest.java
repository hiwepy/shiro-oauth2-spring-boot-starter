package org.apache.shiro.spring.boot.oauth1.token;

import com.github.scribejava.core.model.OAuth1AccessToken;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class OAuthTokenTest {
    @Test
    void hostShouldBeReturned() {
        OAuth1AccessToken accessToken = new OAuth1AccessToken("token", "secret");
        OAuthToken token = new OAuthToken("host", accessToken);
        assertThat(token.getHost()).isEqualTo("host");
    }
    @Test
    void credentialsShouldBeAccessToken() {
        OAuth1AccessToken accessToken = new OAuth1AccessToken("token", "secret");
        OAuthToken token = new OAuthToken("host", accessToken);
        assertThat(token.getCredentials()).isInstanceOf(OAuth1AccessToken.class);
    }
    @Test
    void principalShouldBeUserId() {
        OAuth1AccessToken accessToken = new OAuth1AccessToken("token", "secret");
        OAuthToken token = new OAuthToken("host", accessToken);
        token.setUserId("user1");
        assertThat(token.getPrincipal()).isEqualTo("user1");
    }
}
