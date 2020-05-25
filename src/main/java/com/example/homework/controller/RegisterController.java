package com.example.homework.controller;


import com.example.homework.item.BasicInfo;
import com.example.homework.repository.BasicInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * 管理注册请求的控制器
 */
@Controller
public class RegisterController {

    @Autowired
    BasicInfoRepository basicInfoRepository;

    @PostMapping(value = "/register")
    public String Register(BasicInfo basicInfo,
                           @RequestParam("passwordConfirmed") String passwordConfirmed,//映射方法入参
                        Map<String, Object> map ) {

        if(basicInfo.getPassword().equals(passwordConfirmed)){
            //注册成功，返回登录页面
//            basicInfo.setUsername(basicInfo.getUsername());
//            basicInfo.setPassword(basicInfo.getPassword());
            if(!basicInfoRepository.existsByUsername(basicInfo.getUsername())){
//                basicInfo.setCourse_id(Arrays.asList(1,2,3));
                basicInfoRepository.save(basicInfo);
                map.put("pass","注册成功,请登录！");
                return "login";
            }else{
                map.put("err","用户名已存在");
                return "register";
            }

        } else{
            //注册失败
            map.put("err","两次密码输入不一致");
//            model.addAttribute("username",basicInfo.getUsername());
            return "register";
        }
    }
}
