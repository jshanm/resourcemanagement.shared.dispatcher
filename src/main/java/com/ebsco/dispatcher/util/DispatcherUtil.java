package com.ebsco.dispatcher.util;


import com.amazonaws.util.NumberUtils;
import com.ebsco.dispatcher.controller.CallbackController;
import com.ebsco.dispatcher.mocks.model.PUAUserInfo;
import com.ebsco.dispatcher.model.SerializableOAuth2Authentication;
import com.ebsco.dispatcher.model.UserInformation;
import com.ebsco.dispatcher.service.DatalockerService;
import com.ebsco.dispatcher.service.DatalockerServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.oauth2.sdk.util.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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

    public static String base64Decode(String value) {
        System.out.println("DispatcherUtil.base64Decode: Decoding :" + value);
        return new String(org.springframework.security.crypto.codec.Base64.decode(value.getBytes()));
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

    public static Map<String, String> splitQuery(URL url) throws UnsupportedEncodingException {
        Map<String, String> query_pairs = new LinkedHashMap<String, String>();
        String query = url.getQuery();
        String[] pairs = query.split("&");
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
        }
        return query_pairs;
    }

    public static ByteBuffer createBuffer(OAuth2Authentication input) throws IOException {

        //TODO: Make it beautiful - https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/JavaDocumentAPIBinaryTypeExample.html

        SerializableOAuth2Authentication authentication = new SerializableOAuth2Authentication(input);

        byte[] compressedBytes = convertToBytes(authentication);


        ByteBuffer buffer = ByteBuffer.allocate(compressedBytes.length);
        buffer.put(compressedBytes, 0, compressedBytes.length);
        buffer.position(0); // Important: reset the position of the ByteBuffer
        // to the beginning
        return buffer;
    }

    private static byte[] convertToBytes(Object object) throws IOException {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutput out = new ObjectOutputStream(bos)) {
            out.writeObject(object);
            return bos.toByteArray();
        }
    }

    public static OAuth2Authentication convertJsonToOAuth2Authentication() throws MalformedURLException, UnsupportedEncodingException {

        Optional<PUAUserInfo> userInfo = new DatalockerServiceImpl().getUserInfo("124443");

        String encodedAuthorizationContext = "aHR0cDovL2xvY2FsaG9zdDo4MDgxL29hdXRoL2F1dGhvcml6ZT9jbGllbnRfaWQ9d2ViYXV0aCZyZXNwb25zZV90eXBlPWNvZGUmcmVkaXJlY3RfdXJpPWh0dHBzOi8vd3d3LmdldHBvc3RtYW4uY29tL29hdXRoMi9jYWxsYmFjayZyZXNwb25zZV9tb2RlPXF1ZXJ5JnNjb3BlPXVzZXJfaW5mbyZzdGF0ZT1xd2VydCZhY3I9YWNyX3ZhbHVl";

        String authRequest = DispatcherUtil.base64Decode(encodedAuthorizationContext);

        Map<String, String> queryParameters = DispatcherUtil.splitQuery(new URL(authRequest));

        String authCode = "qwert";

        //OAuth2Request oauth2Request = new CallbackController().createOAuth2Request(authCode, queryParameters);

        //Authentication authenticationObject = new CallbackController().createAuthentication(user);

        //OAuth2Authentication oAuth2Authentication  = new OAuth2Authentication(oauth2Request, authenticationObject);

        return null;
    }

    public static Optional<Integer> convertStringToInt(String string) {
        return Optional.of(NumberUtils.tryParseInt(string));
    }
}
