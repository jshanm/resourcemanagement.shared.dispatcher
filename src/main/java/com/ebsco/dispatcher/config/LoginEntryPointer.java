package com.ebsco.dispatcher.config;

import com.ebsco.dispatcher.util.AuthTypeUtil;
import com.ebsco.dispatcher.util.DispatcherUtil;
import org.codehaus.httpcache4j.uri.URIBuilder;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.net.URI;
import java.net.URISyntaxException;

public class LoginEntryPointer extends LoginUrlAuthenticationEntryPoint {
    /**
     * @param loginFormUrl URL where the login page can be found. Should either be
     *                     relative to the web-app context path (include a leading {@code /}) or an absolute
     *                     URL.
     */
    public LoginEntryPointer(String loginFormUrl) {
        super(loginFormUrl);
    }

    //TODO: Use this method to create custom login URL based on the client\
    //Temporary: Get Login URL from AppProperties and add authorizationContext
    //@Override
    protected String buildRedirectUrlToLoginPage(HttpServletRequest request, HttpServletResponse response,
                                                 AuthenticationException authException) {

        String authorizationContext = DispatcherUtil.base64Encode(DispatcherUtil.getUrlFromRequest(request));
        try {
            //TODO: Get the webAuthLoginUrl from application.yaml
            return addParamsToLoginUrl(request, this.getLoginFormUrl(), authorizationContext).toString();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    private URI addParamsToLoginUrl(HttpServletRequest request, String loginUrl, String authorizationContext) throws URISyntaxException {

        return URIBuilder.fromString(loginUrl)
                .addParameter("authorization_context", authorizationContext)
                .addParameter("authType", AuthTypeUtil.getAuthType(request))
                .toURI(); //TODO: Add the String to application.yaml
    }
}
