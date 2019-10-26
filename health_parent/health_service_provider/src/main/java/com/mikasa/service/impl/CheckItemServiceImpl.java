package com.mikasa.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mikasa.dao.CheckItemDao;
import com.mikasa.entity.PageResult;
import com.mikasa.entity.QueryPageBean;
import com.mikasa.pojo.CheckItem;
import com.mikasa.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 预约管理-检查项管理-服务接口实现层
 */
@Service(interfaceClass = CheckItemService.class)
@Transactional
public class CheckItemServiceImpl implements CheckItemService{
    @Autowired
    private CheckItemDao checkItemDao;

    //1.新增检查项
    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
    }

    //2.分页查询检查项
    @Override
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        //1.获取请求参数
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();

        //2.通过请求参数分页
        PageHelper.startPage(currentPage,pageSize);

        //3.有条件的查询
        Page<CheckItem> page = checkItemDao.selectByCondition(queryString);

        //4.封装页面需要的返回结果
        long total = page.getTotal();
        List<CheckItem> result = page.getResult();
        return new PageResult(total,result);
    }

    //3.根据检查项Id删除检查项
    @Override
    public void deleteByCheckItemId(Integer id) {
        //判断检查项id是否关联了检查组
        long count = checkItemDao.findCountByCheckItemId(id);
        if (count>0){
            //当前检查项关联了检查组,不能删除
            throw new RuntimeException("当前检查项被引用，无法删除");
        }
        checkItemDao.deleteByCheckItemId(id);
    }

    //4.根据检查项Id查询检查项数据
    @Override
    public CheckItem findByCheckItemId(Integer id) {
        return checkItemDao.findByCheckItemId(id);
    }

    //5.编辑检查项
    @Override
    public void edit(CheckItem checkItem) {
        checkItemDao.edit(checkItem);
    }

    //6.查询所有检查项
    @Override
    public List<CheckItem> findAll() {
        return checkItemDao.findAll();
    }
}
