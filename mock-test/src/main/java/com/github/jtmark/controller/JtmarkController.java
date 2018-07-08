/**
 * Copyright (c) 2017-2018 Thinkfly
 */
package com.github.jtmark.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.jtmark.service.UserService;

/**
 * <p>
 * Description: 
 * </p>
 * @author majintao
 * @version 1.0
 * @Date 2018年7月7日
 */
@Controller
@RequestMapping(path="/jtmark")
public class JtmarkController {
    
    @Autowired
    private UserService userService;
    
    @RequestMapping(path = "/login", method = RequestMethod.GET)
    @ResponseBody
    public String login(long userId) {
        return userService.getUser(userId);
    }
}
