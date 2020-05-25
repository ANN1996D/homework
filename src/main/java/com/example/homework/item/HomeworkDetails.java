package com.example.homework.item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 作业详情类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HomeworkDetails implements Serializable {
    private BriefInfo student;
    private boolean flag_submit;//是否提交
    private int submit_times;//提交次数
    private boolean flag_judge;//是否批改
    private float score;
    private String file_path;


    public HomeworkDetails(BriefInfo student){
        this.setStudent(student);
    }
}
