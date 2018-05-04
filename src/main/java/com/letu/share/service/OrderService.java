package com.letu.share.service;

import com.letu.share.dao.OrderDAO;
import com.letu.share.dao.OrderDetailDAO;
import com.letu.share.dao.UserDAO;
import com.letu.share.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.Date;
import java.util.List;

@Service
public class OrderService {
    private Jedis jedis = new Jedis();

    @Autowired
    OrderDAO orderDAO;

    @Autowired
    OrderDetailDAO orderDetailDAO;

    @Autowired
    UserDAO userDAO;

    @Autowired
    ShopCartService shopCartService;

    @Autowired
    CommodityService commodityService;

    // 添加订单
    public void addOrder(Order order, String username) {
        Date date = new Date();
        order.setId(date.getTime());

        User user = userDAO.selectByName(username);
        order.setBuyerId(user.getId());
        ShopCart shopCart = shopCartService.selectShopCartFromRedis(username);
        List<BuyerItem> itemList = shopCart.getItemList();
        for (BuyerItem buyerItem : itemList) {
            buyerItem.setCommodity(commodityService.getCommodityById(buyerItem.getCommodity().getId()));
        }
        order.setTotalPrice(shopCart.getTotalPrice());
        order.setCreatedDate(date);
        order.setState(0);   //0表示提交订单，1表示订单完成

        orderDAO.addOrder(order);

        // 添加订单详情
        for (BuyerItem buyerItem : itemList) {
            OrderDetail detail = new OrderDetail();
            detail.setOrderId(order.getId());
            Commodity commodity = commodityService.getCommodityById(buyerItem.getCommodity().getId());
            detail.setCommodityId(commodity.getId());
            detail.setCommodityName(commodity.getName());
            detail.setAmount(buyerItem.getAmount());

            orderDetailDAO.addOrderDetail(detail);
        }
        jedis.del("shopCart:" + username);
    }

}
