package com.ebsco.dispatcher.service;

import com.ebsco.dispatcher.util.DispatcherUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.util.RandomValueStringGenerator;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

@Service
public class AuthCodeServiceImpl implements AuthorizationCodeServices {

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
        System.out.print(authentication);
        String authCode = randomValueStringGenerator().generate();
        authentication.getOAuth2Request().getExtensions().put("auth_code", authCode);

        tokenService().createAccessToken(authentication);

        System.out.println("Auth_Code: " + authCode);

        return authCode;
    }

    @Override
    public OAuth2Authentication consumeAuthorizationCode(String code) throws InvalidGrantException {
        System.out.println("consumeAuthorizationCode: "+code);
        try {
            return DispatcherUtil.convertJsonToOAuth2Authentication();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
