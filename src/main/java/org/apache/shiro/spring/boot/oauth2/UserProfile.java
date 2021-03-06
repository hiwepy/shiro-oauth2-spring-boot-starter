/*
 * Copyright (c) 2018, hiwepy (https://github.com/hiwepy).
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.apache.shiro.spring.boot.oauth2;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is the user profile retrieved from a provider after successful authentication: it's an identifier (string) and attributes
 * (objects). Additional concepts are the "remember me" nature of the user profile, the associated roles, permissions, client name and 
 * linked identifier.
 *
 */
public abstract class UserProfile implements Serializable, Externalizable {

    private static final long serialVersionUID = 9020114478664816338L;

    protected transient final Logger logger = LoggerFactory.getLogger(getClass());

    private String id;

    private Map<String, Object> attributes = new HashMap<>();
    
    private Map<String, Object> authenticationAttributes = new HashMap<>();

    public transient static final String SEPARATOR = "#";

    private boolean isRemembered = false;

    private Set<String> roles = new HashSet<>();

    private Set<String> permissions = new HashSet<>();

    private String clientName;

    private String linkedId;

    /**
     * Build a profile from user identifier and attributes.
     *
     * @param id user identifier
     * @param attributes user attributes
     */
    public void build(final Object id, final Map<String, Object> attributes) {
        setId(id);
        addAttributes(attributes);
    }
    
    /**
     * Build a profile from user identifier, attributes, and authentication attributes.
     * 
     * @param id user identifier
     * @param attributes user attributes
     * @param authenticationAttributes authentication attributes
     */
    public void build(final Object id, final Map<String, Object> attributes, final Map<String, Object> authenticationAttributes ) {
        build(id, attributes);
        addAuthenticationAttributes(authenticationAttributes);
    }


    /**
     * Add an attribute.
     *
     * @param key key of the attribute
     * @param value value of the attribute
     */
    public void addAttribute(final String key, Object value) {
    	this.attributes.put(key, value);
    }
    
    /**
     * Add an authentication-related attribute
     * 
     * @param key
     * @param value
     */
    public void addAuthenticationAttribute(final String key, Object value) {
    	this.authenticationAttributes.put(key, value);
    }

    
    /**
     * Add attributes.
     *
     * @param attributes use attributes
     */
    public void addAttributes(final Map<String, Object> attributes) {
        if (attributes != null) {
            for (final Map.Entry<String, Object> entry : attributes.entrySet()) {
                addAttribute(entry.getKey(), entry.getValue());
            }
        }
    }
    
    /**
     * Add authentication attributes.
     * 
     * @param attributeMap the authentication attributes
     */
    public void addAuthenticationAttributes(Map<String, Object> attributeMap) {
        if (attributeMap != null) {
            for (final Map.Entry<String, Object> entry : attributeMap.entrySet()) {
                addAuthenticationAttribute(entry.getKey(), entry.getValue());
            }
        }
    }
    /**
     * Remove an attribute by its key.
     *
     * @param key the key
     */
    public void removeAttribute(final String key) {
        attributes.remove(key);
    }
    
    /**
     * Remove an authentication attribute by its key
     * 
     * @param key the key
     */
    public void removeAuthenticationAttribute(final String key) {
        authenticationAttributes.remove(key);    
    }

    /**
     * Set the identifier and convert it if necessary.
     *
     * @param id user identifier
     */
    public void setId(final Object id) {
        String sId = id.toString();
        final String type = this.getClass().getName() + SEPARATOR;
        if (sId.startsWith(type)) {
            sId = sId.substring(type.length());
        }
        logger.debug("identifier: {}", sId);
        this.id = sId;
    }

    /**
     * Get the user identifier. This identifier is unique for this provider but not necessarily through all providers.
     *
     * @return the user identifier
     */
    public String getId() {
        return this.id;
    }

    /**
     * Get the user identifier with a prefix which is the profile type (full class name with package).
     * This identifier is unique through all providers.
     *
     * @return the typed user identifier
     */
    public String getTypedId() {
        return this.getClass().getName() + SEPARATOR + this.id;
    }

    /**
     * Get all attributes as immutable map.
     *
     * @return the immutable attributes
     */
    public Map<String, Object> getAttributes() {
        return getImmutableAttributeMap(this.attributes);
    }

    /**
     * Get all authentication attributes as an immutable map
     * 
     * @return the immutable authentication attributes
     */
    public Map<String, Object> getAuthenticationAttributes() {
        return getImmutableAttributeMap(this.authenticationAttributes);
    }
 
    private Map<String, Object> getImmutableAttributeMap(Map<String, Object> attributeMap) {
        final Map<String, Object> newAttributes = new HashMap<>();
        for (Map.Entry<String, Object> entries : attributeMap.entrySet()) {
            final String key = entries.getKey();
            final Object value = attributeMap.get(key);
            newAttributes.put(key, value);
        }
        return Collections.unmodifiableMap(newAttributes);
    }

