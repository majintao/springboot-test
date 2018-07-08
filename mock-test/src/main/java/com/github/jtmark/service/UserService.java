/**
 * Copyright (c) 2017-2018 Thinkfly
 */
package com.github.jtmark.service;

import org.springframework.stereotype.Service;

/**
 * <p>
 * Description: 
 * </p>
 * @author majintao
 * @version 1.0
 * @Date 2018年7月7日
 */
@Service
public class UserService {
    
    public String getUser(long userId) {
        return "user";
    }
}
