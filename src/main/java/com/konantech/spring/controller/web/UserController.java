package com.konantech.spring.controller.web;

import com.konantech.spring.domain.user.User;
import com.konantech.spring.security.SHAPasswordEncoder;
import com.konantech.spring.service.AuthService;
import com.konantech.spring.util.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Controller
public class UserController {
    @Autowired
    AuthService authService;

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String defaultActon(HttpServletRequest request) {
        return "redirect:" + RequestUtils.getRedirectUrl(request, "/user/login");
    }

    @RequestMapping(value = "/user/login", method = RequestMethod.GET)
    public String login() {
        return "user/login";
    }

    @RequestMapping(value = "/user/logout", method = RequestMethod.GET)
    public void logout() {
    }

    @RequestMapping(value = "/user/idDuplChk", method = RequestMethod.POST)
    @ResponseBody
    public Boolean getUserInfo(@RequestParam(value = "username", defaultValue = "") String username) throws Exception {
        User user = new User();
        Boolean result;
        user.setUserName(username);
        List<Map<String, Object>> userList = authService.getDuplUserChk(user);
        if (userList != null && userList.size() > 0) {
            result = false;
        } else {
            result = true;
        }
        return result;
    }


    @RequestMapping(value = "/user/getPutUser", method = RequestMethod.GET)
    public String getPutUser(Model model) {
        List roleList = authService.getRoleList();
        model.addAttribute("roleList", roleList);
        return "user/_user_create";
    }

    @RequestMapping(value = "/user/putUser", method = RequestMethod.POST)
    @ResponseBody
    public Boolean putUser(@RequestParam(value = "username", defaultValue = "") String username,
                           @RequestParam(value = "password1", defaultValue = "") String password,
                           @RequestParam(value = "name", defaultValue = "") String name,
                           @RequestParam(value = "userAuth", defaultValue = "") String userAuth
    ) throws Exception {
        Map map = new TreeMap();
        map.put("username", username);
        SHAPasswordEncoder passwordEncoder = new SHAPasswordEncoder(512);
        map.put("password", passwordEncoder.encode(password));
        map.put("realname", name);
        map.put("userAuth", userAuth);

        int count = authService.getPutUser(map);
        Boolean result;
        if (count > 0) {
            result = true;
        } else {
            result = false;
        }
        return result;
    }

}


