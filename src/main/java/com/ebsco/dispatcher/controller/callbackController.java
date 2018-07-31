package com.ebsco.dispatcher.controller;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("callback")
public class callbackController {

    @Autowired
    public TokenStore inMemoryTokenStore;

    //TODO: Make Swagger configuration
    @RequestMapping(value = "/webauth", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 302, message = "OK"),
            @ApiResponse(code = 400,
                    message = "BAD REQUEST, Invalid Datalocker Key"),
            @ApiResponse(code = 404, message = "NOT FOUND, user not found."),
            @ApiResponse(code = 500, message = "INTERNAL SERVER ERROR") })
    public void getAuthCode(HttpServletResponse response, @RequestParam(value = "datalockerKey") String datalockerKey)
            throws ServletException {

        System.out.print("datalockerKey: "+ datalockerKey);

        try {
            //TODO: Implement Datalocker Service
            //TODO: ganerate Authcode, Token and store them


            OAuth2Request oauth2Request = createOAuth2Request();

            Authentication authenticationObject = createAuthentication();

            OAuth2Authentication authentication  = new OAuth2Authentication(oauth2Request, authenticationObject);

            AuthorizationCodeServices codeServices = new InMemoryAuthorizationCodeServices();
            String authCode = codeServices.createAuthorizationCode(authentication);

            System.out.println("Auth Code Generated: "+ authCode);

            generateAndStoreToken(authCode, authentication);


            System.out.println("-=-=findTokensByClientId:-=-= " + inMemoryTokenStore.findTokensByClientId("webauth"));


            response.sendRedirect("http://atemppageuselessJPage.com/callback?code="+authCode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void generateAndStoreToken(String authCode, OAuth2Authentication authentication) {

        //OAuth2AccessToken token, OAuth2Authentication authentication

        GrantedAuthority authority = new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return "authorization_code";
            }
        };
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(authority);

        Set<String> scope = new HashSet<>();
        scope.add("user_info");

        Set<String> responseTypes = new HashSet<>();
        scope.add("code");



        OAuth2AccessToken token = new OAuth2AccessToken() {
            @Override
            public Map<String, Object> getAdditionalInformation() {
                return null;
            }

            @Override
            public Set<String> getScope() {
                return scope;
            }

            @Override
            public OAuth2RefreshToken getRefreshToken() {
                return null;
            }

            @Override
            public String getTokenType() {
                return null;
            }

            @Override
            public boolean isExpired() {
                return true;
            }

            @Override
            public Date getExpiration() {
                return null;
            }

            @Override
            public int getExpiresIn() {
                return 0;
            }

            @Override
            public String getValue() {
                return "THE_REAL_TOKEN";
            }
        };


        inMemoryTokenStore.storeAccessToken(token, authentication);
    }

    private OAuth2Request createOAuth2Request() {

        Map<String, String> requestParameters= new HashMap<>();
        requestParameters.put("client_id", "webauth");
        requestParameters.put("response_type", "code");
        requestParameters.put("redirect_uri", "https://www.getpostman.com/oauth2/callback");
        requestParameters.put("response_mode", "query");
        requestParameters.put("scope", "user_info");
        requestParameters.put("state", "qwerty");

        GrantedAuthority authority = new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return "authorization_code";
            }
        };
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(authority);

        Set<String> scope = new HashSet<>();
        scope.add("user_info");

        Set<String> responseTypes = new HashSet<>();
        scope.add("code");


        OAuth2Request oAuth2Request =  new OAuth2Request(requestParameters, "webauth", authorities,  true, scope, null, "https://www.getpostman.com/oauth2/callback", responseTypes, null);
        return oAuth2Request;
    }

    private Authentication createAuthentication() {

        GrantedAuthority authority = new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return "authorization_code";
            }
        };
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(authority);

        Authentication authentication = new Authentication() {
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
                return "userNameJay";
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
                return "Jay Shan";
            }
        };

        return authentication;

    }
}
