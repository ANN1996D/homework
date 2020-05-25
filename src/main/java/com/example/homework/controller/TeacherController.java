package com.example.homework.controller;

import com.example.homework.item.*;
import com.example.homework.service.BasicInfoService;
import com.example.homework.service.CourseService;
import com.example.homework.service.HomeworkService;
import com.example.homework.service.ToolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 管理教师请求的控制器
 */
@Controller
public class TeacherController {

    @Autowired
    BasicInfoService basicInfoService;
    @Autowired
    CourseService courseService;
    @Autowired
    HomeworkService homeworkService;
    @Autowired
    ToolService toolService;

    /**
     * 管理进入教师基础信息页面的请求
     * @param id
     * @param map
     * @return
     */
    @RequestMapping(value = "/teacher/view_info")
    public String viewInfoController(@RequestParam("id") int id,
                                     Map<String, Object> map){

        BasicInfo basic_info =  basicInfoService.findBasicInfoById(id);
        map.put("id",id);
        map.put("name",basicInfoService.findNameById(id));
        map.put("option","view_info");
        map.put("basic_info",basic_info);
        return  "teacher" ;
    }

    /**
     * 管理进入教师课程页面的请求
     * @param id
     * @param map
     * @return
     */
    @RequestMapping("/teacher/view_course")
    public String viewCourseController(@RequestParam("id") int id,
                                       Map<String, Object> map) {
        List<Course> courses = new ArrayList<Course>();
        List<Course> courses_raw = courseService.findAll();
        if(!courses_raw.isEmpty()){
            int len = courses_raw.size();
            for (int i = 0; i < len; i++) {
                Course temp = courses_raw.get(i);
                if (temp.getTeacher().id == id)
                    courses.add(temp);
            }
        }

        map.put("id", id);
        map.put("option", "view_course");
        map.put("courses", courses);
        return "teacher";

    }

    /**
     * 管理教师开设课程的请求
     * @param id
     * @param course_name
     * @param capacity
     * @param redirectAttributes
     * @return
     */
    @RequestMapping("/teacher/addCourse")
    public String addCourseController(@RequestParam("id") int id,
                                            @RequestParam("name") String course_name,
                                            @RequestParam("capacity") int capacity,
                                            RedirectAttributes redirectAttributes) {

        BasicInfo teacher = basicInfoService.findBasicInfoById(id);
        //将课程添加到数据库中
        Course course = new Course();
        course.setTeacher(new BriefInfo(id,teacher.getName()));
        course.setName(course_name);
        course.setCapacity(capacity);
        course = courseService.addCourse(course);
        //将课程信息添加到老师信息中
        List<BriefInfo> course_list = new ArrayList<BriefInfo>();
        course_list = (null!=teacher.getCourse_list()) ? teacher.getCourse_list() : course_list;
        course_list.add(new BriefInfo(course.getId(),course.getName()));
        teacher.setCourse_list(course_list);
        basicInfoService.save(teacher);

        redirectAttributes.addAttribute("id",id);
        return "redirect:/teacher/view_course";

    }

    /**
     * 管理教师进入作业页面的请求
     * @param id
     * @param map
     * @return
     */
    @RequestMapping("/teacher/view_homework_list")
    public String viewHomeworkListController(@RequestParam("id") int id,
                                         Map<String, Object> map) {
        //老师id---课程id---是否布置
        List<Homework> homeworks = new ArrayList<Homework>();
        List<Course> courses = courseService.findAll();
        if(null!=courses){
            for(int i=0;i<courses.size();i++){
                Course temp_course = courses.get(i);
                if(temp_course.getTeacher().id==id){
                    List<BriefInfo> hw_list = temp_course.getHomework_list();
                    if(null!=hw_list){
                        for(int j=0;j<hw_list.size();j++){
                            Homework temp = homeworkService.findHomeworkById(hw_list.get(j).id);
                            if(temp.isFlag_assign())
                                homeworks.add(temp);
                        }
                    }

                }
            }
        }

        map.put("id",id);
        map.put("name",basicInfoService.findNameById(id));
        map.put("option","view_homework_list");
        map.put("homeworks",homeworks);
        return  "teacher" ;
    }

    /**
     * 管理教师查看某次作业详情的请求
     * @param id
     * @param homework_id
     * @param map
     * @return
     */
    @RequestMapping("/teacher/view_homework_single")
    public String viewHomeworkSingleController(@RequestParam("id") int id,
                                         @RequestParam("homework_id") int homework_id,
                                         Map<String, Object> map) {

        Homework homework = homeworkService.findHomeworkById(homework_id);
        map.put("id",id);
        map.put("name",basicInfoService.findNameById(id));
        map.put("option","view_homework_single");
        map.put("homework",homework);
        return  "teacher" ;
    }

