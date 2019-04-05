package com.zxfdospy.tmall.interceptor;

import com.zxfdospy.tmall.pojo.Admin;
import com.zxfdospy.tmall.util.CookieUtil;
import com.zxfdospy.tmall.util.JsonUtil;
import com.zxfdospy.tmall.util.RedisPoolUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AdminInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        String contextPath = session.getServletContext().getContextPath();
        String uri = request.getRequestURI();
        uri = StringUtils.remove(uri, contextPath);
        String method = StringUtils.substringAfterLast(uri, "_");
        if (!method.equals("login")) {
            Admin admin = null;
            String admintoken = CookieUtil.readLoginToken(request, CookieUtil.COOKIE_NAME_ADMIN);
            if (org.apache.commons.lang3.StringUtils.isNotEmpty(admintoken)) {
                String adminstr = RedisPoolUtil.get(admintoken);
                admin = JsonUtil.string2Obj(adminstr, Admin.class);
                if (admin == null) {
                    response.sendRedirect("adminlogin");
                    return false;
                }else {
                    RedisPoolUtil.expire(admintoken,60*30);
                    admin.setPassword("");
                    session.setAttribute("admin",admin);
                    return true;
                }
            }else {
                response.sendRedirect("adminlogin");
                return false;
            }
//            Admin admin = (Admin) session.getAttribute("admin");
//            if (null == admin) {
//                response.sendRedirect("adminlogin");
//                return false;
//            }
        }
        return true;
    }
}
