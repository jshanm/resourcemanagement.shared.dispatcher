package com.ebsco.dispatcher.config;

import com.ebsco.dispatcher.dao.DynamoDBTokenStore;
import com.ebsco.dispatcher.service.AuthCodeServiceImpl;
import com.ebsco.dispatcher.service.AuthorizationServerTokenServicesImpl;
import com.ebsco.dispatcher.service.InMemoryClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.Arrays;

/**
 * @author jshanmugam
 */

@Configuration
@EnableAuthorizationServer
@Order(2)
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {


    @Override
    public void configure(
            AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer.tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("123");
        return converter;
    }


    @Bean
    public TokenEnhancer identityTokenEnhancer() {
        return new IdentityTokenEnhancer();
    }

    @Bean
    public TokenStore inMemoryTokenStore() {
        return new InMemoryTokenStore();
    }


    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

        TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
        enhancerChain.setTokenEnhancers(Arrays.asList(identityTokenEnhancer(), accessTokenConverter()));

        endpoints
                //.tokenStore(dynamoDBTokenStore)
                .authorizationCodeServices(new AuthCodeServiceImpl())
                .tokenEnhancer(enhancerChain);
                //.accessTokenConverter(accessTokenConverter());
        ;
    }

    //-=-=-=-=-=--=-=-=-=-=-=-=--=-=-=-=-=-=-=--=-=-=-=-=-=-=--=-=-=-=-=-=-=--=-=-=-=-=-=-=--=-=-=-=-=-=-=--=-=-=-=-=-=-=--=-=-=-=-=-=-=--=-=-=-=-=-=-=--=-=

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("resolver")
                .secret(passwordEncoder.encode("secret"))
                .authorizedGrantTypes("authorization_code")
                .scopes("openid")
                //.autoApprove(true)
                .redirectUris("http://localhost:8082/ui/login","http://localhost:8083/ui2/login", "https://www.getpostman.com/oauth2/callback");
    }
    /**
     *
     * @param clients
     * @throws Exception
     * May not be used because the response to authorize is not handled by this Dispatcher service.
     */
   /* @Override
    public void configureAuthorizationServerSecurityConfigurerClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(getInMemoryClientBuilder());
    }
    */
}

