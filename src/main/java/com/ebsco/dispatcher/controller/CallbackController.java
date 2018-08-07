package com.ebsco.dispatcher.controller;

import com.ebsco.dispatcher.client.ClientConfiguration;
import com.ebsco.dispatcher.mocks.DatalockerServiceMock;
import com.ebsco.dispatcher.mocks.model.PUAUserInfo;
import com.ebsco.dispatcher.model.UserInformation;
import com.ebsco.dispatcher.service.AuthCodeServiceImpl;
import com.ebsco.dispatcher.service.AuthorizationServerTokenServicesImpl;
import com.ebsco.dispatcher.service.DatalockerService;
import com.ebsco.dispatcher.service.DatalockerServiceImpl;
import com.ebsco.dispatcher.token.TokenService;
import com.ebsco.dispatcher.token.scope.ContextManager;
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
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.*;

/**
 * @author jshanmugam
 *
 */

@RestController
@RequestMapping("callback")
public class CallbackController {


    @Autowired
    private ContextManager contextManager;

    @Autowired
    private AuthCodeServiceImpl authCodeService;

    @Autowired
    public TokenStore inMemoryTokenStore;

   /* @Autowired
    public DatalockerServiceMock datalockerService;*/

    @Bean
    public DatalockerService datalockerService() {
        return new DatalockerServiceMock();
    } //TODO: Change this DatalockerServiceImpl once implemented*/

    @Bean
    public RandomValueStringGenerator randomValueStringGenerator() {
        return new RandomValueStringGenerator();
    }

    @Autowired
    private ClientConfiguration clientConfig;

    @Bean
    private TokenService tokenService() {
        return new TokenService(clientConfig);
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
            throws ServletException, IOException {

        System.out.print("datalockerKey: "+ datalockerKey);

        tokenService().setClaimsSet("resolver", "sub");

        // = datalockerService().getUserInfo(datalockerKey);

        try {
            //TODO: Implement Datalocker service
            //TODO: ganerate Authcode, Token and save them

            this.contextManager.loadUserFromDatalocker(datalockerKey); //Calls the Data locker Service and sets the userinformation

            this.contextManager.loadAuthenticationFromRequest(encodedAuthorizationContext);

            Optional<OAuth2Authentication> authentication = this.contextManager.getAuthentication();

            if(authentication.isPresent()) {
                //AuthCodeServiceImpl authCodeService = new AuthCodeServiceImpl();
                String authCode = authCodeService.createAuthorizationCode(authentication.get());

                System.out.println("CallbackController.getAuthCode" + "Auth Code Generated: "+ authCode);

                response.sendRedirect("http://atemppageuselessJPage.com/callback?code="+authCode); //Store the URL in the application yaml
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("CallbackController.getAuthCode: Exception");
        //response.sendError(HttpServletResponse.SC_UNAUTHORIZED); //TODO: Refine it
    }
}
