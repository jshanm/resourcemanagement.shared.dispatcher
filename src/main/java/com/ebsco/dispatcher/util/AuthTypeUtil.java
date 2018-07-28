package com.ebsco.dispatcher.util;

import javax.servlet.http.HttpServletRequest;

/**
 * @author jshanmugam
 */
public class AuthTypeUtil {

    public static String getAuthTypeFromACR(String acr) {
        //TODO: Implement Logic to translate ACR to AuthType
        if (acr.equalsIgnoreCase("acr_value"))
                return "personalUser";
        return "";
    }

    public static String getAuthType(HttpServletRequest request) {

        if(request.getAttribute("auth_type")!=null) {
            return request.getAttribute("auth_type").toString();
        }

        return "";

    }
}
