package com.ebsco.dispatcher.token.impl;


import com.ebsco.dispatcher.token.TokenService;
import com.ebsco.dispatcher.token.IdToken;
import com.nimbusds.jwt.JWTClaimsSet;


public class IdTokenService extends TokenService {

    public IdTokenService(String clientId, String sub) {
        super(clientId, sub);
    }

    public IdToken createIdToken(String clientId, String sub) {

        if (this.getClaimsSet()!=null) {

            JWTClaimsSet idTokenJwtClaimsSet = this.getClaimsSet();


        }
        System.out.println("IdTokenServiceImpl.createIdToken: Problem in creating JWT claim set");
        return null;
    }
}
