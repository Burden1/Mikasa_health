package com.mikasa.service;

import com.mikasa.entity.PageResult;
import com.mikasa.entity.QueryPageBean;
import com.mikasa.pojo.CheckItem;

import java.util.List;

/**
 * 预约管理-检查项管理-服务接口层
 */
public interface CheckItemService {
    //1.新增检查项
    void add(CheckItem checkItem);
    //2.分页查询检查项
    PageResult pageQuery(QueryPageBean queryPageBean);
    //3.根据检查项id删除检查项
    void deleteByCheckItemId(Integer id);
    //4.根据检查项id查询检查项数据
    CheckItem findByCheckItemId(Integer id);
    //5.编辑检查项
    void edit(CheckItem checkItem);
    //6.查询所有检查项
    List<CheckItem> findAll();
}
