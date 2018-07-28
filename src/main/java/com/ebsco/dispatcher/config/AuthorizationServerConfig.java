package com.ebsco.dispatcher.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;


/**
 * @author jshanmugam
 */

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    /**
     *
     * @return
     * @throws Exception
     * May not be used because the response to authorize is not handled by this Dispatcher service.
     */
    /*@Bean
    public ClientDetailsService getInMemoryClientBuilder() throws Exception {
        return new InMemoryClientBuilder().build();
    }*/

    @Override
    public void configure(
            AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer.tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");
    }

    /**
     *
     * @param clients
     * @throws Exception
     * May not be used because the response to authorize is not handled by this Dispatcher service.
     */
   /* @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(getInMemoryClientBuilder());
    }
    */
}

