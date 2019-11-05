package com.mikasa.service;

import com.mikasa.entity.PageResult;
import com.mikasa.entity.QueryPageBean;
import com.mikasa.entity.Result;
import com.mikasa.pojo.Order;

import java.util.Map;

/**
 * 体检预约 服务
 */
public interface OrderService {
    //1.提交体检预约,并返回预约id
    Result order(Map map) throws Exception;
    //2.根据预约id查询预约信息
    Map findOrderDetailByOrderId(Integer id)throws Exception;
    //3.分页查询预约数据
    PageResult pageQuery(QueryPageBean queryPageBean);
    //4.根据id取消预约
    void cancelOrder(Integer id);
    //5.新增预约信息
    void add(Order order);
    //6.根据预约id查询预约信息
    Order findById(Integer id);
    //7.编辑预约信息
    void edit(Order order);
}
