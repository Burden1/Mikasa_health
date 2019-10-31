package com.mikasa.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.mikasa.constant.MessageConstant;
import com.mikasa.constant.RedisConstant;
import com.mikasa.entity.PageResult;
import com.mikasa.entity.QueryPageBean;
import com.mikasa.entity.Result;
import com.mikasa.pojo.Setmeal;
import com.mikasa.service.SetMealService;
import com.mikasa.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * 预约管理-套餐管理-Controller层
 */
@RestController
@RequestMapping("/setmeal")
public class SetMealController {
    @Reference
    private SetMealService setMealService;

    //注入jedisPool
    @Autowired
    private JedisPool jedisPool;

    //1.图片上传并预览
    @RequestMapping("/upload")
    public Result upload(@RequestParam("imgFile") MultipartFile imgFile){
        System.out.println(imgFile);
        //1.得到文件的原始路径
        String originalFilename = imgFile.getOriginalFilename();
        System.out.println(originalFilename);
        //2.获取扩展名.jpg
        int index = originalFilename.lastIndexOf(".");
        String extension = originalFilename.substring(index);
       //3.通过UUID拼接成新的文件名
        String fileName = UUID.randomUUID().toString()+extension;
        System.out.println(fileName);

        //把文件上传到七牛云服务器
        try {
            QiniuUtils.upload2Qiniu(imgFile.getBytes(),fileName);
            //文件上传到七牛云后把图片保存到redis里面
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES,fileName);
            return new Result(true,MessageConstant.PIC_UPLOAD_SUCCESS,fileName);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }
    }

    //2.新增套餐
    @RequestMapping("/add")
    public Result add(@RequestBody Setmeal setmeal,Integer[] checkgroupIds){
        try {
            setMealService.add(setmeal, checkgroupIds);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.ADD_SETMEAL_FAIL);
        }
        return new Result(true,MessageConstant.ADD_SETMEAL_SUCCESS);
    }

    //3.分页查询套餐
    @RequestMapping("/pageQuery")
    public PageResult pageQuery(@RequestBody QueryPageBean queryPageBean){
        return setMealService.pageQuery(queryPageBean);
    }

    //4.根据套餐id查询套餐信息
    @RequestMapping("/findById")
    public Result findById(Integer id){
        try {
            System.out.println(id);
            Setmeal setmeal = setMealService.findById(id);
            return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }

    //5.根据套餐id查询其所关联的检查组id集合
    @RequestMapping("/findCheckGroupIdsBySetMealId")
    public Result findCheckGroupIdsBySetMealId(Integer id){
        try {
            List<Integer> checkGroupIds = setMealService.findCheckGroupIdsBySetMealId(id);
            return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkGroupIds);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }

    //6.编辑套餐
    @RequestMapping("/edit")
    public Result edit(@RequestBody Setmeal setmeal,Integer[] checkgroupIds){
        try {
            setMealService.edit(setmeal, checkgroupIds);
            return new Result(true,MessageConstant.EDIT_SETMEAL_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_SETMEAL_FAIL);
        }
    }

    //7.根据套餐id删除套餐
    @RequestMapping("/delete")
    public Result delete(Integer id){
        try {
            setMealService.deleteBySetMealId(id);
            return new Result(true,MessageConstant.DELETE_SETMEAL_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_SETMEAL_FAIL);
        }
    }

    //8.根据id查询图片路径
    @RequestMapping("/findImgUrl")
    public Result findImgUrl(Integer id){
        try {
            String imgUrl = setMealService.findImgUrl(id);
            return new Result(true,MessageConstant.QUERY_IMAGEURL_SUCCESS,imgUrl);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_IMAGEURL_FAIL);
        }

    }
}
