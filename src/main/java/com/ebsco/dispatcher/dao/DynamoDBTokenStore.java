package com.ebsco.dispatcher.dao;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.ebsco.dispatcher.mapper.OAuth2AccessTokenMapper;
import com.ebsco.dispatcher.mapper.OAuth2AccessTokenMapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;

import java.util.Collection;
import java.util.function.Function;

public class DynamoDBTokenStore extends DatabaseContext implements TokenStore {

    private static String KEY_NAME = "auth_code";
    private final OAuth2AccessTokenMapper oAuth2AccessTokenMapper;

    public DynamoDBTokenStore() {
        oAuth2AccessTokenMapper = new OAuth2AccessTokenMapperImpl();
    }

    @Autowired
    public DynamoDBTokenStore(OAuth2AccessTokenMapper oAuth2AccessTokenMapper, DynamoDB dynamoDB) {
        super(dynamoDB);
        this.oAuth2AccessTokenMapper = oAuth2AccessTokenMapper;
    }

    @Override
    public OAuth2Authentication readAuthentication(OAuth2AccessToken token) {
        return null;
    }

    @Override
    public OAuth2Authentication readAuthentication(String token) {
        return null;
    }

    @Override
    public void storeAccessToken(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {

        String authCode = (String)authentication.getOAuth2Request().getExtensions().get("auth_code");
        String clientId = (String)authentication.getOAuth2Request().getExtensions().get("client_id");

        System.out.println("Creating mapping for parse Item on database");
        Function<OAuth2AccessToken, Item> mapper = oAuth2AccessTokenMapper
                .parse(accessToken, "state")
                .andThen(item -> item.withString("client_id", clientId).withLong("ttl", Long.parseLong("123456")).withPrimaryKey(KEY_NAME, authCode));

        this.create(mapper).apply(accessToken);

    }

    @Override
    public OAuth2AccessToken readAccessToken(String tokenValue) {
        return null;
    }

    @Override
    public void removeAccessToken(OAuth2AccessToken token) {

    }

    @Override
    public void storeRefreshToken(OAuth2RefreshToken refreshToken, OAuth2Authentication authentication) {

    }

    @Override
    public OAuth2RefreshToken readRefreshToken(String tokenValue) {
        return null;
    }

    @Override
    public OAuth2Authentication readAuthenticationForRefreshToken(OAuth2RefreshToken token) {
        return null;
    }

    @Override
    public void removeRefreshToken(OAuth2RefreshToken token) {

    }

    @Override
    public void removeAccessTokenUsingRefreshToken(OAuth2RefreshToken refreshToken) {

    }

    @Override
    public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
        return null;
    }

    @Override
    public Collection<OAuth2AccessToken> findTokensByClientIdAndUserName(String clientId, String userName) {
        return null;
    }

    @Override
    public Collection<OAuth2AccessToken> findTokensByClientId(String clientId) {
        return null;
    }

    /**
     * @return TableName
     */
    @Override
    protected String getTableName() {
        System.out.println("Getting table name from settings");
        return "dispatcher-tokenStore";//TODO: Move this to configuration
    }
}
