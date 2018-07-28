package com.ebsco.dispatcher.config;

import com.ebsco.dispatcher.model.Client;
import com.ebsco.dispatcher.util.AuthTypeUtil;
import com.ebsco.dispatcher.util.DispatcherUtil;
import org.codehaus.httpcache4j.uri.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Optional;

/**
 * @author jshanmugam
 * @see org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint
 * @see org.springframework.security.web.AuthenticationEntryPoint
 */

public class LoginEntryPointer extends LoginUrlAuthenticationEntryPoint {
    /**
     * @param loginFormUrl URL where the login page can be found. Should either be
     *                     relative to the web-app context path (include a leading {@code /}) or an absolute
     *                     URL.
     */
    public LoginEntryPointer(String loginFormUrl) {
        super(loginFormUrl);
    }

    @Autowired
    private ClientConfiguration clientConfig;

    @Override
    protected String buildRedirectUrlToLoginPage(HttpServletRequest request, HttpServletResponse response,
                                                 AuthenticationException authException) {

        System.out.println("Start: LoginEntryPoint: buildRedirectUrlToLoginPage");

        String authorizationContext = DispatcherUtil.base64Encode(DispatcherUtil.getUrlFromRequest(request));
        try {

            String loginUrl = this.determineUrlToUseForThisRequest(request, response, authException);
            return addParamsToLoginUrl(request, loginUrl, authorizationContext).toString();
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

    @Override
    protected String determineUrlToUseForThisRequest(HttpServletRequest request,
                                                     HttpServletResponse response, AuthenticationException exception) {

        Map<String, String> queryParameters = DispatcherUtil.mapQueryParam(request);
        if(!queryParameters.isEmpty()) {
            Optional<String> clientIdFromRequest = Optional.of(queryParameters.get("client_id"));
            if(clientIdFromRequest.isPresent()) {
                Optional<Client> client = Optional.of(clientConfig.loadClientById(clientIdFromRequest.get()));
                return client.get().getLoginUrl();
            } //TODO: Implement "else" logic
        }
        return getLoginFormUrl();
    }
}
