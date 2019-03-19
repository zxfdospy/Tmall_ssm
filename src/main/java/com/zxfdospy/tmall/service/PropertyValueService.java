package com.zxfdospy.tmall.service;
 
import com.zxfdospy.tmall.pojo.Product;
import com.zxfdospy.tmall.pojo.PropertyValue;
 
import java.util.List;
 
public interface PropertyValueService {
    void init(Product p);
    void update(PropertyValue pv);
 
    PropertyValue get(int ptid, int pid);
    List<PropertyValue> list(int pid);
}