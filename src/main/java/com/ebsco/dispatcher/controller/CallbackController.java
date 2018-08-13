package com.ebsco.dispatcher.controller;

import com.ebsco.dispatcher.client.ClientConfiguration;
import com.ebsco.dispatcher.client.InMemoryClientService;
import com.ebsco.dispatcher.mocks.DatalockerServiceMock;
import com.ebsco.dispatcher.mocks.model.DatalockerValues;
import com.ebsco.dispatcher.service.AuthCodeServiceImpl;
import com.ebsco.dispatcher.service.DatalockerService;
import com.ebsco.dispatcher.token.TokenService;
import com.ebsco.dispatcher.token.scope.ContextManager;
import com.ebsco.dispatcher.util.DispatcherUtil;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.common.util.RandomValueStringGenerator;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * @author jshanmugam
 *
 */

@RestController
@RequestMapping("callback")
public class CallbackController {

    private final static Logger LOGGER = LoggerFactory.getLogger(CallbackController.class.getName());


    @Autowired
    private ContextManager contextManager;

    @Autowired
    private AuthCodeServiceImpl authCodeService;

    @Autowired
    public TokenStore inMemoryTokenStore;

    @Autowired
    public ClientConfiguration clientConfiguration;


   /* @Autowired
    public InMemoryClientService inMemoryClientService;*/

    @Bean
    public DatalockerService datalockerService() {
        return new DatalockerServiceMock();
    } //TODO: Change this DatalockerServiceImpl once implemented

    @Bean
    public InMemoryClientService clientDetailsService() {
        return new InMemoryClientService(clientConfiguration);
    }


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
    public void getAuthCode(HttpServletResponse response, @RequestParam(value = "key") String key, @RequestParam(value = "key") String site, @RequestParam(value = "authorization_context") String encodedAuthorizationContext)
            throws ServletException, IOException {

        System.out.print("datalockerKey: "+ key);
        System.out.print("Site: "+ site);

        tokenService().setClaimsSet("resolver", "sub");


        try {
            //TODO: Implement Datalocker service
            //TODO: ganerate Authcode, Token and save them

            this.contextManager.loadDatalockerValues(key, site); //Calls the Data locker Service and sets the datalocker values.
            //TODO: Need to look what the site value is

            //Below is the logic to decide whether to make call to personalUserInfo
            //TODO: IMPORTANT. Avoid nested if statements below.
            if(this.contextManager.getDatalockerValues().isPresent()) {
                //Implement logic whether to do getPersonaluserInfoByPuaid()
                DatalockerValues datalockerValues = this.contextManager.getDatalockerValues().get();
                if(datalockerValues.getUniqueUserId()!=null || datalockerValues.getUniqueUserId() == "0") {
                    if(DispatcherUtil.convertStringToInt(datalockerValues.getUniqueUserId()).isPresent()) {
                        Integer uniqueUserId = DispatcherUtil.convertStringToInt(datalockerValues.getUniqueUserId()).get();
                        if(uniqueUserId > 0) {
                            //This is a valid puaId,
                            //Call contextManager.loadUserInfo.
                            LOGGER.info("Getting PersonalUserInformaiton for puaid {}", DispatcherUtil.convertStringToInt(datalockerValues.getUniqueUserId()).get());
                            System.out.println("CallbackController.getAuthCode: Getting PersonalUserInformaiton for puaid: " + DispatcherUtil.convertStringToInt(datalockerValues.getUniqueUserId()).get());
                        } else {
                            //This is institutional Authentication.
                            LOGGER.info("Institutional Authenticated user with uniqueuserId {}", DispatcherUtil.convertStringToInt(datalockerValues.getUniqueUserId()).get());
                            System.out.println("CallbackController.getAuthCode: Institutional Authenticated user with uniqueuserId" + DispatcherUtil.convertStringToInt(datalockerValues.getUniqueUserId()).get());
                        }
                    } else {
                        LOGGER.error("Invalid UniqueUserId from Datalocker");
                        System.out.println("CallbackController.getAuthCode: Invalid UniqueUserId from Datalocker");
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                    }
                } else {
                    LOGGER.error("Invalid UniqueUserId from Datalocker");
                    System.out.println("CallbackController.getAuthCode: Invalid UniqueUserId from Datalocker");
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                }
            } else {
                LOGGER.error("Invalid UniqueUserId from Datalocker");
                System.out.println("CallbackController.getAuthCode: Invalid UniqueUserId from Datalocker");
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            }

            this.contextManager.loadAuthenticationFromRequest(encodedAuthorizationContext);

            Optional<OAuth2Authentication> authentication = this.contextManager.getAuthentication();

            if(authentication.isPresent()) {
                //AuthCodeServiceImpl authCodeService = new AuthCodeServiceImpl();
                String authCode = authCodeService.createAuthorizationCode(authentication.get());

                System.out.println("CallbackController.getAuthCode" + "Auth Code Generated: "+ authCode);

                response.sendRedirect("http://atemppageuselessJPage.com/callback?code="+authCode); //Store the URL in the application yaml
            }

        } catch (Exception e) { //TODO: Catch proper Exception
            e.printStackTrace();
        }
    }
}
