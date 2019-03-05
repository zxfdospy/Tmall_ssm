package com.how2java.tmall.service;

import com.how2java.tmall.pojo.Admin;

public interface AdminService {
    Admin get(String name, String password);
}
