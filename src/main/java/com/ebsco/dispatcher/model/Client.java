package com.ebsco.dispatcher.model;

import java.util.List;
import java.util.Set;

/**
 * @author jshanmugam
 */
public class Client {

    private String id;
    private String clientSecret;
    private Set<String> registeredRedirectURI;
    private Set<String> grantType;
    private Set<String> scope;
    private  String loginUrl;

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

    public Set<String> getRegisteredRedirectURI() {
        return registeredRedirectURI;
    }

    public void setRegisteredRedirectURI(Set<String> registeredRedirectURI) {
        this.registeredRedirectURI = registeredRedirectURI;
    }

    public Set<String> getGrantType() {
        return grantType;
    }

    public void setGrantType(Set<String> grantType) {
        this.grantType = grantType;
    }

    public Set<String> getScope() {
        return scope;
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public void setScope(Set<String> scope) {
        this.scope = scope;
    }


}
