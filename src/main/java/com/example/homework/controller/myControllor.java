package com.example.homework.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 将首页映射为登录页的控制器
 */
@Controller
public class myControllor {

    @RequestMapping({"/",""})
    public String index(){
        return "login";
    }//初始登录页面


}