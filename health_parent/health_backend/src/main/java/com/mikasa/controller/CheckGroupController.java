package com.mikasa.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.mikasa.constant.MessageConstant;
import com.mikasa.entity.PageResult;
import com.mikasa.entity.QueryPageBean;
import com.mikasa.entity.Result;
import com.mikasa.pojo.CheckGroup;
import com.mikasa.service.CheckGroupService;
import org.apache.poi.util.IntegerField;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 预约管理-检查组管理-Controller层
 */
@RestController
@RequestMapping("/checkgroup")
public class CheckGroupController {
    @Reference//远程调用服务层
    private CheckGroupService checkGroupService;

    //1.新增检查组和与检查组相关联的检查项id
    @RequestMapping("/add")
    public Result add(@RequestBody CheckGroup checkGroup,Integer[] checkitemIds){
        try{
            checkGroupService.add(checkGroup,checkitemIds);
            System.out.println(checkitemIds);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL);//新增失败
        }
        return new Result(true,MessageConstant.ADD_CHECKGROUP_SUCCESS);//新增成功
    }

    //2.分页查询检查组
    @RequestMapping("/pageQuery")
    public PageResult pageQuery(@RequestBody QueryPageBean queryPageBean){
        return checkGroupService.pageQuery(queryPageBean);
    }

    //3.根据检查组id查询检查组
    @RequestMapping("/findById")
    public Result findById(Integer id){
        try {
            CheckGroup checkGroup = checkGroupService.findById(id);
            return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkGroup);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }

    //4.根据当前检查组id查询关联的检查项
    @RequestMapping("/findCheckItemIdsByCheckGroupId")
    public Result findCheckItemIdsByCheckGroupId(Integer id){
        try {
            List<Integer> idList = checkGroupService.findCheckItemIdsByCheckGroupId(id);
            return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,idList);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }

    //5.编辑检查组
    @RequestMapping("/edit")
    public Result edit(@RequestBody CheckGroup checkGroup,Integer[] checkitemIds){
        try {
            checkGroupService.edit(checkGroup, checkitemIds);
        }catch (Exception e){
            return new Result(false,MessageConstant.EDIT_CHECKGROUP_FAIL);
        }
        return new Result(true,MessageConstant.EDIT_CHECKGROUP_SUCCESS);
    }

    //6.根据检查组id删除检查组
    @RequestMapping("/delete")
    public Result delete(Integer id){
        try {
            checkGroupService.deleteByCheckGroupId(id);
        }catch (Exception e){
            return new Result(false,MessageConstant.DELETE_CHECKGROUP_FAIL);
        }
        return new Result(true,MessageConstant.DELETE_CHECKGROUP_SUCCESS);
    }
}
