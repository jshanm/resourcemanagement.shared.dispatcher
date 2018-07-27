package com.ebsco.dispatcher.util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.provider.ClientDetailsService;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author jshanmugam
 */

public class DispatcherUtil {

    @Bean
    public static BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    public static String getUrlFromRequest(HttpServletRequest request) {
        String requestUrl = request.getRequestURL().toString();
        String queryString = request.getQueryString();
        if (queryString != null) {
            requestUrl += "?"+queryString;
        }
        return requestUrl;
    }

    public static String base64Encode(String value) {
        byte[] message = value.getBytes(StandardCharsets.UTF_8);
        String encoded = Base64.getEncoder().encodeToString(message);
        return encoded;
    }

    public static Map<String, String> mapQueryParam(HttpServletRequest request) {

        String[] params = request.getQueryString().toString().split("&");
        Map<String, String> map = new HashMap<String, String>();
        for (String param : params)
        {
            String name = param.split("=")[0];
            String value = param.split("=")[1];
            map.put(name, value);
        }
        return map;
    }

    public static ClientDetailsService getClientDetailsService() throws Exception {

        Map<String, String> additionalInformationForClient1 = new HashMap<>();
        additionalInformationForClient1.put("loginUrl", "http://auth-edc.ebscohost.com/login.aspx");

        return new InMemoryClientDetailsServiceBuilder()
                .withClient("webauth")
                .secret(passwordEncoder().encode("secret"))
                .authorizedGrantTypes("authorization_code")
                .scopes("user_info")
                .autoApprove(true)
                .redirectUris("https://www.getpostman.com/oauth2/callback", "https://resolver.ebscohost.com/openurl")
                .additionalInformation(additionalInformationForClient1)
                .and()
                .withClient("client1")
                .secret(passwordEncoder().encode("secret1"))
                .authorizedGrantTypes("authorization_code")
                .scopes("user_info")
                .autoApprove(true)
                .redirectUris("https://www.getpostman.com/oauth2/callback", "https://resolver.ebscohost.com/openurl")
                .and().build();
    }
}
