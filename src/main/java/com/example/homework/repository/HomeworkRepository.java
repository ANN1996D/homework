package com.example.homework.repository;

import com.example.homework.item.Homework;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring-Boot的操作数据库中Homework表的接口
 */
public interface HomeworkRepository extends JpaRepository<Homework,Integer> {

    public Homework findHomeworkById(int id);
}
