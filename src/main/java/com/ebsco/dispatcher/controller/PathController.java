package com.ebsco.dispatcher.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PathController {

    @RequestMapping("/login")
    public String list() {
        System.out.print("INSIDE CONTROLLER");
        return "loginPage";
    }
}