    /**
     * 管理教师查看题库的请求
     * @param id
     * @param map
     * @return
     */
    @RequestMapping("/teacher/view_library")
    public String viewLibraryController(@RequestParam("id") int id,
                                               Map<String, Object> map) {
        //导入题库列表
        List<BriefInfo> course_list = basicInfoService.findCourseListById(id);
        List<Integer> course_id_list = new ArrayList<Integer>();
        List<Homework> library = new ArrayList<Homework>();
        if(null!=course_list){
            for(int i=0; i<course_list.size();i++){
                course_id_list.add(course_list.get(i).id);
            }
            library.addAll(homeworkService.findLibraryByCourseIdList(course_id_list));
        }

        //导入课程列表
        List<Course> courses = new ArrayList<Course>();
        List<Course> courses_raw = courseService.findAll();
        if(null!=courses_raw){
            int len = courses_raw.size();
            for (int i = 0; i < len; i++) {
                Course temp = courses_raw.get(i);
                if (temp.getTeacher().id == id)
                    courses.add(temp);
            }
        }

        map.put("id",id);
        map.put("name",basicInfoService.findNameById(id));
        map.put("option","view_library");
        map.put("courses",courses);
        map.put("library",library);
        return  "teacher" ;
    }

    /**
     * 处理教师布置作业的请求
     * @param id
     * @param date
     * @param homework_id
     * @param redirectAttributes
     * @return
     */
    @RequestMapping("/teacher/assign")
    public String assignController(@RequestParam("id") int id,
                                   @RequestParam("deadline") Date date,
                                   @RequestParam("homework_id") int homework_id,
                                   RedirectAttributes redirectAttributes
                                       ) {

        Homework homework = homeworkService.findHomeworkById(homework_id);
        homework.setFlag_assign(true);
        homework.setDeadline(date);
        homeworkService.save(homework);

        redirectAttributes.addAttribute("id",id);
        return "redirect:/teacher/view_library";
    }

    /**
     * 处理教师增加题库的请求
     * @param id
     * @param course_id
     * @param file
     * @param path
     * @param homework
     * @param redirectAttributes
     * @return
     */
    @RequestMapping("/teacher/addLibrary")
    public String addLibraryController(@RequestParam("id") int id,
                                       @RequestParam("course_id") int course_id,
                                       @RequestParam("file") MultipartFile file,
                                       @RequestParam("path") String path,
                                       Homework homework,
                                       RedirectAttributes redirectAttributes) {
        //将demo文件存储到服务器
        String filePath = System.getProperty("user.dir") + "/files/" + path;
        String fileName = file.getOriginalFilename();
        fileName = filePath + fileName;//UUID.randomUUID()
        System.out.println(fileName);
        // 文件对象
        File dest = new File(fileName);
        if(!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            // 保存到服务器中
            file.transferTo(dest);
        } catch (Exception e) {
            e.printStackTrace();
            return "404";
        }

        Course course = courseService.findCourseById(course_id);
        //将题库信息存储到数据库
        homework.setCourse(new BriefInfo(course_id,course.getName()));
        homework.setDemo_path(fileName);
        //将课程里的学生名单导入到题库里
        List<BriefInfo> student_list = new ArrayList<>();
        List<HomeworkDetails> hd_list = new ArrayList<>();
        student_list = null!= course.getStudent_list() ? course.getStudent_list() : student_list;
        for(int i=0; i<student_list.size(); i++)
            hd_list.add(new HomeworkDetails(student_list.get(i)));
        homework.setDetails(hd_list);
        homework = homeworkService.save(homework);
        //更新课程里的题库信息
        List<BriefInfo> hw_list = new ArrayList<BriefInfo>();
        hw_list = null!=course.getHomework_list()?course.getHomework_list():hw_list;
        hw_list.add(new BriefInfo(homework.getId(),homework.getName()));
        course.setHomework_list(hw_list);
        courseService.save(course);


        redirectAttributes.addAttribute("id",id);
        return "redirect:/teacher/view_library";
    }

    /**
     * 处理教师给作业打分的请求
     * @param id
     * @param student_id
     * @param homework_id
     * @param score
     * @param redirectAttributes
     * @return
     */
    @RequestMapping("/teacher/score")
    public String judgeController( @RequestParam("id") int id,
                                   @RequestParam("student_id") int student_id,
                                   @RequestParam("homework_id") int homework_id,
                                   @RequestParam("score") int score,
                                   RedirectAttributes redirectAttributes) {

        Homework homework = homeworkService.findHomeworkById(homework_id);
        List<HomeworkDetails> details = homework.getDetails();
        if(null != details) {
            for (int i = 0; i < details.size(); i++)
                if (student_id == details.get(i).getStudent().id){
                    details.get(i).setFlag_judge(true);
                    details.get(i).setScore(score);
                }

        }
        homework.setDetails(details);
        homeworkService.save(homework);

        redirectAttributes.addAttribute("homework_id",homework_id);
        redirectAttributes.addAttribute("id",id);
        return "redirect:/teacher/view_homework_single";
    }



}
