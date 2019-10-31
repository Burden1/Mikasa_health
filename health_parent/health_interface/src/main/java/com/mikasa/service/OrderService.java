package com.mikasa.service;

import com.mikasa.entity.Result;

import java.util.Map;

/**
 * 体检预约 服务
 */
public interface OrderService {
    //1.提交体检预约,并返回预约id
    Result order(Map map) throws Exception;
    //2.根据预约id查询预约信息
    Map findOrderDetailByOrderId(Integer id)throws Exception;
}
