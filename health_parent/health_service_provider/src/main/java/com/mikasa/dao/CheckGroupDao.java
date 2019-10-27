package com.mikasa.dao;
import com.github.pagehelper.Page;
import com.mikasa.pojo.CheckGroup;

import java.util.List;
import java.util.Map; /**
 * 预约管理-检查项管理-Dao层
 */
public interface CheckGroupDao {
    //1.新增检查组
    void add(CheckGroup checkGroup);
    //2.设置检查组和检查项关联关系
    void setCheckGroupAndCheckItem(Map map);
    //3.分页查询检查组
    Page<CheckGroup> selectByCondition(String queryString);
    //4.根据检查组id查询检查组
    CheckGroup findById(Integer id);
    //5.根据当前检查组id查询关联的检查项id集合
    List<Integer> findCheckItemIdsByCheckGroupId(Integer id);
    //6.编辑检查组
    void edit(CheckGroup checkGroup);
    //7.通过检查组id删除关联的检查项
    void deleteAssociation(Integer checkGroupId);
    //8.根据检查组id删除检查组
    void deleteByCheckGroupId(Integer id);
    //9.查询所有的检查组信息
    List<CheckGroup> findAll();
}
