package com.mikasa.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.mikasa.constant.MessageConstant;
import com.mikasa.constant.RedisMessageConstant;
import com.mikasa.entity.Result;
import com.mikasa.pojo.Order;
import com.mikasa.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Map;

/**
 * 体检预约
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Reference
    private OrderService orderService;
    @Autowired
    private JedisPool jedisPool;

    //1.提交预约
    @RequestMapping("/submit")
    public Result submit(@RequestBody Map map){
        //1.从Redis中获取保存的验证码，与用户输入验证码比较
        String telephone = (String) map.get("telephone");
        System.out.println(telephone);
        String validateCodeInRedis = jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_ORDER);
        System.out.println(validateCodeInRedis);
        String validateCode = (String) map.get("validateCode");
        System.out.println(validateCode);
        if (validateCode != null && validateCodeInRedis != null && validateCode.equals(validateCodeInRedis)){
            //设置预约类型，分为微信，电话预约
            map.put("orderType", Order.ORDERTYPE_WEIXIN);
            //调用Dubbo远程调用服务实现在线预约业务处理
            try {
                Result result = orderService.order(map);
                System.out.println("result为"+result);
                return result;
            }catch (Exception e){
                e.printStackTrace();
                return new Result(false,MessageConstant.ORDER_FAIL);
            }
        }else {
            //验证码校验失败，
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
    }

    //2.通过预约id查询预约信息
    @RequestMapping("/findOrderDetailByOrderId")
    public Result findOrderDetailByOrderId(Integer id){
        try {
            Map map = orderService.findOrderDetailByOrderId(id);
            return new Result(true,MessageConstant.QUERY_ORDER_SUCCESS,map);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_ORDER_FAIL);
        }
    }
}
