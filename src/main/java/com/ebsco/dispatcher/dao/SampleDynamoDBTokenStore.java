package com.ebsco.dispatcher.dao;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;

import java.util.Collection;

public class SampleDynamoDBTokenStore implements TokenStore {

    @Override
    public OAuth2Authentication readAuthentication(OAuth2AccessToken token) {
        System.out.print("SampleDynamoDBTokenStore:readAuthentication");
        return null;
    }

    @Override
    public OAuth2Authentication readAuthentication(String token) {
        System.out.print("SampleDynamoDBTokenStore:readAuthentication");
        return null;
    }

    @Override
    public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
        System.out.print("SampleDynamoDBTokenStore:readAuthentication");

    }

    @Override
    public OAuth2AccessToken readAccessToken(String tokenValue) {
        System.out.print("SampleDynamoDBTokenStore:readAuthentication");
        return null;
    }

    @Override
    public void removeAccessToken(OAuth2AccessToken token) {
        System.out.print("SampleDynamoDBTokenStore:readAuthentication");

    }

    @Override
    public void storeRefreshToken(OAuth2RefreshToken refreshToken, OAuth2Authentication authentication) {
        System.out.print("SampleDynamoDBTokenStore:readAuthentication");

    }

    @Override
    public OAuth2RefreshToken readRefreshToken(String tokenValue) {
        System.out.print("SampleDynamoDBTokenStore:readAuthentication");
        return null;
    }

    @Override
    public OAuth2Authentication readAuthenticationForRefreshToken(OAuth2RefreshToken token) {
        System.out.print("SampleDynamoDBTokenStore:readAuthentication");
        return null;
    }

    @Override
    public void removeRefreshToken(OAuth2RefreshToken token) {
        System.out.print("SampleDynamoDBTokenStore:readAuthentication");

    }

    @Override
    public void removeAccessTokenUsingRefreshToken(OAuth2RefreshToken refreshToken) {
        System.out.print("SampleDynamoDBTokenStore:readAuthentication");

    }

    @Override
    public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
        System.out.print("SampleDynamoDBTokenStore:readAuthentication");
        return null;
    }

    @Override
    public Collection<OAuth2AccessToken> findTokensByClientIdAndUserName(String clientId, String userName) {
        System.out.print("SampleDynamoDBTokenStore:readAuthentication");
        return null;
    }

    @Override
    public Collection<OAuth2AccessToken> findTokensByClientId(String clientId) {
        System.out.print("SampleDynamoDBTokenStore:readAuthentication");
        return null;
    }
}
