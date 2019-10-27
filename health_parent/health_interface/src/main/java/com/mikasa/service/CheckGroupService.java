package com.mikasa.service;

import com.mikasa.entity.PageResult;
import com.mikasa.entity.QueryPageBean;
import com.mikasa.pojo.CheckGroup;

import java.util.List;

/**
 * 预约管理-检查组管理-服务接口层
 */
public interface CheckGroupService {
    //1.新增检查组和与检查组相关联的检查项id
    void add(CheckGroup checkGroup, Integer[] checkItemIds);
    //2.分页查询检查组
    PageResult pageQuery(QueryPageBean queryPageBean);
    //3.根据检查组id查询检查组
    CheckGroup findById(Integer id);
    //4.根据当前检查组id查询关联的检查项id集合
    List<Integer> findCheckItemIdsByCheckGroupId(Integer id);
    //5.编辑检查组
    void edit(CheckGroup checkGroup, Integer[] checkitemIds);
    //6.根据检查组id删除检查组
    void deleteByCheckGroupId(Integer id);
    //7.查询所有的检查组信息
    List<CheckGroup> findAll();
}
