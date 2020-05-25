package com.example.homework.controller;

import com.example.homework.item.*;
import com.example.homework.service.BasicInfoService;
import com.example.homework.service.CourseService;
import com.example.homework.service.HomeworkService;
import com.example.homework.service.ToolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 管理学生请求的控制器
 */
@Controller
public class StudentController {

    @Autowired
    BasicInfoService basicInfoService;
    @Autowired
    CourseService courseService;
    @Autowired
    HomeworkService homeworkService;
    @Autowired
    ToolService toolService;

    /**
     * 管理进入学生基本信息页面请求
     * @param id
     * @param map
     * @return
     */
    @RequestMapping(value = "/student/view_info")
    public String viewInfoController(@RequestParam("id") int id,
                                    Map<String, Object> map){
        BasicInfo basic_info =  basicInfoService.findBasicInfoById(id);

        map.put("id",id);
        map.put("name",basicInfoService.findNameById(id));
        map.put("option","view_info");
        map.put("basic_info",basic_info);
        return  "student" ;
    }

    /**
     * 管理进入学生课程页面请求
     * @param id
     * @param map
     * @return
     */
    @RequestMapping("/student/view_course")
    public String viewCourseController(@RequestParam("id") int id,
                                       Map<String, Object> map) {

        //找出已选的课程编号
        List<Integer> choose_list = new ArrayList<>();
        List<Course> courses = (null==courseService.findAll()) ? (new ArrayList<>()) : courseService.findAll();
        for(int i=0;i<courses.size();i++){
            List<BriefInfo> temp_list = (null==courses.get(i).getStudent_list()) ? (new ArrayList<>()) : courses.get(i).getStudent_list();
            List<Integer> student_id_list = toolService.extractIdListFromInfoList(temp_list);
            if(student_id_list.contains(id))
                choose_list.add(courses.get(i).getId());
        }

        map.put("id",id);
        map.put("name",basicInfoService.findNameById(id));
        map.put("option","view_course");
        map.put("choose_list",choose_list);
        map.put("courses",courses);
        return  "student" ;
    }

    /**
     * 管理进入学生作业页面的请求
     * @param id
     * @param map
     * @return
     */
    @RequestMapping("/student/view_homework")
    public String viewHomeworkController(@RequestParam("id") int id,
                                       Map<String, Object> map) {

        BasicInfo basic_info =  basicInfoService.findBasicInfoById(id);

        List<BriefInfo> Course_list = basic_info.getCourse_list();
        List<Homework> homeworks = new ArrayList<>();
        if(null != Course_list){
            for(int i=0;i<Course_list.size();i++){
                Course course = courseService.findCourseById(Course_list.get(i).id);
                List<BriefInfo> hw_id = course.getHomework_list();
                if(null != hw_id){
                    for(int j=0;j<hw_id.size();j++){
                        Homework hw = homeworkService.findHomeworkById(hw_id.get(j).id);
                        homeworks.add(hw);
                    }
                }

            }
        }

        map.put("id",id);
        map.put("name",basicInfoService.findNameById(id));
        map.put("option","view_homework");
        map.put("homeworks",homeworks);
        return  "student" ;
    }

