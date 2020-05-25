package com.example.homework.service;

import com.example.homework.item.Course;
import com.example.homework.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Controller和数据库中course表进行数据交换的服务工具
 */
@Service
public class CourseService {

    @Autowired
    CourseRepository courseRepository;

    public Course findCourseById(int id){
        return  courseRepository.findCourseById(id);
    }
    public String findNameById(int id){
        return courseRepository.findCourseById(id).getName();
    }
    public List<Course> findAll(){
        return courseRepository.findAll();
    }
    public Course addCourse(Course course){
        return courseRepository.save(course);
    }
    public Course save(Course c){
        return courseRepository.save(c);
    }
}
