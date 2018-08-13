package com.ebsco.dispatcher.service;

import com.ebsco.dispatcher.token.scope.ContextManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.util.RandomValueStringGenerator;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthCodeServiceImpl implements AuthorizationCodeServices {

    //@Autowired
    private ContextManager contextManager;

    @Autowired
    public AuthCodeServiceImpl(ContextManager contextManager){
        this.contextManager = contextManager;

    }


    @Bean
    public RandomValueStringGenerator randomValueStringGenerator() {
        return new RandomValueStringGenerator();
    }

    @Bean
    public AuthorizationServerTokenServices tokenService() {
        return new AuthorizationServerTokenServicesImpl();
    }


    @Override
    public String createAuthorizationCode(OAuth2Authentication authentication) {

        System.out.println("AuthCodeServiceImpl.createAuthorizationCode");
        String authCode = randomValueStringGenerator().generate();
        authentication.getOAuth2Request().getExtensions().put("auth_code", authCode);

        //TODO: Call AccessTokenService's custom generateAccessToken Method, get encrypted access token.
        //TODO: Call IdTokenService's custom generateIdToken Method, get encrypted Id token.
        //TODO: Call tokenService's custom save token--> calls DAO layer, insert authCode, authentication, access_token, Id_token

        System.out.println("AuthCodeServiceImpl.createAuthorizationCode: Generating Access token");
        System.out.println("AuthCodeServiceImpl.createAuthorizationCode: Generating ID token");
        System.out.println("AuthCodeServiceImpl.createAuthorizationCode: Saving all tokens to Auth code, returns authCode from DAO");

        return authCode;
    }

    @Override
    public OAuth2Authentication consumeAuthorizationCode(String code) throws InvalidGrantException {

        System.out.println("AuthCodeServiceImpl.consumeAuthorizationCode: " + code);

        this.contextManager.loadUser("12321"); //Calls the Data locker Service and sets the userinformation

        try {
            this.contextManager.loadAuthenticationFromRequest("aHR0cDovL2xvY2FsaG9zdDo4MDgxL29hdXRoL2F1dGhvcml6ZT9jbGllbnRfaWQ9cmVzb2x2ZXImcmVzcG9uc2VfdHlwZT1jb2RlJnJlZGlyZWN0X3VyaT1odHRwczovL3d3dy5nZXRwb3N0bWFuLmNvbS9vYXV0aDIvY2FsbGJhY2smcmVzcG9uc2VfbW9kZT1xdWVyeSZzY29wZT11c2VyX2luZm8mc3RhdGU9cXdlcnQmYWNyPWFjcl92YWx1ZQ");
            return this.contextManager.getAuthentication().get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Optional<OAuth2Authentication> authentication = this.contextManager.getAuthentication();
        return authentication.get();
    }
}
