package com.mikasa.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.mikasa.constant.MessageConstant;
import com.mikasa.entity.Result;
import com.mikasa.pojo.Setmeal;
import com.mikasa.service.SetMealService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 移动端-套餐页面-Controller层
 */
@RestController
@RequestMapping("/setmeal")
public class SetMealController {
    @Reference
    private SetMealService setMealService;

    //1.查询所有套餐信息
    @RequestMapping("/findAllSetmeal")
    public Result findAllSetmeal(){
        try {
            List<Setmeal> allSetmeal = setMealService.findAllSetmeal();
            return new Result(true,MessageConstant.GET_SETMEAL_LIST_SUCCESS,allSetmeal);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_SETMEAL_LIST_FAIL);
        }
    }

    //2.根据套餐id查询套餐详情(套餐基本信息，检查组信息，检查项信息，）
    @RequestMapping("/findSetmealDetail")
    public Result findSetmealDetail(int id){
        try {
            System.out.println(id);
            Setmeal setmealDetail = setMealService.findSetmealDetail(id);
            System.out.println(setmealDetail);
            return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,setmealDetail);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }
}
