package com.ebsco.dispatcher.model;

import java.util.List;

public class Client {

    private String id;
    private String clientSecret;
    private List<String> registeredRedirectURI;
    private List<String> grantType;
    private List<String> scope;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public List<String> getRegisteredRedirectURI() {
        return registeredRedirectURI;
    }

    public void setRegisteredRedirectURI(List<String> registeredRedirectURI) {
        this.registeredRedirectURI = registeredRedirectURI;
    }

    public List<String> getGrantType() {
        return grantType;
    }

    public void setGrantType(List<String> grantType) {
        this.grantType = grantType;
    }

    public List<String> getScope() {
        return scope;
    }

    public void setScope(List<String> scope) {
        this.scope = scope;
    }
}
