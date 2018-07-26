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

public class ClientValidationFilter extends GenericFilterBean {

    @Autowired
    private ClientConfiguration clientConfig;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        System.out.println("START: ClientValidationFilter");

        Map<String, String> queryParameters = DispatcherUtil.mapQueryParam(req);
        Optional<String> clientIdFromRequest = Optional.of(queryParameters.get("client_id"));

        if(!clientIdFromRequest.isPresent()) {
            res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal Server Error");
            return;
        }

        Optional<List<Client>> registeredClients = Optional.of(clientConfig.getRegisteredClients());

        if (!registeredClients.isPresent() || registeredClients.get().isEmpty()) {
            res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal Server Error");
            return;
        }

        Boolean isValidClient = registeredClients.get().stream()
                .anyMatch(client -> client.getId().equals(clientIdFromRequest.get()));

        if(!isValidClient) {
            System.out.println("Invalid Client: "+ clientIdFromRequest);
            res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal Server Error");
            return;
        }


        System.out.println("END: ClientValidationFilter");
        chain.doFilter(request, response);
    }
}
