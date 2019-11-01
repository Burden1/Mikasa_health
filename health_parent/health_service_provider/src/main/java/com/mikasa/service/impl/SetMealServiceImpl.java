package com.mikasa.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mikasa.constant.RedisConstant;
import com.mikasa.dao.SetMealDao;
import com.mikasa.entity.PageResult;
import com.mikasa.entity.QueryPageBean;
import com.mikasa.pojo.Setmeal;
import com.mikasa.service.SetMealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 预约管理-套餐管理-服务接口实现层
 */
@Service(interfaceClass = SetMealService.class)
@Transactional
public class SetMealServiceImpl implements SetMealService {
    @Autowired
    private SetMealDao setMealDao;

    @Autowired
    private JedisPool jedisPool;
    //1.新增套餐
    @Override
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        //1.新增套餐
        setMealDao.add(setmeal);
        //2.设置套餐和检查组多对多的关联关系
        Integer setmealId = setmeal.getId();
        this.setSetMealAndCheckGroup(setmealId,checkgroupIds);

        //在新增套餐后将图片保存到redis
        String fileName = setmeal.getImg();
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,fileName);
    }

    //2.分页查询套餐
    @Override
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();

        PageHelper.startPage(currentPage,pageSize);
        Page<Setmeal> page = setMealDao.selectByCondition(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }

    //3.根据套餐id查询套餐信息
    @Override
    public Setmeal findById(Integer id) {
        return setMealDao.findById(id);
    }
    //4.根据套餐id查询所关联的检查组id集合
    @Override
    public List<Integer> findCheckGroupIdsBySetMealId(Integer id) {
        return setMealDao.findCheckGroupIdsBySetMealId(id);
    }

    //5.编辑套餐
    @Override
    public void edit(Setmeal setmeal, Integer[] checkgroupIds) {
        //1.编辑套餐基本信息
        setMealDao.edit(setmeal);
        //2.删除与检查组id的关联
        Integer setmealId = setmeal.getId();
        setMealDao.deleteAssociation(setmealId);
        //3.重新建立关联
        this.setSetMealAndCheckGroup(setmealId,checkgroupIds);
    }

    //6.通过套餐id删除套餐
    @Override
    public void deleteBySetMealId(Integer id) {
        //1.先删除套餐和检查组的关联关系
        setMealDao.deleteAssociation(id);
        //2.再删除套餐
        setMealDao.deleteBySetMealId(id);
    }

    //7，查询所有套餐信息
    @Override
    public List<Setmeal> findAllSetmeal() {
        return setMealDao.findAllSetmeal();
    }

    //8.根据套餐id查询图片
    public String findImgUrl(Integer id) {
        return setMealDao.findImgUrl(id);
    }

    //9.根据套餐id查询套餐详情
    public Setmeal findSetmealDetail(int id) {
        return setMealDao.findSetmealDetail(id);
    }

    //10.查询套餐对应预约人数
    @Override
    public List<Map<String, Object>> findSetmealCount() {
        return setMealDao.findSetmealCount();
    }

    //设置套餐和检查组多对多的关联关系
    private void setSetMealAndCheckGroup(Integer setmealId, Integer[] checkgroupIds) {
        if (checkgroupIds != null && checkgroupIds.length > 0){
            for (Integer checkgroupId :checkgroupIds){
                Map<String,Integer> map = new HashMap<>();
                map.put("setmealId",setmealId);
                map.put("checkgroupId",checkgroupId);
                setMealDao.setSetMealAndCheckGroup(map);
            }
        }
    }
}
