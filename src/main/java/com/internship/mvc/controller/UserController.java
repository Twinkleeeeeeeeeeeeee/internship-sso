package com.internship.mvc.controller;

import com.internship.mvc.pojo.SingleResult;
import com.internship.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ResponseBody
    public SingleResult userLogin(String username, String password,
                                  HttpServletRequest request, HttpServletResponse response) {
        try {
            SingleResult result = userService.userLogin(username, password, request, response);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return SingleResult.build(500, "");
        }
    }

    @RequestMapping("/loginOut/{token}")
    public String loginOut(@PathVariable String token) {
        userService.logout(token);
        return "index";
    }

    @RequestMapping("/token/{token}")
    @ResponseBody
    public Object getUserByToken(@PathVariable String token) {
        try {
            SingleResult result = userService.queryUserByToken(token);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return SingleResult.build(400, "用户名或者密码错误");
        }

    }
}
