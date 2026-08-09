package org.apache.shiro.spring.boot.oauth1.exception;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class OAuthAuthenticationExceptionTest {
    @Test
    void defaultConstructorShouldWork() {
        OAuthAuthenticationException ex = new OAuthAuthenticationException();
        assertThat(ex).isNotNull();
    }
    @Test
    void constructorWithMessageShouldWork() {
        OAuthAuthenticationException ex = new OAuthAuthenticationException("error");
        assertThat(ex.getMessage()).isEqualTo("error");
    }
    @Test
    void constructorWithCauseShouldWork() {
        Throwable cause = new RuntimeException("root");
        OAuthAuthenticationException ex = new OAuthAuthenticationException(cause);
        assertThat(ex.getCause()).isEqualTo(cause);
    }
    @Test
    void constructorWithMessageAndCauseShouldWork() {
        Throwable cause = new RuntimeException("root");
        OAuthAuthenticationException ex = new OAuthAuthenticationException("error", cause);
        assertThat(ex.getMessage()).isEqualTo("error");
        assertThat(ex.getCause()).isEqualTo(cause);
    }
}
