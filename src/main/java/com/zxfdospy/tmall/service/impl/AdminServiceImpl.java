package com.zxfdospy.tmall.service.impl;

import com.zxfdospy.tmall.mapper.AdminMapper;
import com.zxfdospy.tmall.pojo.Admin;
import com.zxfdospy.tmall.pojo.AdminExample;
import com.zxfdospy.tmall.service.AdminService;
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
