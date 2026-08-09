package org.apache.shiro.spring.boot;

import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;
import static org.assertj.core.api.Assertions.assertThat;

class ShiroOAuth2WebAutoConfigurationTest {
    @Test
    void filterChainDefinitionShouldBeCreated() throws Exception {
        ShiroOAuth2WebAutoConfiguration config = new ShiroOAuth2WebAutoConfiguration();
        ShiroOAuth2Properties props = new ShiroOAuth2Properties();
        Field field = ShiroOAuth2WebAutoConfiguration.class.getDeclaredField("properties");
        field.setAccessible(true);
        field.set(config, props);
        ShiroFilterChainDefinition definition = config.shiroFilterChainDefinition();
        assertThat(definition).isNotNull();
        assertThat(definition.getFilterChainMap()).isNotNull();
    }
    @Test
    void classShouldBeLoadable() {
        assertThat(ShiroOAuth2WebAutoConfiguration.class).isNotNull();
    }
}
