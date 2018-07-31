package com.ebsco.dispatcher.service;

import com.ebsco.dispatcher.dao.DynamoDBTokenStore;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


@Service
public class AuthorizationServerTokenServicesImpl implements AuthorizationServerTokenServices {

    @Bean
    public DynamoDBTokenStore tokenStore() {
        return new DynamoDBTokenStore();
    }


    @Override
    public OAuth2AccessToken createAccessToken(OAuth2Authentication authentication) throws AuthenticationException {
        System.out.println("Creating Token...");
        OAuth2AccessToken token = generateToken(authentication);
        tokenStore().storeAccessToken(token, authentication);
        return null;
    }

    @Override
    public OAuth2AccessToken refreshAccessToken(String refreshToken, TokenRequest tokenRequest) throws AuthenticationException {
        System.out.println("No Refresh Token Supported...");
        return null;
    }

    @Override
    public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
        System.out.println("Getting Access Token");
        return null;
    }

    private OAuth2AccessToken generateToken(OAuth2Authentication authentication){

        GrantedAuthority authority = new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return "authorization_code";
            }
        };
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(authority);

        Set<String> scope = new HashSet<>();
        scope.add("user_info");

        Set<String> responseTypes = new HashSet<>();
        scope.add("code");

        OAuth2AccessToken token = new OAuth2AccessToken() {
            @Override
            public Map<String, Object> getAdditionalInformation() {
                return null;
            }

            @Override
            public Set<String> getScope() {
                return scope;
            }

            @Override
            public OAuth2RefreshToken getRefreshToken() {
                return null;
            }

            @Override
            public String getTokenType() {
                return null;
            }

            @Override
            public boolean isExpired() {
                return true;
            }

            @Override
            public Date getExpiration() {
                return null;
            }

            @Override
            public int getExpiresIn() {
                return 0;
            }

            @Override
            public String getValue() {
                return "THE_REAL_TOKEN in IMPL";
            }
        };

        return token;

    }
}
