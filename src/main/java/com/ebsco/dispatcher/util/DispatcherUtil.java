package com.ebsco.dispatcher.util;


import com.nimbusds.oauth2.sdk.util.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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

        if(request.getQueryString()!=null) {
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

        return new HashMap<>();
    }

    public static String getPath(final HttpServletRequest request){
        if(StringUtils.isNotBlank(request.getServletPath())){
            return request.getServletPath();
        }else if(StringUtils.isNotBlank(request.getPathInfo())){
            return request.getPathInfo();
        }
        return "";
    }
}
