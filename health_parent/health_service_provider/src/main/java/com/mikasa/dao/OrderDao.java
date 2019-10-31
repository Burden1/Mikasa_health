package com.mikasa.dao;

import com.mikasa.pojo.Order;

import java.util.List;
import java.util.Map;

public interface OrderDao {
    //1.有条件的查询预约单
    List<Order> findOrderByCondition(Order order);
    //2.添加预约
    void add(Order order);
    //3.根据预约id查询预约信息
    Map findOrderDetailByOrderId(Integer id);
}
