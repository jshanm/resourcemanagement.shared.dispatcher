package com.ebsco.dispatcher.filters;

import com.ebsco.dispatcher.util.DispatcherUtil;
import org.apache.http.protocol.HTTP;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.http.client.utils.URIBuilder;

public class ClientFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        Map<String, String> queryParameters = DispatcherUtil.mapQueryParam(request);

        String clientId = queryParameters.get("client_id").toString();

        if (null==queryParameters.get("redirect_uri")) {
            System.out.println("Invalid redirectUri");
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid redirectUri");
            return;
        }

        if ("clientId1"==queryParameters.get("client_id")) {
            System.out.println("Invalid clientId");
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid clientId");
            return;
        }

        filterChain.doFilter(request, response);

        String redirectUri = queryParameters.get("redirect_uri").toString();
        String state = queryParameters.get("state").toString();

        Set<String> scope = new HashSet<>();
        scope.add(queryParameters.get("scope").toString());

        Set<String> responseTypes = new HashSet<>();
        scope.add(queryParameters.get("response_type").toString());

        ClientDetails clientDetails = new BaseClientDetails();


        try {
            OAuth2Request oauth2Request = new OAuth2Request
                    (queryParameters, clientId, null, false, scope, null, redirectUri, responseTypes, null);
        } catch (Exception e) {

            System.out.println("Exception in Client Details");

            invalidAuthorizeRequest(request, response, filterChain);

            //filterChain.doFilter(request, response);
        }
    }

    private void invalidAuthorizeRequest(HttpServletRequest request, HttpServletResponse response,
                                         FilterChain chain) throws IOException, ServletException {

        SecurityContextHolder.clearContext();

        if (logger.isDebugEnabled()) {
            logger.debug("Authentication request failed: ");
            logger.debug("Updated SecurityContextHolder to contain null Authentication");
        }

        String getStateParam = request.getParameter("state");
        String redirectURL = request.getParameter("redirect_uri");

        try{
            URIBuilder builder = new URIBuilder(redirectURL);
            builder.addParameter("error", "invalid_request_object");
            builder.addParameter("error_description", "Request contains invalid data");
            builder.addParameter("state", getStateParam);

            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid redirectUri");
        } catch (URISyntaxException e) {
            System.out.println("Client "+request.getParameter("client_id") + " sent invalid redirect_uri: " + redirectURL);
        }
    }
}