/**
 * Copyright (c) 2017-2018 Thinkfly
 */
package com.github.jtmark.controller;

import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.github.jtmark.service.UserService;

/**
 * <p>
 * Description:
 * </p>
 * 
 * @author majintao
 * @version 1.0
 * @Date 2018年7月7日
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class JtmarkControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    @InjectMocks     //负责注入mocker对象
    JtmarkController jtmarkController;
    
    @Mock            //mock代理对象
    UserService userService ;

    @Test
    public void login() throws Exception {
        when(userService.getUser(11355)).thenReturn("user");
        
        mvc.perform(MockMvcRequestBuilders.get("/jtmark/login?userId=11355").contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("user"))
                .andDo(MockMvcResultHandlers.print());
    }
}
