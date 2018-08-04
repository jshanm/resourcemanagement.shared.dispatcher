package com.ebsco.dispatcher.token.scope;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ContextManager {

    private ThreadLocal<OAuth2Authentication> authenticationThreadLocal;

    public Optional<OAuth2Authentication> getAuthentication() {
        return Optional.ofNullable(this.getAuthenticationThreadLocal().get());
    }

    private ThreadLocal<OAuth2Authentication> getAuthenticationThreadLocal() {
        if(null == this.authenticationThreadLocal) {
            this.authenticationThreadLocal = new ThreadLocal<>();
        }
        return authenticationThreadLocal;
    }

    public void load(OAuth2Authentication authentication) {

        System.out.println("ContextManager.load: Seeding the EbscoContextManager for request...");

        this.getAuthenticationThreadLocal().set(authentication);
    }

}
