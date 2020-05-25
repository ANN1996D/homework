package com.example.homework.repository;

import com.example.homework.item.Course;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring-Boot的操作数据库中course表的接口
 */
public interface CourseRepository extends JpaRepository<Course,Integer> {

    public Course findCourseById(int id);

}
