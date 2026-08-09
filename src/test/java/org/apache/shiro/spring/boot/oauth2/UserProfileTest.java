package org.apache.shiro.spring.boot.oauth2;

import org.junit.jupiter.api.Test;
import java.io.*;
import java.util.*;
import static org.assertj.core.api.Assertions.assertThat;

class UserProfileTest {

    static class TestUserProfile extends UserProfile {
        @Override
        public void build(Object id, Map<String, Object> map) {
            this.setId(id);
            if (map != null) {
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    this.addAttribute(entry.getKey(), entry.getValue());
                }
            }
        }
    }

    @Test
    void idSetterGetterShouldWork() {
        TestUserProfile p = new TestUserProfile();
        p.setId("id1");
        assertThat(p.getId()).isEqualTo("id1");
    }
    @Test
    void attributesShouldWork() {
        TestUserProfile p = new TestUserProfile();
        p.addAttribute("key", "value");
        assertThat(p.getAttribute("key")).isEqualTo("value");
        assertThat(p.containsAttribute("key")).isTrue();
        assertThat(p.containsAttribute("missing")).isFalse();
    }
    @Test
    void authenticationAttributesShouldWork() {
        TestUserProfile p = new TestUserProfile();
        p.addAuthenticationAttribute("akey", "avalue");
        assertThat(p.getAuthenticationAttribute("akey")).isEqualTo("avalue");
        assertThat(p.containsAuthenicationAttribute("akey")).isTrue();
    }
    @Test
    void rolesShouldWork() {
        TestUserProfile p = new TestUserProfile();
        p.addRole("admin");
        p.addRole("user");
        assertThat(p.getRoles()).contains("admin", "user");
    }
    @Test
    void permissionsShouldWork() {
        TestUserProfile p = new TestUserProfile();
        p.addPermission("read");
        p.addPermission("write");
        assertThat(p.getPermissions()).contains("read", "write");
    }
    @Test
    void rememberedShouldWork() {
        TestUserProfile p = new TestUserProfile();
        p.setRemembered(true);
        assertThat(p.isRemembered()).isTrue();
    }
    @Test
    void removeAttributeShouldWork() {
        TestUserProfile p = new TestUserProfile();
        p.addAttribute("key", "value");
        p.removeAttribute("key");
        assertThat(p.containsAttribute("key")).isFalse();
    }
    @Test
    void separatorShouldExist() {
        assertThat(UserProfile.SEPARATOR).isNotEmpty();
    }
    @Test
    void addAttributesMapShouldWork() {
        TestUserProfile p = new TestUserProfile();
        Map<String, Object> attrs = new HashMap<>();
        attrs.put("k1", "v1");
        attrs.put("k2", "v2");
        p.addAttributes(attrs);
        assertThat(p.getAttribute("k1")).isEqualTo("v1");
        assertThat(p.getAttribute("k2")).isEqualTo("v2");
    }
    @Test
    void addAuthenticationAttributesMapShouldWork() {
        TestUserProfile p = new TestUserProfile();
        Map<String, Object> attrs = new HashMap<>();
        attrs.put("ak1", "av1");
        p.addAuthenticationAttributes(attrs);
        assertThat(p.getAuthenticationAttribute("ak1")).isEqualTo("av1");
    }
    @Test
    void removeAuthenticationAttributeShouldWork() {
        TestUserProfile p = new TestUserProfile();
        p.addAuthenticationAttribute("akey", "val");
        p.removeAuthenticationAttribute("akey");
        assertThat(p.containsAuthenicationAttribute("akey")).isFalse();
    }
    @Test
    void getAttributeWithTypeShouldWork() {
        TestUserProfile p = new TestUserProfile();
        p.addAttribute("num", 42);
        Integer val = p.getAttribute("num", Integer.class);
        assertThat(val).isEqualTo(42);
    }
    @Test
    void getAuthenticationAttributeWithTypeShouldWork() {
        TestUserProfile p = new TestUserProfile();
        p.addAuthenticationAttribute("flag", true);
        Boolean val = p.getAuthenticationAttribute("flag", Boolean.class);
        assertThat(val).isTrue();
    }
    @Test
    void addRolesCollectionShouldWork() {
        TestUserProfile p = new TestUserProfile();
        p.addRoles(Arrays.asList("r1", "r2"));
        assertThat(p.getRoles()).contains("r1", "r2");
    }
    @Test
    void addRolesSetShouldWork() {
        TestUserProfile p = new TestUserProfile();
        Set<String> roles = new HashSet<>(Arrays.asList("r3", "r4"));
        p.addRoles(roles);
        assertThat(p.getRoles()).contains("r3", "r4");
    }
    @Test
    void addPermissionsCollectionShouldWork() {
        TestUserProfile p = new TestUserProfile();
        p.addPermissions(Arrays.asList("p1", "p2"));
        assertThat(p.getPermissions()).contains("p1", "p2");
    }
    @Test
    void buildMethodShouldWork() {
        TestUserProfile p = new TestUserProfile();
        Map<String, Object> map = new HashMap<>();
        map.put("name", "test");
        p.build("id1", map);
        assertThat(p.getId()).isEqualTo("id1");
        assertThat(p.getAttribute("name")).isEqualTo("test");
    }
    @Test
    void buildWithThreeArgsShouldWork() {
        TestUserProfile p = new TestUserProfile();
        Map<String, Object> map = new HashMap<>();
        map.put("k", "v");
        Map<String, Object> authMap = new HashMap<>();
        authMap.put("ak", "av");
        p.build("id1", map, authMap);
        assertThat(p.getId()).isEqualTo("id1");
        assertThat(p.getAttribute("k")).isEqualTo("v");
        assertThat(p.getAuthenticationAttribute("ak")).isEqualTo("av");
    }
    @Test
    void externalizableShouldWork() throws Exception {
        TestUserProfile p = new TestUserProfile();
        p.setId("id1");
        p.addAttribute("key", "value");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        p.writeExternal(oos);
        oos.flush();
        assertThat(bos.toByteArray().length).isGreaterThan(0);
    }
    @Test
    void readExternalShouldWork() throws Exception {
        TestUserProfile p = new TestUserProfile();
        p.setId("id1");
        p.addAttribute("key", "value");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        p.writeExternal(oos);
        oos.flush();
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bis);
        TestUserProfile p2 = new TestUserProfile();
        p2.readExternal(ois);
        assertThat(p2.getId()).isEqualTo("id1");
    }
    @Test
    void typedIdShouldReturnClassNamePlusId() {
        TestUserProfile p = new TestUserProfile();
        p.setId("id1");
        assertThat(p.getTypedId()).contains("id1");
    }
    @Test
    void getAttributesShouldReturnMap() {
        TestUserProfile p = new TestUserProfile();
        p.addAttribute("k", "v");
        Map<String, Object> attrs = p.getAttributes();
        assertThat(attrs).containsEntry("k", "v");
    }
    @Test
    void getAuthenticationAttributesShouldReturnMap() {
        TestUserProfile p = new TestUserProfile();
        p.addAuthenticationAttribute("ak", "av");
        Map<String, Object> attrs = p.getAuthenticationAttributes();
        assertThat(attrs).containsEntry("ak", "av");
    }
}
