package com.ebsco.dispatcher.client;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class InMemoryClient {

    private String id;
    private String clientSecret;
    private Set<String> registeredRedirectURI;
    private Set<String> grantType;
    private Set<String> scope;
    private  String loginUrl;
    private Set<String> resourceIds;
    private boolean isSecretRequired;
    private boolean isScoped;
    private Collection<GrantedAuthority> authorities;
    private Integer accessTokenValiditySeconds;
    private Integer refreshTokenValiditySeconds;
    private boolean isAutoApprove;
    private Map<String, Object> getAdditionalInformation;
    private Set<String> authorizedGrantTypes;
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

    public Set<String> getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(Set<String> resourceIds) {
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

    public Set<String> getAuthorizedGrantTypes() {
        return authorizedGrantTypes;
    }

    public void setAuthorizedGrantTypes(Set<String> authorizedGrantTypes) {
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

    public class Signing {
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
    }
}
