package com.ebsco.dispatcher.filters;

import com.ebsco.dispatcher.util.AuthTypeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;
import org.thymeleaf.extras.springsecurity4.auth.AuthUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author jshanmugam
 *
 */

public class AcrFilter  extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AcrFilter.class.getName());

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        LOGGER.debug("Start: AcrFilter");
        System.out.println("Start: AcrFilter");

        if (request.getParameter("acr")!=null) {
            //TODO: Implement ACR-->AuthType Mapping
            System.out.println("ACR value for Request: " + request.getParameter("acr"));
            request.setAttribute("auth_type", AuthTypeUtil.getAuthTypeFromACR(request.getParameter("acr"))); //TODO: Move constancs to Constants or Application.yaml
        }
        LOGGER.debug("Start: AcrFilter");
        System.out.println("End: AcrFilter");

        filterChain.doFilter(request, response);
    }
}
