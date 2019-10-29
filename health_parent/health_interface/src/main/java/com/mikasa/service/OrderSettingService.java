package com.mikasa.service;
import com.mikasa.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

/**
 * 预约管理-预约设置-服务接口层
 */

public interface OrderSettingService {
    //1.批量导入预约设置信息
    void add(List<OrderSetting> data);
    //2.根据预约日期查询预约数据
    List<Map> getOrderSettingByMonth(String date);
    //3.基于日历实现预约设置
    void editNumberByDate(OrderSetting orderSetting);
}
