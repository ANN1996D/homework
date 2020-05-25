package com.example.homework.item;

import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

/**
 * 作业信息类：用于生成数据库表homework
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@TypeDef(name = "json", typeClass = JsonStringType.class)
public class Homework {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//自增主键
    private int id;
    @Column
    private boolean flag_assign;
    @Type(type = "json")
    @Column(columnDefinition = "json" )
    private BriefInfo course;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private Date deadline;
    @Column
    private String demo_path;
    @Type(type = "json")
    @Column(columnDefinition = "json" )
    private List<HomeworkDetails> details;
    /**
     * 计算选课的总人数
     */
    public int getNumStudent(){
        if(this.getDetails()!=null){
            return this.getDetails().size();
        }else
            return 0;

    }

    /**
     * 计算提交作业的人数
     */
    public int getNumSubmit(){

        List<HomeworkDetails> details = this.getDetails();
        if(details!=null){
            int num = 0;
            for(int i=0;i<details.size();i++){
                if(details.get(i).isFlag_submit()){
                    num++;
                }
            }
            return num;
        }else
            return 0;

    }


}
