package com.mikasa.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.aliyuncs.exceptions.ClientException;
import com.mikasa.constant.MessageConstant;
import com.mikasa.constant.RedisMessageConstant;
import com.mikasa.entity.Result;
import com.mikasa.utils.SMSUtils;
import com.mikasa.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

/*
发送验证码
 */
@RestController
@RequestMapping("/validateCode")
public class ValicateCodeController {
    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/sendCode2Telephone")
    public Result sendCode2Telephone(String telephone){
        //1.随机生成四位数字的验证码
        System.out.println(telephone);
        Integer validateCode = ValidateCodeUtils.generateValidateCode(4);
        System.out.println(validateCode);
        try {
            //2.发送短信
            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone,validateCode.toString());
            //3.将验证码保存到redis中5分钟
            jedisPool.getResource().setex(telephone+ RedisMessageConstant.SENDTYPE_ORDER,300,validateCode.toString());
            return new Result(true,MessageConstant.SEND_VALIDATECODE_SUCCESS);
        } catch (ClientException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
    }
}
