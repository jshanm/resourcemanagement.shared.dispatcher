package com.ebsco.dispatcher.model;

import java.util.List;

/**
 * @author jshanmugam
 */
public class Client {

    private String id;
    private String clientSecret;
    private List<String> registeredRedirectURI;
    private List<String> grantType;
    private List<String> scope;
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

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public void setScope(List<String> scope) {
        this.scope = scope;
    }


}
