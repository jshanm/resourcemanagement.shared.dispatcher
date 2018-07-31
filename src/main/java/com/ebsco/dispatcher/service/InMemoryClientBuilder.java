package com.ebsco.dispatcher.service;

import com.ebsco.dispatcher.config.ClientConfiguration;
import com.ebsco.dispatcher.model.Client;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;

import java.util.*;

/**
 * @author jshanmugam
 */

@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class InMemoryClientBuilder extends InMemoryClientDetailsServiceBuilder {

    @Bean
    private ClientConfiguration getClientConfig() {
        return new ClientConfiguration();
    }

    @Override
    protected ClientDetailsService performBuild() {

        //Get the registered clients from the Configuration
        Optional<List<Client>> registeredClients = Optional.of(getClientConfig().getRegisteredClients());

        //Add ClientDetails from ClientConfiguration
        registeredClients.get().stream()
                .forEach(c -> {
                    this.addClient(c.getId(), mapClientDetails(c));
                });
        try {
            return this.build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private ClientDetails mapClientDetails(Client client){

        ClientDetails clientDetails = new ClientDetails() {
            @Override
            public String getClientId() {
                return client.getId();
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
                return client.getClientSecret();
            }

            @Override
            public boolean isScoped() {
                return false;
            }

            @Override
            public Set<String> getScope() {
                return client.getScope();
            }

            @Override
            public Set<String> getAuthorizedGrantTypes() {
                return client.getGrantType();
            }

            @Override
            public Set<String> getRegisteredRedirectUri() {
                return client.getRegisteredRedirectURI();
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
