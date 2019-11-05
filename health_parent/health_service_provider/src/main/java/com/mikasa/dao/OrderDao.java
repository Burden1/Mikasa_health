package com.mikasa.dao;

import com.github.pagehelper.Page;
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
    //4.根据日期统计预约数
    Integer findOrderCountByDate(String today);
    //5.根据日期统计预约数，统计指定日期之后的预约数
    Integer findOrderCountAfterDate(String thisWeekMonday);
    //6.根据日期统计到诊数
    Integer findVisitsCountByDate(String today);
    //7.根据日期统计到诊数，统计指定日期之后的到诊数
    Integer findVisitsCountAfterDate(String thisWeekMonday);
    //8.查询热门套餐，查询前4条
    List<Map> findHotSetmeal();
    //9.有条件的查询预约数据
    Page<Order> selectByCondition(String queryString);
    //10.根据id取消预约
    void cancelOrder(Integer id);
    //11.根据预约id查询预约信息
    Order findById(Integer id);
    //12.编辑预约信息
    void edit(Order order);
}
