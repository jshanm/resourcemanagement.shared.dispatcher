package com.ebsco.dispatcher.service;

import com.ebsco.dispatcher.config.ClientConfiguration;
import com.ebsco.dispatcher.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

import java.util.*;

//TODO: Utilize this class when we call the Auth0 server to get the client details.

@Service
public class InMemoryClientService implements ClientDetailsService {

    @Bean
    public static BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private ClientConfiguration clientConfig;

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {

        Optional<Client> client = Optional.of(clientConfig.loadClientById(clientId));

        ClientDetails clientDetails = new ClientDetails() {
            @Override
            public String getClientId() {
                return client.get().getId();
            }

            @Override
            public Set<String> getResourceIds() {
                return null;
            }

            @Override
            public boolean isSecretRequired() {
                return false;
            }

            @Override
            public String getClientSecret() {
                return client.get().getClientSecret();
            }

            @Override
            public boolean isScoped() {
                return false;
            }

            @Override
            public Set<String> getScope() {
                return client.get().getScope();
            }

            @Override
            public Set<String> getAuthorizedGrantTypes() {
                return client.get().getGrantType();
            }

            @Override
            public Set<String> getRegisteredRedirectUri() {
                return client.get().getRegisteredRedirectURI();
            }

            @Override
            public Collection<GrantedAuthority> getAuthorities() {
                return null;
            }

            @Override
            public Integer getAccessTokenValiditySeconds() {
                return null;
            }

            @Override
            public Integer getRefreshTokenValiditySeconds() {
                return null;
            }

            @Override
            public boolean isAutoApprove(String scope) {
                return false;
            }

            @Override
            public Map<String, Object> getAdditionalInformation() {
                return null;
            }
        };

        return clientDetails;
    }

}



