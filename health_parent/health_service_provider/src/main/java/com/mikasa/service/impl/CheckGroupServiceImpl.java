package com.mikasa.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mikasa.dao.CheckGroupDao;
import com.mikasa.entity.PageResult;
import com.mikasa.entity.QueryPageBean;
import com.mikasa.pojo.CheckGroup;
import com.mikasa.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 预约管理-检查项管理-服务接口实现层
 */
@Service(interfaceClass = CheckGroupService.class)
@Transactional
public class CheckGroupServiceImpl implements CheckGroupService{
    @Autowired
    private CheckGroupDao checkGroupDao;

    //1.新增检查组和检查组相关联的检查项ID
    @Override
    public void add(CheckGroup checkGroup, Integer[] checkItemIds) {
        //1.新增检查组,操作t_checkgroup表
        checkGroupDao.add(checkGroup);
        //2.设置检查组与检查项多对多的关联关系，操作中间表t_checkgroup_checkitem
        Integer checkGroupId = checkGroup.getId();
        System.out.println("检查组ID"+checkGroupId);
        System.out.println("检查项id"+checkItemIds);
        if(checkItemIds != null && checkItemIds.length >0){
            for (Integer checkItemid : checkItemIds) {
                System.out.println("检查项id"+checkItemid);
                Map<String, Integer> map = new HashMap<>();
                map.put("checkgroupId", checkGroupId);
                map.put("checkitemId", checkItemid);
                checkGroupDao.setCheckGroupAndCheckItem(map);
            }
        }
    }

    //2.分页查询检查组
    @Override
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();

        PageHelper.startPage(currentPage,pageSize);
        //有条件的查询;
        Page<CheckGroup> page = checkGroupDao.selectByCondition(queryString);
        long total = page.getTotal();
        List<CheckGroup> result = page.getResult();
        return new PageResult(total,result);
    }

    //3.根据检查组id查询检查组
    @Override
    public CheckGroup findById(Integer id) {
        return checkGroupDao.findById(id);
    }

    //4.根据当前检查组id查询关联的检查项id集合
    @Override
    public List<Integer> findCheckItemIdsByCheckGroupId(Integer id) {
        return checkGroupDao.findCheckItemIdsByCheckGroupId(id);
    }

    //5.编辑检查组
    @Override
    public void edit(CheckGroup checkGroup, Integer[] checkitemIds) {
        //1.编辑检查组基本信息
        checkGroupDao.edit(checkGroup);
        //2.解除之前对检查项的关联
        Integer checkGroupId = checkGroup.getId();
        checkGroupDao.deleteAssociation(checkGroupId);
        //3.重新建立关联
        if (checkitemIds != null && checkitemIds.length > 0){
            for (Integer checkItemid : checkitemIds){
                Map<String, Integer> map = new HashMap<>();
                map.put("checkgroupId", checkGroupId);
                map.put("checkitemId", checkItemid);
                checkGroupDao.setCheckGroupAndCheckItem(map);
            }
        }
    }

    //6.根据检查组id删除检查组
    @Override
    public void deleteByCheckGroupId(Integer id) {
        //1.首先删除中间表，检查组关联的检查项
        checkGroupDao.deleteAssociation(id);
        //2.再删除检查组
        checkGroupDao.deleteByCheckGroupId(id);
    }
}
