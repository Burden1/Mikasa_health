package com.mikasa.dao;
import com.mikasa.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 预约管理-预约设置-Dao层
 */
public interface OrderSettingDao {
    //1.通过预约日期查找预约数量
    long findCountByOrderDate(Date orderDate);
    //2.更新预约数量
    void editNumberByOrderDate(OrderSetting orderSetting);
    //3.添加预约设置
    void add(OrderSetting orderSetting);
    //4.根据预约日期范围查询预约数据
    List<OrderSetting> getOrderSettingByMonth(Map map);
}
