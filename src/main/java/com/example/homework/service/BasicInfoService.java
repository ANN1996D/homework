package com.example.homework.service;

import com.example.homework.item.BasicInfo;
import com.example.homework.item.BriefInfo;
import com.example.homework.repository.BasicInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Controller和数据库中base_info表进行数据交换的服务工具
 */
@Service
public class BasicInfoService {

    @Autowired
    BasicInfoRepository basicInfoRepository;

    public boolean findRoleById(int id) {
        return basicInfoRepository.findBasicInfoById(id).isRole();
    }

    public String findNameById(int id){
        return basicInfoRepository.findBasicInfoById(id).getName();
    }
    public List<BriefInfo> findCourse_listById(int id){
        return basicInfoRepository.findBasicInfoById(id).getCourse_list();
    }

    public BasicInfo findBasicInfoByUsernameAndPassword(String username, String password){
        return basicInfoRepository.findBasicInfoByUsernameAndPassword(username,password);
    }
    public Boolean existsByUsername(String username){
        return basicInfoRepository.existsByUsername(username);
    }
    public BasicInfo findBasicInfoById(int id){
        return basicInfoRepository.findBasicInfoById(id);
    }
    public List<BriefInfo> findCourseListById(int id){
        return basicInfoRepository.findBasicInfoById(id).getCourse_list();
    }
    public BasicInfo save(BasicInfo b){
        return basicInfoRepository.save(b);
    }

}
