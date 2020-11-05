package com.konantech.spring.controller.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class testController {
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String custom() {
        return "test/test";
    }

}
