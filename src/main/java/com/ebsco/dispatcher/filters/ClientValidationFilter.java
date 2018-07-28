package com.ebsco.dispatcher.filters;

import com.ebsco.dispatcher.config.ClientConfiguration;
import com.ebsco.dispatcher.model.Client;
import com.ebsco.dispatcher.util.DispatcherUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * @author jshanmugam
 */

public class ClientValidationFilter extends GenericFilterBean {

    @Autowired
    private ClientConfiguration clientConfig;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        System.out.println("START: ClientValidationFilter");

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        // skip everything that's not an authorize URL
        if (!DispatcherUtil.getPath(req).startsWith("/authorize")) {
            chain.doFilter(req, res);
            return;
        }

        //Continue if it is /authorize
        doClientValidation(req, res, chain);
    }

    private void doClientValidation(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {

        Map<String, String> queryParameters = DispatcherUtil.mapQueryParam(req);

        if (queryParameters.isEmpty()) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Internal Server Error");//TODO:Send OpenID standard HTTP and message
            return;
        }
        Optional<String> clientIdFromRequest = Optional.of(queryParameters.get("client_id"));


        Optional<String> redirectUriFromRequest = Optional.of(queryParameters.get("redirect_uri"));

        //When there is no client Id in the request, error out.
        if(!clientIdFromRequest.isPresent()) {
            res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal Server Error");//TODO:Send OpenID standard HTTP and message
            return;
        }

        //Get the registered clients from the Configuration
        Optional<List<Client>> registeredClients = Optional.of(clientConfig.getRegisteredClients());

        //Error out if there is a problem in
        if (!registeredClients.isPresent() || registeredClients.get().isEmpty()) {
            res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal Server Error");//TODO:Send OpenID standard HTTP and message
            return;
        }

        Boolean isValidClient = registeredClients.get().stream()
                .anyMatch(client -> client.getId().equals(clientIdFromRequest.get()));

        if(!isValidClient) {
            System.out.println("Invalid Client: "+ clientIdFromRequest);
            res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal Server Error");//TODO:Send OpenID standard HTTP and message
            return;
        }

        //Load the needed client from the Configuration.
        Optional<Client> client = Optional.of(clientConfig.loadClientById(clientIdFromRequest.get()));

        //Get the registered redirectURIs for the client.
        Optional<Set<String>> registeredRedirectURIs = Optional.of(client.get().getRegisteredRedirectURI());

        Boolean isRedirectUriValid = registeredRedirectURIs.get().stream()
                .anyMatch(r -> r.equalsIgnoreCase(redirectUriFromRequest.get()));


        //Check if redirect URI is available, but not matches with the registered.
        if (redirectUriFromRequest.isPresent() && !isRedirectUriValid) {

            System.out.println("Invalid redirectUri");
            res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid redirectUri");//TODO:Send OpenID standard HTTP and message
            return;
        }

        System.out.println("END: ClientValidationFilter");
        chain.doFilter(req, res);
    }


    }
