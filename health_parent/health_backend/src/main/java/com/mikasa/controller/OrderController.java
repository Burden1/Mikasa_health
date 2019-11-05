package com.mikasa.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.mikasa.constant.MessageConstant;
import com.mikasa.entity.PageResult;
import com.mikasa.entity.QueryPageBean;
import com.mikasa.entity.Result;
import com.mikasa.pojo.Order;
import com.mikasa.service.OrderService;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 预约列表小模块
 */
@RestController
@RequestMapping("/orderlist")
public class OrderController {
    @Reference
    private OrderService orderService;

    //1.分页查询预约信息
    @RequestMapping("/pageQuery")
    public PageResult pageQuery(@RequestBody QueryPageBean queryPageBean){
        return orderService.pageQuery(queryPageBean);
    }

    //2.取消预约
    @RequestMapping("/cancelOrder")
    public Result cancelOrder(Integer id){
        try {
            orderService.cancelOrder(id);
            return new Result(true,MessageConstant.CANCEL_ORDER_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.CANCEL_ORDER_FAIL);
        }
    }

    //3.新增预约
    @RequestMapping("/add")
    public Result add(@RequestBody Order order){
        try {
            System.out.println(order);
            orderService.add(order);
            return new Result(true,MessageConstant.ADD_ORDER_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.ADD_ORDER_FAIL);
        }
    }

    //4.通过预约id查询预约信息
    @RequestMapping("/findById")
    public Result findById(Integer id){
        try {
            Order order = orderService.findById(id);
            return new Result(true,MessageConstant.QUERY_ORDER_SUCCESS,order);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_ORDER_FAIL);
        }
    }

    //5.编辑预约信息
    @RequestMapping("/edit")
    public Result edit(@RequestBody Order order){
        try {
            orderService.edit(order);
            return new Result(true,MessageConstant.EDIT_ORDER_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_ORDER_ERROR);
        }
    }
}
