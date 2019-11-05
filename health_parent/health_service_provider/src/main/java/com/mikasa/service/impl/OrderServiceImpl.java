package com.mikasa.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mikasa.constant.MessageConstant;
import com.mikasa.dao.MemberDao;
import com.mikasa.dao.OrderDao;
import com.mikasa.dao.OrderSettingDao;
import com.mikasa.entity.PageResult;
import com.mikasa.entity.QueryPageBean;
import com.mikasa.entity.Result;
import com.mikasa.pojo.Member;
import com.mikasa.pojo.Order;
import com.mikasa.pojo.OrderSetting;
import com.mikasa.service.OrderService;
import com.mikasa.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 体检预约服务
 */
@Service(interfaceClass = OrderService.class)
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderSettingDao orderSettingDao;
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private OrderDao orderDao;
    //1.预约
    @Override
    public Result order(Map map) throws Exception {
        //1.检查用户所选日期，是否已提前进行了预约设置
        String orderDate = (String) map.get("orderDate");
        System.out.println(orderDate);
        OrderSetting orderSetting = orderSettingDao.findOrderSettingByOrderDate(DateUtils.parseString2Date(orderDate));
        if (orderSetting == null || orderSetting.getNumber() == 0){
            //指定日期未进行预约设置，无法预约
            return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }

        //2.检查用户所选预约日期是否已约满
        int reservations = orderSetting.getReservations();//已预约人数
        int number = orderSetting.getNumber();//可预约人数
        if (reservations >= number){
            return new Result(false,MessageConstant.ORDER_FULL);
        }

        //3、检查用户是否重复预约（同一个用户在同一天预约了同一个套餐），如果是重复预约则无法完成再次预约
        String telephone = (String) map.get("telephone");
        Member member = memberDao.findMemberByTel(telephone);
        if (member != null){
            //带有个条件的进行查询改预约单，如果有了相符合的预约，则重复预约
            Integer memberId = member.getId();//会员id
            System.out.println(memberId);
            Date date = DateUtils.parseString2Date(orderDate);//预约日期
            String setmealId = (String) map.get("setmealId");//套餐ID

            Order order = new Order(memberId, date, Integer.parseInt(setmealId));
            List<Order> orderList = orderDao.findOrderByCondition(order);
            if (orderList != null && orderList.size() > 0){
                //查询到相同预约，说明用户重复预约
                return new Result(false,MessageConstant.HAS_ORDERED);
            }
        }else {
            //4.检查用户是否是会员，用户不是会员，创建会员，设置数据
            member = new Member();
            member.setName((String) map.get("name"));
            member.setPhoneNumber(telephone);
            member.setIdCard((String) map.get("idCard"));
            member.setSex((String) map.get("sex"));
            member.setRegTime(new Date());
            //添加会员
            memberDao.add(member);
        }

        //5.预约成功，更新当日已预约的人数
        Order order = new Order();
        order.setMemberId(member.getId());//设置会员ID
        System.out.println("会员ID："+member.getId());
        order.setOrderDate(DateUtils.parseString2Date(orderDate));//预约日期
        order.setOrderType((String) map.get("orderType"));//预约类型
        order.setOrderStatus(Order.ORDERSTATUS_NO);//到诊状态
        order.setSetmealId(Integer.parseInt((String) map.get("setmealId")));//套餐ID
        //5.1添加预约
        orderDao.add(order);

        //5.2更新已预约人数
        orderSetting.setReservations(orderSetting.getReservations() + 1);//设置已预约人数+1
        orderSettingDao.editReservationsByOrderDate(orderSetting);

        System.out.println("预约id"+order.getId());
        return new Result(true,MessageConstant.ORDER_SUCCESS,order.getId());
    }

    //6.根据预约id查询预约信息
    @Override
    public Map findOrderDetailByOrderId(Integer id) throws Exception {
        Map map = orderDao.findOrderDetailByOrderId(id);
        if (map != null){
            //处理日期格式
            Date orderDate = (Date) map.get("orderDate");
            map.put("orderDate",DateUtils.parseDate2String(orderDate));
        }
        return map;
    }

    //4，分页查询预约数据
    @Override
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        //1.获取请求参数
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        //2.通过请求参数分页
        PageHelper.startPage(currentPage,pageSize);
        //3.有条件的查询
        Page<Order> page = orderDao.selectByCondition(queryString);
        //4.封装页面需要的返回结果
        return new PageResult(page.getTotal(),page.getResult());
    }

    //5.根据id取消预约
    @Override
    public void cancelOrder(Integer id) {
        orderDao.cancelOrder(id);
    }
    //6.新增预约
    @Override
    public void add(Order order) {
        orderDao.add(order);
    }

    //7根据预约id查询预约信息
    @Override
    public Order findById(Integer id) {
        return orderDao.findById(id);
    }

    //8.编辑雨夜信息
    @Override
    public void edit(Order order) {
        orderDao.edit(order);
    }
}
