package com.zxfdospy.tmall.controller;

import com.zxfdospy.tmall.pojo.Admin;
import com.zxfdospy.tmall.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpSession;

@Controller
public class AdminController {
    @Autowired
    AdminService adminService;

    @RequestMapping("adminlogin")
    public  String adminLoginPage(){
        return "admin/adminlogin";
    }

    @RequestMapping("admin_check_login")
    public String login(@RequestParam("name")String name, @RequestParam("password")String password, Model model,HttpSession session){
        name= HtmlUtils.htmlEscape(name);
        Admin admin=adminService.get(name,password);
        if(null==admin){
            model.addAttribute("msg", "账号密码错误");
            return "admin/adminlogin";
        }
        session.setAttribute("admin", admin);
        return "redirect:admin_category_list";
    }

    @RequestMapping("admin_check_loginout")
    public String logout(HttpSession session){
        session.removeAttribute("admin");
        return "admin/adminlogin";
    }
}
