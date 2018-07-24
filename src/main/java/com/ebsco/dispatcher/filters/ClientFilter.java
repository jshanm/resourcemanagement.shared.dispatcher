package com.ebsco.dispatcher.filters;

import com.ebsco.dispatcher.util.DispatcherUtil;
import org.springframework.security.oauth2.provider.OAuth2Request;
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
        String redirectUri = queryParameters.get("redirect_uri").toString();
        String state = queryParameters.get("state").toString();

        Set<String> scope = new HashSet<>();
        scope.add(queryParameters.get("scope").toString());

        Set<String> responseTypes = new HashSet<>();
        scope.add(queryParameters.get("response_type").toString());

        try {
            OAuth2Request oauth2Request = new OAuth2Request
                    (queryParameters, clientId, null, false, scope, null, redirectUri, responseTypes, null);
        } catch (Exception e) {

            try{
                URIBuilder builder = new URIBuilder(redirectUri);
                builder.addParameter("error", "invalid_request_object");
                builder.addParameter("error_description", "Request contains invalid data");
                builder.addParameter("state", state);

                response.sendRedirect(builder.toString());

            } catch (URISyntaxException uriException) {
                System.out.println("Client: "+clientId + " sent invalid redirect_uri " + redirectUri); //TODO: Add logger and exception
            }
        }

        filterChain.doFilter(request, response);
    }
}