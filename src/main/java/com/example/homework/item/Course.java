package com.example.homework.item;

import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.List;

/**
 * 课程信息类：用于生成数据库表course
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@TypeDef(name = "json", typeClass = JsonStringType.class)
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//自增主键
    @Column
    private int id;
    @Column
    private String name;
    @Column
    private int capacity;
    @Type(type = "json")
    @Column(columnDefinition = "json" )
    private BriefInfo teacher;
    @Type(type = "json")
    @Column(columnDefinition = "json" )
    private List<BriefInfo> student_list;
    @Type(type = "json")
    @Column(columnDefinition = "json" )
    private List<BriefInfo> homework_list;

    /**
     * 计算选课的总人数
     */
    public int getNumStudent(){
        if(this.getStudent_list()!=null){
            return this.getStudent_list().size();
        }else
            return 0;

    }
}
