package com.mikasa.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.mikasa.constant.MessageConstant;
import com.mikasa.constant.RedisMessageConstant;
import com.mikasa.entity.Result;
import com.mikasa.pojo.Member;
import com.mikasa.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * 会员Controller层
 */
@RestController
@RequestMapping("/member")
public class MemberController {
    @Autowired
    private JedisPool jedisPool;
    @Reference
    private MemberService memberService;

    @RequestMapping("/login")
    public Result login(@RequestBody Map map, HttpServletResponse response){
        //1.校验用户名短信验证码是否正确，错误则登录失败
        String telephone = (String) map.get("telephone");
        System.out.println(telephone);
        String validateCodeInRedis = jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_LOGIN);
        System.out.println(validateCodeInRedis);
        String validateCode = (String) map.get("validateCode");
        System.out.println(validateCode);
        //校验验证码，不成功
        if (validateCode != null && validateCodeInRedis != null && validateCode.equals(validateCodeInRedis)) {
            //2.若验证码正确，判断当前用户是否为会员，
            Member member = memberService.findMemberByTel(telephone);
            //不是会员，则自动完成注册
            if (member == null) {
                member.setRegTime(new Date());
                member.setPhoneNumber(telephone);
                //添加会员
                memberService.add(member);
            }

            //3.向客户端写入cookie，内容为手机号
            Cookie cookie = new Cookie("login_member_telephone", telephone);
            cookie.setPath("/");//cookit在该域名下的路径
            cookie.setMaxAge(60*60*24*30);//设置存活时间为30天
            response.addCookie(cookie);

            //4.将会员信息保存在redis，使用手机号作为key，保存时长为30分钟
            String json = JSON.toJSON(member).toString();
            System.out.println("会员信息" + json);
            jedisPool.getResource().setex(telephone, 60 * 30, json);
            return new Result(true,MessageConstant.LOGIN_SUCCESS);
        }else {
            //验证码校验错误
            return new Result(false,MessageConstant.VALIDATECODE_ERROR);
        }
    }
}
