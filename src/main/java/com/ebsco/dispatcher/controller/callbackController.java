package com.ebsco.dispatcher.controller;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("callback")
public class callbackController {

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
            response.sendRedirect("http://atemppageuselessJPage.com/callback?code=pRkiZN");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
