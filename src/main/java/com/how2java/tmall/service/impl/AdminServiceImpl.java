package com.how2java.tmall.service.impl;

import com.how2java.tmall.mapper.AdminMapper;
import com.how2java.tmall.pojo.Admin;
import com.how2java.tmall.pojo.AdminExample;
import com.how2java.tmall.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminMapper adminMapper;

    @Override
    public Admin get(String name, String password) {
        AdminExample example=new AdminExample();
        example.createCriteria().andNameEqualTo(name).andPasswordEqualTo(password);
        List<Admin> result= adminMapper.selectByExample(example);
        if(result.isEmpty())
            return null;
        return result.get(0);
    }
}
