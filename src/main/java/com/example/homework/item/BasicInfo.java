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
 * 人物信息息类：用于生成数据库表basic_info
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity//告诉是个实体类
@TypeDef(name = "json", typeClass = JsonStringType.class)
public class BasicInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//自增主键
    private int id;
    @Column //省略默认列名就是属性名
    private boolean role;//true: 学生 false:教师
    @Column
    private String name;
    @Column
    private boolean sex;//true: 男 false：女
    @Column
    private String phone;
    @Column
    private String email;
    @Column
    private String username;
    @Column
    private String password;
    @Type(type = "json")
    @Column(columnDefinition = "json" )
    private List<BriefInfo> course_list;

}
