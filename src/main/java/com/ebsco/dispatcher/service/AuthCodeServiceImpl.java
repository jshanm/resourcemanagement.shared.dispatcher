package com.ebsco.dispatcher.service;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.code.RandomValueAuthorizationCodeServices;
import org.springframework.stereotype.Service;

@Service
public class AuthCodeServiceImpl extends RandomValueAuthorizationCodeServices {
    @Override
    protected void store(String code, OAuth2Authentication authentication) {
        /**
         * TODO: Decide to save AuthCode or not
         * If not, just generate a Random String for AuthCode
         **/
    }

    @Override
    protected OAuth2Authentication remove(String code) {
        return null;
    }
}
