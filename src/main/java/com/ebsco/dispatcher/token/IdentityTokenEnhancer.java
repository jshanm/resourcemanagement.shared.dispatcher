package com.ebsco.dispatcher.token;

import com.ebsco.dispatcher.client.InMemoryClientService;
import com.ebsco.dispatcher.client.OAuth2Client;
import com.ebsco.dispatcher.tokenTest.AccessToken;
import com.ebsco.dispatcher.tokenTest.jwt.JWTSigningAndValidationService;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class IdentityTokenEnhancer implements TokenEnhancer {

    @Autowired
    private InMemoryClientService clientService;

    /*@Autowired
    private JWTSigningAndValidationService jwtService;*/

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {

        final Map<String, Object> additionalInfo = new HashMap<>();
        additionalInfo.put("id_token", "JWT ID token here"); //TODO: Get the encoded idToken from contextManeger, Set here.
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
        return accessToken;

        //TODO: Get the Id token from Database and set here. Ref: ConnectTokenEnhancer.java

        /*AccessToken token = (AccessToken) accessToken;
        OAuth2Request originalAuthRequest = authentication.getOAuth2Request();

        String clientId = originalAuthRequest.getClientId();
         OAuth2Client client = clientService.loadClientByClientId(clientId);

        JWTClaimsSet.Builder builder = new JWTClaimsSet.Builder()
                .claim("azp", clientId)
                .issuer("dispatcher") // TODO: Get from some config.
                .issueTime(new Date())
                .expirationTime(token.getExpiration())
                .subject(authentication.getName())
                .jwtID(UUID.randomUUID().toString()); // set a random NONCE in the middle of it

        String audience = (String) authentication.getOAuth2Request().getExtensions().get("aud");
        if (!Strings.isNullOrEmpty(audience)) {
            builder.audience(Lists.newArrayList(audience));
        }

        addCustomAccessTokenClaims(builder, token, authentication);

        JWTClaimsSet claims = builder.build();

        JWSAlgorithm signingAlg = new JWSAlgorithm(client.getSigning().getDefaultSigningAlgorithmName());
        JWSHeader header = new JWSHeader(signingAlg, null, null, null, null, null, null, null, null, null,
                client.getSigning().getDefaultSignerKeyId(),
                null, null);
        SignedJWT signed = new SignedJWT(header, claims);

        jwtService.signJwt(signed);

        token.setJwt(signed);

        *//**
         * Authorization request scope MUST include "openid" in OIDC, but access token request
         * may or may not include the scope parameter. As long as the AuthorizationRequest
         * has the proper scope, we can consider this a valid OpenID Connect request. Otherwise,
         * we consider it to be a vanilla OAuth2 request.
         *
         * Also, there must be a user authentication involved in the request for it to be considered
         * OIDC and not OAuth, so we check for that as well.
         *//*
        if (originalAuthRequest.getScope().contains(SystemScopeService.OPENID_SCOPE)
                && !authentication.isClientOnly()) {

            String username = authentication.getName();
            UserInfo userInfo = userInfoService.getByUsernameAndClientId(username, clientId);

            if (userInfo != null) {

                JWT idToken = connectTokenService.createIdToken(client,
                        originalAuthRequest, claims.getIssueTime(),
                        userInfo.getSub(), token);

                // attach the id token to the parent access token
                token.setIdToken(idToken);
            } else {
                // can't create an id token if we can't find the user
                logger.warn("Request for ID token when no user is present.");
            }
        }

        return token;*/
    }

    private void addCustomAccessTokenClaims(JWTClaimsSet.Builder builder, AccessToken token, OAuth2Authentication authentication) {

    }
}
