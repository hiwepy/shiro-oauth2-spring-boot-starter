package org.apache.shiro.spring.boot;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class ShiroOAuth2PropertiesTest {
    @Test
    void defaultValuesShouldBeCorrect() {
        ShiroOAuth2Properties props = new ShiroOAuth2Properties();
        assertThat(props.isEnabled()).isFalse();
        assertThat(ShiroOAuth2Properties.PREFIX).isEqualTo("shiro.oauth2");
    }
    @Test
    void enabledSetterShouldWork() {
        ShiroOAuth2Properties props = new ShiroOAuth2Properties();
        props.setEnabled(true);
        assertThat(props.isEnabled()).isTrue();
    }
    @Test
    void filterChainDefinitionMapShouldBeSettable() {
        ShiroOAuth2Properties props = new ShiroOAuth2Properties();
        java.util.Map<String, String> map = new java.util.LinkedHashMap<>();
        map.put("/api/**", "oauth2");
        props.setFilterChainDefinitionMap(map);
        assertThat(props.getFilterChainDefinitionMap()).containsEntry("/api/**", "oauth2");
    }
    @Test
    void successUrlShouldBeSettable() {
        ShiroOAuth2Properties props = new ShiroOAuth2Properties();
        props.setSuccessUrl("/home");
        assertThat(props.getSuccessUrl()).isEqualTo("/home");
    }
    @Test
    void loginUrlShouldBeSettable() {
        ShiroOAuth2Properties props = new ShiroOAuth2Properties();
        props.setLoginUrl("/login");
        assertThat(props.getLoginUrl()).isEqualTo("/login");
    }
    @Test
    void baseUrlShouldBeSettable() {
        ShiroOAuth2Properties props = new ShiroOAuth2Properties();
        props.setBaseUrl("https://oauth.example.com");
        assertThat(props.getBaseUrl()).isEqualTo("https://oauth.example.com");
    }
    @Test
    void redirectUrlShouldBeSettable() {
        ShiroOAuth2Properties props = new ShiroOAuth2Properties();
        props.setRedirectUrl("/redirect");
        assertThat(props.getRedirectUrl()).isEqualTo("/redirect");
    }
    @Test
    void unauthorizedUrlShouldBeSettable() {
        ShiroOAuth2Properties props = new ShiroOAuth2Properties();
        props.setUnauthorizedUrl("/unauthorized");
        assertThat(props.getUnauthorizedUrl()).isEqualTo("/unauthorized");
    }
    @Test
    void cachingPropertiesShouldWork() {
        ShiroOAuth2Properties props = new ShiroOAuth2Properties();
        props.setCachingEnabled(true);
        props.setAuthenticationCachingEnabled(true);
        props.setAuthorizationCachingEnabled(true);
        props.setAuthenticationCacheName("authCache");
        props.setAuthorizationCacheName("authzCache");
        assertThat(props.isCachingEnabled()).isTrue();
        assertThat(props.isAuthenticationCachingEnabled()).isTrue();
        assertThat(props.isAuthorizationCachingEnabled()).isTrue();
        assertThat(props.getAuthenticationCacheName()).isEqualTo("authCache");
        assertThat(props.getAuthorizationCacheName()).isEqualTo("authzCache");
    }
    @Test
    void defaultRolesAndPermissionsShouldWork() {
        ShiroOAuth2Properties props = new ShiroOAuth2Properties();
        props.setDefaultRoles("admin,user");
        props.setDefaultPermissions("read,write");
        assertThat(props.getDefaultRoles()).isEqualTo("admin,user");
        assertThat(props.getDefaultPermissions()).isEqualTo("read,write");
    }
}
