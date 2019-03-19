package com.zxfdospy.tmall.service;

import com.zxfdospy.tmall.pojo.Admin;

public interface AdminService {
    Admin get(String name, String password);
}
