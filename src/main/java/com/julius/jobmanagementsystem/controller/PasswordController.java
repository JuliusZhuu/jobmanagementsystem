package com.julius.jobmanagementsystem.controller;

import com.julius.jobmanagementsystem.domain.entity.Student;
import com.julius.jobmanagementsystem.domain.entity.Teacher;
import com.julius.jobmanagementsystem.service.StudentService;
import com.julius.jobmanagementsystem.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * 修改密码Controller。包括修改学生密码、老师密码。
 *
 * @author Chan
 */
@Controller
public class PasswordController {

    @Autowired
    StudentService studentService;
    @Autowired
    TeacherService teacherService;

    /**
     * 根据session中信息修改当前用户密码
     *
     * @param request 请求信息对象
     * @return 返回物理视图
     */
    @RequestMapping(value = "/modifyStuPassword", method = RequestMethod.POST)
    public String modifyStuPassword(HttpServletRequest request) {
        try {
            //获取当前学生session中保存的账号
            String studentId = request.getSession().getAttribute("studentId").toString();
            String oldPassword = request.getParameter("oldPwd");
            String newPassword = request.getParameter("newPwd");
            Student stu = studentService.findStudentInfoByStuId(Integer.valueOf(studentId));
            if (stu.getStuPwd().equals(oldPassword)) {
                studentService.updatePasswordByStuId(Integer.valueOf(studentId), newPassword);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "/modifyPwdFailed";
        }
        return "/modifyPwdSuccess";
    }
    /**
     * 根据session中信息修改当前用户密码
     *
     * @param request 请求信息对象
     * @return 返回物理视图
     */
    @RequestMapping(value = "/modifyTeaPassword", method = RequestMethod.POST)
    public String modifyTeaPassword(HttpServletRequest request) {
        String currentID = (String) request.getSession().getAttribute("id");
        try {
            Teacher tea = teacherService.findTeacherByTeaId(Integer.valueOf(currentID));
            if (!tea.getTeaPwd().equals(request.getParameter("oldPwd"))) {
                return "/modifyPwdFailed";
            }
            teacherService.updatePasswordByTeaId(currentID, request.getParameter("newPwd"));
        } catch (Exception e) {
            e.printStackTrace();
            return "/modifyPwdFailed";
        }
        return "/modifyPwdSuccess";
    }
}
