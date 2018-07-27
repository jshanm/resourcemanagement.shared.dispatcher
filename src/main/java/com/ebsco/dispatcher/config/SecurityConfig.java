package com.ebsco.dispatcher.config;

import com.ebsco.dispatcher.filters.AcrFilter;
import com.ebsco.dispatcher.filters.ClientValidationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import java.util.Arrays;
import java.util.List;

/**
 * @author jshanmugam
 */

@Configuration
@Order(1)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static List<String> clients = Arrays.asList("client1", "client2");


    @Bean
    public AcrFilter getAcrFilter() throws Exception {
        return new AcrFilter();
    }

    @Bean
    public AuthenticationEntryPoint getAuthenticationEntryPoint() {
        return new LoginEntryPointer("/");
    }

    @Bean
    public ClientValidationFilter getClientValidationFilter() {
        return new ClientValidationFilter();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .addFilterAfter(new OAuth2ClientContextFilter(),
                        AbstractPreAuthenticatedProcessingFilter.class)
                .httpBasic()
                .authenticationEntryPoint(getAuthenticationEntryPoint())
                .and()
                .authorizeRequests()
                .anyRequest().authenticated();

        http.addFilterBefore(getClientValidationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(getAcrFilter(), UsernamePasswordAuthenticationFilter.class);
    }

}
