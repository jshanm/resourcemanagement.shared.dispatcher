package com.ebsco.dispatcher.service;

import com.auth0.jwt.Algorithm;
import com.auth0.jwt.JWTVerifier;
import com.ebsco.dispatcher.dao.DynamoDBTokenStore;
import com.ebsco.dispatcher.util.DispatcherUtil;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.SignedJWT;
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
        //TODO: Get Token from the DynamoDB
        //TODO: Decode the token.
        //TODO: Make OAuth2AccessToken from Payload.
        System.out.println("AuthorizationServerTokenServicesImpl.createAccessToken: Retreiving token from Dynamo");
        String token = "eyJraWQiOiJyc2ExIiwiYWxnIjoiUlMyNTYifQ.eyJhdWQiOiJIS0FwVXM3VkY0Iiwicm9sZSI6W10sImFmZmlsaWF0aW9uIjpbeyJjdXN0IjoiZGVtbyIsImdyb3VwIjoibWFpbiJ9LHsiY3VzdCI6IndlYnRlc3RxYSIsImdyb3VwIjoibWFpbiJ9LHsiY3VzdCI6ImF3c2Jhc2ljIiwiZ3JvdXAiOiJtYWluIn1dLCJpc3MiOiJodHRwczpcL1wvYXV0aC5kZXZxYS5lYnNjby56b25lIiwiZXhwIjoxNTMzNTE5Mjc5LCJpYXQiOjE1MzM1MTg5NzksImp0aSI6IjM5ZjM3ZjQ4LTUxZDAtNGQ1Yi1hMjEzLTZiYTJmNzk1ZDYxMyJ9.PVMUxpNU8N9k5EmBc7L1syETV-27JjJaw5WIkFRFkj8GlMOugO_Z1hyR6QwcbSLxpqo7u0BA0TUVGEjizunQ5izYHfQf_3yOd6LgknSIRJRnqJA4jcNzq_IHwOPfCYbfICkiZ1RhittkP-7xPtXM2KFJLyke70HqwkCuhJYhfOPRqcaxMK5rtZIlBgq72a965ADGSYA8tXyKMOl5Tv89-Ci4Rdq983H7VE7VZgjJMGTKSVPYlZ5_kC6EXpXbMZCLsBu7tHyRfpwnD8x_0VnSJRMq3CEGvnwLhdjutpxCTi_mCoLrgn60mxZHRL39ABQi0AnrbXm7PlLgBqPOIBeefA";


        String token1 = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXUyJ9.eyJpc3MiOiJhdXRoMCJ9.AbIJTDMFc7yUa5MhvcP03nJPyCPzZtQcGEp-zWfOkEE";

            String[] split_string = token.split("\\.");
            String base64EncodedHeader = split_string[0];
            String base64EncodedBody = split_string[1];
            String base64EncodedSignature = split_string[2];


            System.out.println("~~~~~~~~~ JWT Body ~~~~~~~");
            String body =  DispatcherUtil.base64Decode(base64EncodedBody);
//            String body = new String(base64Url.decode(base64EncodedBody));
            System.out.println("JWT Body : "+body);

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

    private String generateTokenValue() {

        return "";

    }
}