    /**
     * 处理学生选课请求
     * @param id
     * @param course_id
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/student/choose")
    public String chooseController(@RequestParam("id") int id,
                                   @RequestParam ("course_id") int course_id,
                                   RedirectAttributes redirectAttributes) {

        //增加学生的课程信息
        BasicInfo student = basicInfoService.findBasicInfoById(id);
        List<BriefInfo> course_list = null==student.getCourse_list()  ? new ArrayList<>() : student.getCourse_list();
        course_list.add(new BriefInfo(course_id,courseService.findNameById(course_id)));
        student.setCourse_list(course_list);
        basicInfoService.save(student);
        //增加课程的学生信息
        Course course = courseService.findCourseById(course_id);
        List<BriefInfo> student_list = null==course.getStudent_list() ? new ArrayList<>() : course.getStudent_list();
        student_list.add(new BriefInfo(id,student.getName())) ;
        course.setStudent_list(student_list);
        courseService.save(course);
        //增加作业的学生信息
        List<Integer> hw_id_list = toolService.extractIdListFromInfoList(course.getHomework_list());
        for(int i=0;i<hw_id_list.size();i++) {
            Homework homework = homeworkService.findHomeworkById(hw_id_list.get(i));
            List<HomeworkDetails> details = null==homework.getDetails() ? new ArrayList<>() : homework.getDetails();
            HomeworkDetails new_details =new HomeworkDetails();
            new_details.setStudent(new BriefInfo(id,student.getName()));
            details.add(new_details);
            homework.setDetails(details);
            homeworkService.save(homework);
        }

        redirectAttributes.addAttribute("id",id);
        return  "redirect:/student/view_course";
    }


    /**
     * 处理学生撤课请求
     * @param id
     * @param course_id
     * @param redirectAttributes
     * @return
     */
    @RequestMapping("/student/quit")
    public String quitController(@RequestParam("id") int id,
                                 @RequestParam ("course_id") int course_id,
                                 RedirectAttributes redirectAttributes) {
        //删除学生的课程信息
        BasicInfo student = basicInfoService.findBasicInfoById(id);
        List<BriefInfo> course_list = student.getCourse_list();
        course_list = toolService.removeOneFromInfoListById(course_list,course_id);
        student.setCourse_list(course_list);
        basicInfoService.save(student);
        //删除课程的学生信息
        Course course = courseService.findCourseById(course_id);
        List<BriefInfo> student_list = course.getStudent_list();
        student_list = toolService.removeOneFromInfoListById(student_list,id);
        course.setStudent_list(student_list);
        courseService.save(course);
        //删除作业的学生信息
        List<Integer> hw_id_list = toolService.extractIdListFromInfoList(course.getHomework_list());
        for(int i=0;i<hw_id_list.size();i++) {
            Homework homework = homeworkService.findHomeworkById(hw_id_list.get(i));
            List<HomeworkDetails> details = homework.getDetails();
            details = toolService.removeOneFromDetailsById(details,id);
            homework.setDetails(details);
            homeworkService.save(homework);
        }

        redirectAttributes.addAttribute("id",id);
        return  "redirect:/student/view_course";
    }

    /**
     * 处理学生提交作业请求
     * @param file
     * @param id
     * @param hw_id
     * @param redirectAttributes
     * @return
     */
    @RequestMapping("/student/submit")
    public String quitController(@RequestParam("file") MultipartFile file,
                                 @RequestParam("id") int id,
                                 @RequestParam ("homework_id") int hw_id,
                                 RedirectAttributes redirectAttributes
                                 ) {

        //获取文件名
        String fileName = file.getOriginalFilename();
        String filePath = System.getProperty("user.dir") + "/files/homework/" ;
        fileName = filePath + fileName;

        //更新作业数据库信息
        Homework hw = homeworkService.findHomeworkById(hw_id);
        List<HomeworkDetails> hw_details = hw.getDetails();
        for(int i=0; i <hw_details.size();i++){
//            HomeworkDetails temp = hw_details.get(i);
            if(hw_details.get(i).getStudent().id==id){
                hw_details.get(i).setSubmit_times(hw_details.get(i).getSubmit_times()+1);
                hw_details.get(i).setFlag_submit(true);
                hw_details.get(i).setFile_path(fileName);
            }
        }
        hw.setDetails(hw_details);
        homeworkService.save(hw);

        File dest = new File(fileName);
        // 判断路径是否存在，如果不存在则创建
        if(!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            // 保存到服务器中
            file.transferTo(dest);
            redirectAttributes.addAttribute("id",id);
            return "redirect:/student/view_homework";
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("上传失败");
            return "404";
        }
    }


}