    /**
     * Return the attribute with name.
     *
     * @param name attribute name
     * @return the attribute with name
     */
    public Object getAttribute(final String name) {
        return this.attributes.get(name);
    }
    
    /**
     * Return the authentication attribute with name.
     *
     * @param name authentication attribute name
     * @return the authentication attribute with name
     */
    public Object getAuthenticationAttribute(final String name) {
        return this.authenticationAttributes.get(name);
    }

    /**
     * Check to see if profile contains attribute name.
     *
     * @param name the name
     * @return true/false
     */
    public boolean containsAttribute(final String name) {
        return this.attributes.containsKey(name);
    }
    
    /**
     * Check to see if profile contains attribute name.
     *
     * @param name the name
     * @return true/false
     */
    public boolean containsAuthenicationAttribute(final String name) {
        return this.authenticationAttributes.containsKey(name);
    }
    
    /**
     * Return the attribute with name.
     *
     * @param name the attribute name
     * @param clazz the class of the attribute
     * @param <T> the type of the attribute
     * @return the attribute by its name
     * @since 1.8
     */
    public <T> T getAttribute(final String name, final Class<T> clazz) {
        final Object attribute = getAttribute(name);
        return getAttributeByType(name, clazz, attribute);
    }
    
    /**
     * Return authentication attribute with name
     * 
     * @param name Name of authentication attribute
     * @param clazz The class of the authentication attribute
     * @param <T> The type of the authentication attribute
     * @return the named attribute
     */
    public <T> T getAuthenticationAttribute(final String name, final Class<T> clazz)
    {
        final Object attribute = getAuthenticationAttribute(name);
        return getAttributeByType(name, clazz, attribute);
    }

    private <T> T getAttributeByType(String name, Class<T> clazz, Object attribute) {

        if (attribute == null) {
            return null;
        }

        if (!clazz.isAssignableFrom(attribute.getClass())) {
            throw new ClassCastException("Attribute [" + name
                    + " is of type " + attribute.getClass()
                    + " when we were expecting " + clazz);
        }

        return (T) attribute;
    }

    /**
     * Add a role.
     *
     * @param role the role to add.
     */
    public void addRole(final String role) {
        this.roles.add(role);
    }

    /**
     * Add roles.
     *
     * @param roles the roles to add.
     */
    public void addRoles(final Collection<String> roles) {
        this.roles.addAll(roles);
    }

    /**
     * Add roles.
     *
     * @param roles the roles to add.
     */
    public void addRoles(final Set<String> roles) {
        this.roles.addAll(roles);
    }

    /**
     * Add a permission.
     *
     * @param permission the permission to add.
     */
    public void addPermission(final String permission) {
        this.permissions.add(permission);
    }

     /** Add permissions.
     *
     * @param permissions the permissions to add.
     */
    public void addPermissions(final Collection<String> permissions) {
        this.permissions.addAll(permissions);
    }

    /**
     * Define if this profile is remembered.
     *
     * @param rme whether the user is remembered.
     */
    public void setRemembered(final boolean rme) {
        this.isRemembered = rme;
    }

    /**
     * Get the roles of the user.
     *
     * @return the user roles.
     */
    public Set<String> getRoles() {
        return Collections.unmodifiableSet(this.roles);
    }

    /**
     * Get the permissions of the user.
     *
     * @return the user permissions.
     */
    public Set<String> getPermissions() {
        return Collections.unmodifiableSet(this.permissions);
    }

    /**
     * Is the user remembered?
     *
     * @return whether the user is remembered.
     */
    public boolean isRemembered() {
        return this.isRemembered;
    }

  
    @Override
    public void writeExternal(final ObjectOutput out) throws IOException {
        out.writeObject(this.id);
        out.writeObject(this.attributes);
        out.writeObject(this.authenticationAttributes);
        out.writeBoolean(this.isRemembered);
        out.writeObject(this.roles);
        out.writeObject(this.permissions);
        out.writeObject(this.clientName);
        out.writeObject(this.linkedId);
    }

    @Override
    public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
        this.id = (String) in.readObject();
        this.attributes = (Map) in.readObject();
        this.authenticationAttributes = (Map) in.readObject();
        this.isRemembered = in.readBoolean();
        this.roles = (Set) in.readObject();
        this.permissions = (Set) in.readObject();
        this.clientName = (String) in.readObject();
        this.linkedId = (String) in.readObject();
    }

    public void clearSensitiveData() {
        // No-op. Allow subtypes to specify which state should be cleared out.
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(final String clientName) {
        this.clientName = clientName;
    }

    public String getLinkedId() {
        return linkedId;
    }

    public void setLinkedId(final String linkedId) {
        this.linkedId = linkedId;
    }
}
