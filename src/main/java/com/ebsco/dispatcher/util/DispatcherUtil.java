package com.ebsco.dispatcher.util;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class DispatcherUtil {

    public static String getUrlFromRequest(HttpServletRequest request) {
        String requestUrl = request.getRequestURL().toString();
        String queryString = request.getQueryString();   // d=789
        if (queryString != null) {
            requestUrl += "?"+queryString;
        }
        System.out.println("RequestUrl: " + requestUrl);
        return requestUrl;
    }

    public static String base64Encode(String value) {
        byte[] message = value.getBytes(StandardCharsets.UTF_8);
        String encoded = Base64.getEncoder().encodeToString(message);
        return encoded;
    }

}
