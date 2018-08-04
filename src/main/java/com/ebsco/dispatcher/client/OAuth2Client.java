package com.ebsco.dispatcher.client;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * @author jShanmugam
 */

public class OAuth2Client implements ClientDetails {

    private InMemoryClient client;

    public OAuth2Client(InMemoryClient client) {
        this.client = client;
    }

    @Override
    public String getClientId() {
        return this.client.getId();
    }

    @Override
    public Set<String> getResourceIds() {
        return this.client.getResourceIds();
    }

    @Override
    public boolean isSecretRequired() {
        return this.client.isSecretRequired();
    }

    @Override
    public String getClientSecret() {
        return this.client.getClientSecret();
    }

    @Override
    public boolean isScoped() {
        return this.client.isScoped();
    }

    @Override
    public Set<String> getScope() {
        return this.client.getScope();
    }

    @Override
    public Set<String> getAuthorizedGrantTypes() {
        return this.client.getAuthorizedGrantTypes();
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {
        return this.client.getRegisteredRedirectURI();
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return this.client.getAuthorities();
    }

    @Override
    public Integer getAccessTokenValiditySeconds() {
        return this.client.getAccessTokenValiditySeconds();
    }

    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return this.client.getRefreshTokenValiditySeconds();
    }

    @Override
    public boolean isAutoApprove(String scope) {
        return this.client.isAutoApprove();
    }

    @Override
    public Map<String, Object> getAdditionalInformation() {
        return this.client.getGetAdditionalInformation();
    }

    public Integer getIdTokenValiditySeconds() {
        return this.client.getIdTokenValiditySeconds();
    }

    public InMemoryClient.Signing getSigning() {
        return this.client.getSigning();
    }

    public String getLoginUrl() {
        return this.client.getLoginUrl();
    }
}