package com.ebsco.dispatcher;

import java.util.Base64;

public class TestAndDelete {

    public static void main(String[] args) {
        String string = "aHR0cDovL2xvY2FsaG9zdDo4MDgxL29hdXRoL2F1dGhvcml6ZT9jbGllbnRfaWQ9d2ViYXV0aCZyZXNwb25zZV90eXBlPWNvZGUmcmVkaXJlY3RfdXJpPWh0dHBzOi8vd3d3LmdldHBvc3RtYW4uY29tL29hdXRoMi9jYWxsYmFjayZyZXNwb25zZV9tb2RlPXF1ZXJ5JnNjb3BlPXVzZXJfaW5mbyZzdGF0ZT1xd2VydCZhY3I9YWNyX3ZhbHVl";
        byte[] decodedBytes = Base64.getDecoder().decode("aHR0cDovL2xvY2FsaG9zdDo4MDgxL29hdXRoL2F1dGhvcml6ZT9jbGllbnRfaWQ9d2ViYXV0aCZyZXNwb25zZV90eXBlPWNvZGUmcmVkaXJlY3RfdXJpPWh0dHBzOi8vd3d3LmdldHBvc3RtYW4uY29tL29hdXRoMi9jYWxsYmFjayZyZXNwb25zZV9tb2RlPXF1ZXJ5JnNjb3BlPXVzZXJfaW5mbyZzdGF0ZT1xd2VydCZhY3I9YWNyX3ZhbHVl");
        System.out.println(decodedBytes.toString());

        System.out.println(decodeBase64(string));
    }

    public static String decodeBase64(final String string){
        return new String(org.springframework.security.crypto.codec.Base64.decode(string.getBytes()));
    }

    public void testJSON(){
        //String jsonString = "{\\\"authorities\\\":[{\\\"authority\\\":\\\"authorization_code\\\"}],\\\"details\\\":null,\\\"authenticated\\\":true,\\\"userAuthentication\\\":{\\\"name\\\":null,\\\"principal\\\":\\\"JShan\\\",\\\"authenticated\\\":true,\\\"credentials\\\":null,\\\"authorities\\\":[{\\\"authority\\\":\\\"authorization_code\\\"}],\\\"details\\\":null},\\\"oauth2Request\\\":{\\\"clientId\\\":\\\"resolver\\\",\\\"scope\\\":[\\\"code\\\",\\\"user_info\\\"],\\\"requestParameters\\\":{\\\"acr\\\":\\\"acr_value\\\",\\\"scope\\\":\\\"user_info\\\",\\\"response_type\\\":\\\"code\\\",\\\"redirect_uri\\\":\\\"https://www.getpostman.com/oauth2/callback\\\",\\\"state\\\":\\\"qwert\\\",\\\"client_id\\\":\\\"webauth\\\",\\\"response_mode\\\":\\\"query\\\"},\\\"resourceIds\\\":[],\\\"authorities\\\":[{\\\"authority\\\":\\\"authorization_code\\\"}],\\\"approved\\\":true,\\\"refresh\\\":false,\\\"redirectUri\\\":\\\"https://www.getpostman.com/oauth2/callback\\\",\\\"responseTypes\\\":[],\\\"extensions\\\":{\\\"client_id\\\":\\\"webauth\\\",\\\"auth_code\\\":\\\"ExAWjo\\\"},\\\"refreshTokenRequest\\\":null,\\\"grantType\\\":null},\\\"principal\\\":\\\"JShan\\\",\\\"credentials\\\":\\\"\\\",\\\"clientOnly\\\":false,\\\"name\\\":\\\"JShan\\\"}"


    }




}
