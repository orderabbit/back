package com.example.back.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

public class CustomOAuth2UserEntity implements OAuth2User {

    private String userId;
    private Map<String, Object> attributes;
    private Collection<? extends GrantedAuthority> authorities;

    public CustomOAuth2UserEntity(String userId, Map<String, Object> attributes, Collection<? extends GrantedAuthority> authorities) {
        this.userId = userId;
        this.attributes = attributes;
        this.authorities = authorities;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getName() {
        return this.userId;
    }
}
