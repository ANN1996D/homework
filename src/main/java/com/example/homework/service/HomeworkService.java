package com.example.homework.service;

import com.example.homework.item.Homework;
import com.example.homework.repository.HomeworkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller和数据库中homework表进行数据交换的服务工具
 */
@Service
public class HomeworkService {

    @Autowired
    HomeworkRepository homeworkRepository;

    public Homework findHomeworkById(int id){
        return homeworkRepository.findHomeworkById(id);
    }
    public String findNameById(int id){
        return homeworkRepository.findHomeworkById(id).getName();
    }
    public List<Homework> findLibraryByCourseIdList(List<Integer> id_list){
        List<Homework> homeworkList_raw = homeworkRepository.findAll();
        List<Homework> homeworkList = new ArrayList<>();
        for(int i=0; i< homeworkList_raw.size(); i++){
            Homework temp = homeworkList_raw.get(i);
            if(id_list.contains(temp.getCourse().id))
                homeworkList.add(temp);
        }

        return homeworkList;
    }

    public Homework save(Homework homework){
        return homeworkRepository.save(homework);
    }
}
