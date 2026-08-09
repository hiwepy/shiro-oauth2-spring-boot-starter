package org.apache.shiro.spring.boot.oauth2.exception;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class OAuth2AuthenticationExceptionTest {
    @Test
    void defaultConstructorShouldWork() {
        OAuth2AuthenticationException ex = new OAuth2AuthenticationException();
        assertThat(ex).isNotNull();
    }
    @Test
    void constructorWithMessageShouldWork() {
        OAuth2AuthenticationException ex = new OAuth2AuthenticationException("error");
        assertThat(ex.getMessage()).isEqualTo("error");
    }
    @Test
    void constructorWithCauseShouldWork() {
        Throwable cause = new RuntimeException("root");
        OAuth2AuthenticationException ex = new OAuth2AuthenticationException(cause);
        assertThat(ex.getCause()).isEqualTo(cause);
    }
    @Test
    void constructorWithMessageAndCauseShouldWork() {
        Throwable cause = new RuntimeException("root");
        OAuth2AuthenticationException ex = new OAuth2AuthenticationException("error", cause);
        assertThat(ex.getMessage()).isEqualTo("error");
        assertThat(ex.getCause()).isEqualTo(cause);
    }
}
