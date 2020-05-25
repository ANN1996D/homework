package com.example.homework.service;

import com.example.homework.item.BriefInfo;
import com.example.homework.item.HomeworkDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller中到的一些数据处理工具
 */
@Service
public class ToolService {

    @Autowired
    BasicInfoService basicInfoService;
    @Autowired
    CourseService courseService;
    @Autowired
    HomeworkService homeworkService;

    public List<Integer> extractIdListFromInfoList(List<BriefInfo> info_list) {

        List<Integer> id_list = new ArrayList<Integer>();
        if (info_list != null)
            for (int i = 0; i < info_list.size(); i++)
                id_list.add(info_list.get(i).id);
        return id_list;
    }

    public List<BriefInfo> removeOneFromInfoListById(List<BriefInfo> info_list ,int id) {

        List<BriefInfo> dst_list = new ArrayList<BriefInfo>();
        if (info_list != null){
            for (int i = 0; i < info_list.size(); i++)
                if(id != info_list.get(i).id)
                    dst_list.add(info_list.get(i));
        }
        return dst_list;
    }

    public List<HomeworkDetails> removeOneFromDetailsById(List<HomeworkDetails> details , int id) {

        List<HomeworkDetails> dst_list = new ArrayList<HomeworkDetails>();
        if (details != null){
            for (int i = 0; i < details.size(); i++)
                if(id != details.get(i).getStudent().id)
                    dst_list.add(details.get(i));
        }
        return dst_list;
    }

    public HomeworkDetails findOneFromDetailsById(List<HomeworkDetails> details , int id) {

        if(null != details) {
            for (int i = 0; i < details.size(); i++)
                if (id == details.get(i).getStudent().id)
                    return details.get(i);
        }
        return new HomeworkDetails();
    }


}
