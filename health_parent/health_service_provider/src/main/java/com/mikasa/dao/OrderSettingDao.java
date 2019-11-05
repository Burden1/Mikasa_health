package com.mikasa.dao;
import com.github.pagehelper.Page;
import com.mikasa.pojo.CheckItem;
import com.mikasa.pojo.Order;
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
    //5.通过预约日期查找是否已经进行了预约设置
    OrderSetting findOrderSettingByOrderDate(Date orderDate);
    //6.更新当日预约设置已预约人数
    void editReservationsByOrderDate(OrderSetting orderSetting);
}
