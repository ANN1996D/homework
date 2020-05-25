package com.example.homework.controller;

import com.example.homework.item.Account;
import com.example.homework.item.BasicInfo;
import com.example.homework.repository.BasicInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;

import java.util.Map;

/**
 * 管理登录请求的控制器
 */
@Controller
public class LoginController {

//    @Autowired
//    LoginService loginService;
    @Autowired
    BasicInfoRepository basicInfoRepository;

    @PostMapping(value = "/login")
    public String Login(Account acc,
                        @RequestParam("action") String action,
                        Map<String, Object> map){

        String username = acc.getUsername();
        String password = acc.getPassword();
        if("login".equals(action)){
            //登陆操作
            if(StringUtils.isEmpty(username)){
                map.put("err","用户名不能为空");
                return "login";
            }else if(StringUtils.isEmpty(password)){
                map.put("err","密码不能为空");
                return "login";
            }else{
                BasicInfo basic_info =  basicInfoRepository.findBasicInfoByUsernameAndPassword(username,password);
                if(null==basic_info){
                    map.put("err","用户名和密码不匹配");
                  return "login";
                }else{
                    //登陆成功
                    map.put("option","view_info");
                    map.put("id",basic_info.getId());
                    map.put("name",basic_info.getName());
                    map.put("basic_info",basic_info);
                    return basic_info.isRole() ? "student" : "teacher";
                }
            }
        }else{
            //注册操作
            return "register";
        }

    }

}
