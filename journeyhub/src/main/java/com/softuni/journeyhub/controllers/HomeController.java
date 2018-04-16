package com.softuni.journeyhub.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController extends BaseController {

    @GetMapping("/")
    private ModelAndView getHome(ModelAndView modelAndView){
        return this.view("index");
    }
}
