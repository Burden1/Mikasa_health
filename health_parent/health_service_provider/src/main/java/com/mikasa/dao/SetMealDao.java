package com.mikasa.dao;

import com.github.pagehelper.Page;
import com.mikasa.pojo.Setmeal;

import java.util.List;
import java.util.Map;

/**
 * 预约管理-套餐管理-Dao层
 */
public interface SetMealDao {
    //1.新增套餐
    void add(Setmeal setmeal);
    //2.设置套餐Id与检查组id的关联关系
    void setSetMealAndCheckGroup(Map<String, Integer> map);
    //3.有条件的查询套餐
    Page<Setmeal> selectByCondition(String queryString);
    //4.通过套餐id查询套餐信息
    Setmeal findById(Integer id);
    //5.根据套餐id查询其所关联的检查组id集合
    List<Integer> findCheckGroupIdsBySetMealId(Integer id);
    //6.编辑套餐信息
    void edit(Setmeal setmeal);
    //7.根据套餐id删除关联的检查组
    void deleteAssociation(Integer setmealId);
    //8.根据套餐id删除套餐数据
    void deleteBySetMealId(Integer id);
    //9.查询所有套餐信息
    List<Setmeal> findAllSetmeal();
    //10.根据套餐id查询图片路径
    String findImgUrl(Integer id);
    //11根据套餐id查询套餐详情
    Setmeal findSetmealDetail(int id);
    //12.查询套餐对应预约人数
    List<Map<String,Object>> findSetmealCount();
}
