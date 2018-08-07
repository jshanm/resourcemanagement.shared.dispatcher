package com.ebsco.dispatcher.model;

import org.springframework.security.oauth2.provider.OAuth2Authentication;

import java.io.Serializable;

public class SerializableOAuth2Authentication implements Serializable {

    private transient OAuth2Authentication authentication;

    public SerializableOAuth2Authentication(OAuth2Authentication authentication) {
        this.authentication = authentication;
    }

    public SerializableOAuth2Authentication() {
    }

    public OAuth2Authentication getAuthentication() {
        return authentication;
    }

    public void setAuthentication(OAuth2Authentication authentication) {
        this.authentication = authentication;
    }
}
