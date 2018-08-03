package com.ebsco.dispatcher.controller;

import com.ebsco.dispatcher.model.UserInformation;
import com.ebsco.dispatcher.service.AuthorizationServerTokenServicesImpl;
import com.ebsco.dispatcher.service.DatalockerService;
import com.ebsco.dispatcher.service.DatalockerServiceImpl;
import com.ebsco.dispatcher.util.DispatcherUtil;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.common.util.RandomValueStringGenerator;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.*;

@RestController
@RequestMapping("callback")
public class CallbackController {

    @Autowired
    public TokenStore inMemoryTokenStore;

    @Bean
    public DatalockerService datalockerService() {
        return new DatalockerServiceImpl();
    }

    @Bean
    public RandomValueStringGenerator randomValueStringGenerator() {
        return new RandomValueStringGenerator();
    }

    //TODO: Make Swagger configuration
    @RequestMapping(value = "/resolver", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 302, message = "OK"),
            @ApiResponse(code = 400,
                    message = "BAD REQUEST, Invalid Datalocker Key"),
            @ApiResponse(code = 404, message = "NOT FOUND, user not found."),
            @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR") })
    public void getAuthCode(HttpServletResponse response, @RequestParam(value = "datalockerKey") String datalockerKey, @RequestParam(value = "authorization_context") String encodedAuthorizationContext)
            throws ServletException {

        System.out.print("datalockerKey: "+ datalockerKey);

        UserInformation user = datalockerService().getUserInformation(datalockerKey);

        try {
            //TODO: Implement Datalocker service
            //TODO: ganerate Authcode, Token and save them

            String authRequest = DispatcherUtil.base64Decode(encodedAuthorizationContext);

            Map<String, String> queryParameters = DispatcherUtil.splitQuery(new URL(authRequest));

            String authCode = randomValueStringGenerator().generate();

            OAuth2Request oauth2Request = createOAuth2Request(authCode, queryParameters);

            Authentication authenticationObject = createAuthentication(user);

            OAuth2Authentication oAuth2Authentication  = new OAuth2Authentication(oauth2Request, authenticationObject);

            System.out.println("Auth Code Generated: "+ authCode);

            generateAndStoreToken(authCode, oAuth2Authentication);


            response.sendRedirect("http://atemppageuselessJPage.com/callback?code="+authCode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void generateAndStoreToken(String authCode, OAuth2Authentication authentication) {

        AuthorizationServerTokenServices tokenService = new AuthorizationServerTokenServicesImpl();
        tokenService.createAccessToken(authentication);


    }

    public OAuth2Request createOAuth2Request(String authCode, Map<String, String> queryParameters) { //TODO: Change this to private

        GrantedAuthority authority = new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return "authorization_code";
            }
        };
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(authority);

        Set<String> scope = new HashSet<>();
        scope.add(queryParameters.get("scope")); //TODO: Make it a Set, not String

        Set<String> responseTypes = new HashSet<>();
        scope.add(queryParameters.get("response_type")); //TODO: Make it a Set, not String

        String redirectUri = queryParameters.get("redirect_uri"); //TODO: Default the redirect URI if not found in the request

        Map<String, Serializable> extensionProperties = new HashMap<>();
        extensionProperties.put("client_id", queryParameters.get("client_id"));
        extensionProperties.put("auth_code", authCode);


        OAuth2Request oAuth2Request =  new OAuth2Request(queryParameters, "resolver", authorities,  true, scope, null, redirectUri, responseTypes, extensionProperties);
        return oAuth2Request;
    }

    public Authentication createAuthentication(UserInformation userInformation) { //Change this to private

        GrantedAuthority authority = new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return "authorization_code";
            }
        };
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(authority);

        return new Authentication() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return authorities;
            }

            @Override
            public Object getCredentials() {
                return null;
            }

            @Override
            public Object getDetails() {
                return null;
            }

            @Override
            public Object getPrincipal() {
                return userInformation.getUserName();
            }

            @Override
            public boolean isAuthenticated() {
                return true;
            }

            @Override
            public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

            }

            @Override
            public String getName() {
                return userInformation.getName();
            }
        };
    }
}
