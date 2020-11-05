package com.konantech.spring.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MonitoringController {


   @RequestMapping(value = "/monitoring", method = RequestMethod.GET)
    public String monitoring(ModelMap modelMap, HttpServletRequest request) throws Exception {
        return "monitoring/monitoring";
    }


}