package com.ebsco.dispatcher.client;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class InMemoryClient {

    private String id;
    private String clientSecret;
    private List<String> registeredRedirectURI;
    private List<String> grantType;
    private List<String> scope;
    private  String loginUrl;
    private List<String> resourceIds;
    private boolean isSecretRequired;
    private boolean isScoped;
    private Collection<GrantedAuthority> authorities;
    private Integer accessTokenValiditySeconds;
    private Integer refreshTokenValiditySeconds;
    private boolean isAutoApprove;
    private Map<String, Object> getAdditionalInformation;
    private List<String> authorizedGrantTypes;
    private Integer idTokenValiditySeconds;
    private Signing signing;

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

    public List<String> getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(List<String> resourceIds) {
        this.resourceIds = resourceIds;
    }

    public boolean isSecretRequired() {
        return isSecretRequired;
    }

    public void setSecretRequired(boolean secretRequired) {
        isSecretRequired = secretRequired;
    }

    public boolean isScoped() {
        return isScoped;
    }

    public void setScoped(boolean scoped) {
        isScoped = scoped;
    }

    public Collection<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public Integer getAccessTokenValiditySeconds() {
        return accessTokenValiditySeconds;
    }

    public void setAccessTokenValiditySeconds(Integer accessTokenValiditySeconds) {
        this.accessTokenValiditySeconds = accessTokenValiditySeconds;
    }

    public Integer getRefreshTokenValiditySeconds() {
        return refreshTokenValiditySeconds;
    }

    public void setRefreshTokenValiditySeconds(Integer refreshTokenValiditySeconds) {
        this.refreshTokenValiditySeconds = refreshTokenValiditySeconds;
    }

    public boolean isAutoApprove() {
        return isAutoApprove;
    }

    public void setAutoApprove(boolean autoApprove) {
        isAutoApprove = autoApprove;
    }

    public Map<String, Object> getGetAdditionalInformation() {
        return getAdditionalInformation;
    }

    public void setGetAdditionalInformation(Map<String, Object> getAdditionalInformation) {
        this.getAdditionalInformation = getAdditionalInformation;
    }

    public List<String> getAuthorizedGrantTypes() {
        return authorizedGrantTypes;
    }

    public void setAuthorizedGrantTypes(List<String> authorizedGrantTypes) {
        this.authorizedGrantTypes = authorizedGrantTypes;
    }

    public Integer getIdTokenValiditySeconds() {
        return idTokenValiditySeconds;
    }

    public void setIdTokenValiditySeconds(Integer idTokenValiditySeconds) {
        this.idTokenValiditySeconds = idTokenValiditySeconds;
    }

    public Signing getSigning() {
        return signing;
    }

    public void setSigning(Signing signing) {
        this.signing = signing;
    }

    /*private class Signing {
        private String defaultSignerKeyId;
        private String defaultSigningAlgorithmName;

        public String getDefaultSignerKeyId() {
            return defaultSignerKeyId;
        }

        public void setDefaultSignerKeyId(String defaultSignerKeyId) {
            this.defaultSignerKeyId = defaultSignerKeyId;
        }

        public String getDefaultSigningAlgorithmName() {
            return defaultSigningAlgorithmName;
        }

        public void setDefaultSigningAlgorithmName(String defaultSigningAlgorithmName) {
            this.defaultSigningAlgorithmName = defaultSigningAlgorithmName;
        }
    }*/
}
