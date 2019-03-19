package com.zxfdospy.tmall.interceptor;

import com.zxfdospy.tmall.pojo.Admin;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AdminInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        String contextPath=session.getServletContext().getContextPath();
        String uri = request.getRequestURI();
        uri = StringUtils.remove(uri, contextPath);
        String method = StringUtils.substringAfterLast(uri, "_");
        if (!method.equals("login")) {
            Admin admin = (Admin) session.getAttribute("admin");
            if (null == admin) {
                response.sendRedirect("adminlogin");
                return false;
            }
        }
        return true;
    }
}
