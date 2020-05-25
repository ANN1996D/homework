package com.example.homework.repository;

import com.example.homework.item.BasicInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring-Boot的操作数据库中basic_info表的接口
 * 完成对数据库的操作
 */
public interface BasicInfoRepository extends JpaRepository<BasicInfo,Integer> {

    public BasicInfo findBasicInfoByUsernameAndPassword(String username, String password);
    public Boolean existsByUsername(String username);
    public BasicInfo findBasicInfoById(int id);

}

