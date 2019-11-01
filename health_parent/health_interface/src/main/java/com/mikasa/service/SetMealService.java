package com.mikasa.service;
import com.mikasa.entity.PageResult;
import com.mikasa.entity.QueryPageBean;
import com.mikasa.pojo.Setmeal;

import java.util.List;
import java.util.Map;

/**
 * 预约管理-套餐管理-服务接口层
 */
public interface SetMealService {
    //1.新增套餐
    void add(Setmeal setmeal, Integer[] checkgroupIds);
    //2.分页查询套餐
    PageResult pageQuery(QueryPageBean queryPageBean);
    //3.根据套餐id查询套餐信息
    Setmeal findById(Integer id);
    //4.根据套餐id查询所关联的检查组集合
    List<Integer> findCheckGroupIdsBySetMealId(Integer id);
    //5.编辑套餐
    void edit(Setmeal setmeal, Integer[] checkgroupIds);
    //6.通过套餐id删除套餐
    void deleteBySetMealId(Integer id);
    //7.查询所有套餐信息
    List<Setmeal> findAllSetmeal();
    //8.根据套餐id查询图片路径
    String findImgUrl(Integer id);
    //9.根据套餐id查询套餐详情
    Setmeal findSetmealDetail(int id);
    //10.查找预约套餐的人数
    List<Map<String,Object>> findSetmealCount();
}
