package com.ebsco.dispatcher.token;

import com.ebsco.dispatcher.client.ClientConfiguration;
import com.ebsco.dispatcher.client.InMemoryClientService;
import com.ebsco.dispatcher.client.OAuth2Client;
import com.google.common.collect.Lists;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import java.text.ParseException;
import java.util.Date;
import java.util.UUID;

public class TokenService {

    private ClientConfiguration clientConfig;

    @Autowired
    private InMemoryClientService inMemoryClientService;

    @Bean
    private InMemoryClientService clientService() {
        return new InMemoryClientService(clientConfig);
    }

    public TokenService(ClientConfiguration clientConfiguration) {
        this.clientConfig = clientConfiguration;
    }

    public void setClaimsSet(String clientId, String sub) {

        //TODO: Check clientId is not null

        OAuth2Client clientDetails = new InMemoryClientService(this.clientConfig).loadClientByClientId(clientId);

        //IdToken idToken = new IdToken()
        JWTClaimsSet.Builder idClaims = new JWTClaimsSet.Builder();

        //Set issue and expire time
        Date issueTime = new Date(System.currentTimeMillis());
        idClaims.issueTime(issueTime);

        /*if(clientDetails.getIdTokenValiditySeconds() != null){
            Date expiration = new Date(System.currentTimeMillis() + (clientDetails.getIdTokenValiditySeconds() * 1000L));
            idClaims.expirationTime(expiration);
            //TODO: Set the expiration in Id token too.
        }*/

        //TODO: See if we need to set the issuer.
        //idClaims.issuer(configBean.getIssuer());

        idClaims.subject(sub);
        idClaims.audience(Lists.newArrayList(clientDetails.getClientId()));
        idClaims.jwtID(UUID.randomUUID().toString()); // set a random NONCE in the middle of it

        // signed ID token

        JWT idToken;

        /*JWSAlgorithm signingAlg = new JWSAlgorithm(clientDetails.getSigning().getDefaultSigningAlgorithmName());
        String signingKeyId = clientDetails.getSigning().getDefaultSignerKeyId();*/

        JWSAlgorithm signingAlg = new JWSAlgorithm("RSA");
        String signingKeyId = "secret";

        JWSHeader header = new JWSHeader(signingAlg, null, null, null, null, null, null, null, null, null,
                signingKeyId,
                null, null);

        if (signingAlg.equals(JWSAlgorithm.HS256)
                || signingAlg.equals(JWSAlgorithm.HS384)
                || signingAlg.equals(JWSAlgorithm.HS512)) {
            idToken = new SignedJWT(header, idClaims.build());

        } else {
            idClaims.claim("kid", signingKeyId);
            idToken = new SignedJWT(header, idClaims.build());
        }

    }
}
