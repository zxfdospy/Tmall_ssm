package com.zxfdospy.tmall.controller;

import com.zxfdospy.tmall.pojo.Admin;
import com.zxfdospy.tmall.service.AdminService;
import com.zxfdospy.tmall.util.CookieUtil;
import com.zxfdospy.tmall.util.JsonUtil;
import com.zxfdospy.tmall.util.RedisPoolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;

@Controller
public class AdminController {
    @Autowired
    AdminService adminService;

    @RequestMapping("adminlogin")
    public  String adminLoginPage(){
        return "admin/adminlogin";
    }

    @RequestMapping("admin_check_login")
    public String login(@RequestParam("name")String name, @RequestParam("password")String password, Model model, HttpSession session, HttpServletResponse response){
        name= HtmlUtils.htmlEscape(name);
        Admin admin=adminService.get(name,password);
        if(null==admin){
            model.addAttribute("msg", "账号密码错误");
            return "admin/adminlogin";
        }
        String adminuuid=UUID.randomUUID().toString();
        CookieUtil.writeLoginToken(response,CookieUtil.COOKIE_NAME_ADMIN, adminuuid);
        RedisPoolUtil.setEx(adminuuid, JsonUtil.obj2String(admin),60*30);//30分钟失效
        admin.setPassword("");
        session.setAttribute("admin",admin);
//        session.setAttribute("admin", admin);
        return "redirect:admin_category_list";
    }

    @RequestMapping("admin_check_loginout")
    public String logout(HttpSession session, HttpServletRequest request){
        String admintoken= CookieUtil.readLoginToken(request,CookieUtil.COOKIE_NAME_ADMIN);
        RedisPoolUtil.del(admintoken);
        session.removeAttribute("admin");
        return "admin/adminlogin";
    }
}
