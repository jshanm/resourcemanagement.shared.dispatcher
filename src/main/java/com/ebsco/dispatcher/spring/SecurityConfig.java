package com.ebsco.dispatcher.spring;

import com.ebsco.dispatcher.filters.AcrFilter;
import com.ebsco.dispatcher.filters.ClientValidationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author jshanmugam
 */

@Configuration
@Order(1)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

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

        http.antMatcher("/**")
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/login**").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/swagger**").permitAll()
                .antMatchers(HttpMethod.GET, "/callback/**").permitAll()
                .antMatchers("/token/**").permitAll()
                .anyRequest().authenticated()
                .and().exceptionHandling()
                .authenticationEntryPoint(getAuthenticationEntryPoint());

        http.addFilterBefore(getClientValidationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(getAcrFilter(), UsernamePasswordAuthenticationFilter.class);
    }

}
