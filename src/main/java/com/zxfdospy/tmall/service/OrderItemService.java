package com.zxfdospy.tmall.service;

import java.util.List;

import com.zxfdospy.tmall.pojo.Order;
import com.zxfdospy.tmall.pojo.OrderItem;

public interface OrderItemService {

    void add(OrderItem c);

    void delete(int id);
    void update(OrderItem c);
    OrderItem get(int id);
    List list();

    void fill(List<Order> os);

    void fill(Order o);

    int getSaleCount(int  pid);

    List listByUser(int id);
}