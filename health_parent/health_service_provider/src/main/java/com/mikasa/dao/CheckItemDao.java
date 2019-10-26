package com.mikasa.dao;

import com.github.pagehelper.Page;
import com.mikasa.pojo.CheckItem;

import java.util.List;

/**
 * 预约管理-检查项管理-Dao层
 */
public interface CheckItemDao {
    //1.新增检查项
    void add(CheckItem checkItem);
    //2.有条件的查询检查项
    Page<CheckItem> selectByCondition(String queryString);
    //3.根据检查项id统计数据量
    long findCountByCheckItemId(Integer id);
    //4.通过检查项id删除检查项
    void deleteByCheckItemId(Integer id);
    //5.根据检查项id查询检查项
    CheckItem findByCheckItemId(Integer id);
    //6.编辑检查项
    void edit(CheckItem checkItem);
    //7.查询所有检查项
    List<CheckItem> findAll();
}
