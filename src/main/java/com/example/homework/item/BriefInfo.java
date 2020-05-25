package com.example.homework.item;

import java.io.Serializable;

/**
 * 项目（人物、作业、课程）的基本信息类
 * 包括id和名称
 */
public class BriefInfo implements Serializable {
    public int id;
    public String name;

    public BriefInfo(int id, String name){
        this.id = id;
        this.name = name;
    }

    public BriefInfo(){
    }
}
