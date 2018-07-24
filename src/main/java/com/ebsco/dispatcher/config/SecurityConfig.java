package com.ebsco.dispatcher.config;

import com.ebsco.dispatcher.filters.AcrFilter;
import com.ebsco.dispatcher.filters.ClientFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AcrFilter getAcrFilter() throws Exception {
        return new AcrFilter();
    }

    @Bean
    public ClientFilter getClientFilter() throws Exception {
        return new ClientFilter();
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new LoginEntryPointer("http://auth.ebsco.zone/api/oidcprovider");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.requestMatchers()
                .antMatchers("/login", "/oauth/authorize")
                .and()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and().exceptionHandling()
                .authenticationEntryPoint(new LoginEntryPointer("http://auth-edc.ebscohost.com/login.aspx")); //TODO: Get the Webauth Login URL from Application.yaml, make env specific

        http.addFilterBefore(getAcrFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(getClientFilter(), AcrFilter.class);

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("john")
                .password(passwordEncoder().encode("123"))
                .roles("USER");
    }


}
