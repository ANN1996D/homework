package com.example.homework.controller;

import com.example.homework.repository.HomeworkRepository;
import com.example.homework.service.BasicInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

/**
 * 处理文件上传和下载请求的控制器
 */
@Controller
public class FileController {

    @Autowired
    HomeworkRepository homeworkRepository;
    @Autowired
    BasicInfoService basicInfoService;

    @PostMapping("/file/upload")
    public String uploadController (@RequestParam("file") MultipartFile file,
                                    @RequestParam("path") String path,
                                    @RequestParam("id") int id,
                                    RedirectAttributes redirectAttributes
    ) {
        // 获取原始名字
        String fileName = file.getOriginalFilename();
        // 文件保存路径
        String filePath = System.getProperty("user.dir") + "/files/" + path;
        // 文件重命名，防止重复
        fileName = filePath + fileName;//UUID.randomUUID()
        System.out.println(fileName);
        // 文件对象
        File dest = new File(fileName);

        // 判断路径是否存在，如果不存在则创建
        if(!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            // 保存到服务器中
            file.transferTo(dest);

            redirectAttributes.addAttribute("id",id);
            if(basicInfoService.findRoleById(id))
                return "redirect:/student/view_homework";
            else
                return "redirect:/teacher/view_library";
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("上传失败");
            return "404";
        }
    }

    @PostMapping("/file/download")
    @ResponseBody
    public void download(HttpServletResponse response,
                           @RequestParam("filename") String fileName
    ) throws Exception {

        File file = new File(fileName);
        // 穿件输入对象
        FileInputStream fis = new FileInputStream(file);
        // 设置相关格式
        response.setContentType("application/force-download");
        // 设置下载后的文件名以及header
        response.addHeader("Content-disposition", "attachment;fileName=" + file.getName());
        // 创建输出对象
        OutputStream os = response.getOutputStream();
        // 常规操作
        byte[] buf = new byte[1024];
        int len = 0;
        while((len = fis.read(buf)) != -1) {
            os.write(buf, 0, len);
        }
        fis.close();

    }
}



