package com.letu.share.controller;

import com.alibaba.fastjson.JSONObject;
import com.letu.share.model.BuyerItem;
import com.letu.share.model.Commodity;
import com.letu.share.model.Order;
import com.letu.share.model.ShopCart;
import com.letu.share.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class ShoppingController {

    @Autowired
    CommodityService commodityService;

    @Autowired
    ShopCartService cartService;

    @Autowired
    OrderService orderService;

    @Autowired
    LoginTicketService loginTicketService;

    @Autowired
    UserService userService;

    // 加入购物车
    @RequestMapping(value = {"/shopping/shopcart/{commodityId}/{amount}"})
    public String shopCart(Model model,
                           @PathVariable("commodityId") Integer commodityId,
                           @PathVariable("amount") Integer amount,
                           HttpServletRequest request,
                           HttpServletResponse response) {
        ShopCart shopCart = null;
        // 获取Cookie中的购物车
        Cookie[] cookies = request.getCookies();
        try {
            if (cookies != null && cookies.length > 0) {
                for (Cookie cookie : cookies) {
                    if ("SHOP_CART".equals(cookie.getName())) {
                        String temp = new String(java.net.URLDecoder.decode(cookie.getValue(), "UTF-8"));
//                        System.out.println("second:" + temp);
                        ShopCart shopCart1 = JSONObject.parseObject(temp, ShopCart.class);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        // Cookie中没有购物车，创建购物车对象
        if (shopCart == null) {
            shopCart = new ShopCart();
        }
        // 将当前商品添加到购物车
        if (commodityId != null && amount != null) {
            Commodity commodity = new Commodity();
            commodity.setId(commodityId);
            BuyerItem buyerItem = new BuyerItem();
            buyerItem.setCommodity(commodity);
            buyerItem.setAmount(amount);
            shopCart.addItem(buyerItem);
        }

        // 判断用户是否登陆
        String username = getUserNameByTicket(cookies);
        System.out.println("username:" + username);
        if (username != null) {
            // 登陆了, 将购物车追加到Redis中
            cartService.insertShopCartToRedis(shopCart, username);
            Cookie cookie = new Cookie("SHOP_CART", null);
            cookie.setPath("/");
            cookie.setMaxAge(-0);
            response.addCookie(cookie);
        } else {
            // 没登陆，将购物车保存到cookie中
            try {
                String jsonString = JSONObject.toJSONString(shopCart);
                jsonString = java.net.URLEncoder.encode(jsonString, "UTF-8");
                Cookie cookie = new Cookie("SHOP_CART", jsonString);
                cookie.setPath("/");
                cookie.setMaxAge(24 * 60 * 60);
                response.addCookie(cookie);
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }

        return "redirect:/shopping/toCart";
//        return "redirect:/";
    }


    // 购物车界面
    @RequestMapping(value = {"/shopping/toCart"})
    public String toCart(Model model,
                         HttpServletRequest request,
                         HttpServletResponse response) {
        ShopCart shopCart = null;

        // 获取cookie中的购物车
        try {
            Cookie[] cookies = request.getCookies();
            if (cookies != null && cookies.length > 0) {
                for (Cookie cookie : cookies) {
                    if ("SHOP_CART".equals(cookie.getName())) {
                        String temp = new String(java.net.URLDecoder.decode(cookie.getValue(), "UTF-8"));
                        shopCart = JSONObject.parseObject(temp, ShopCart.class);
                        break;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(e.toString());
        }

        // 判断用户是否登陆
        String username = getUserNameByTicket(request.getCookies());
        System.out.println("购物车：username--" + username);
        if (username != null) {

            // 登陆，并且购物车有东西，则将购物车保存达到redis中
            if (shopCart != null) {
                cartService.insertShopCartToRedis(shopCart, username);
                Cookie cookie = new Cookie("SHOP_CART", null);
                cookie.setPath("/");
                cookie.setMaxAge(-0);
                response.addCookie(cookie);
            }
            // 取出购物车

            shopCart = cartService.selectShopCartFromRedis(username);
        }
        if (shopCart == null) {
            shopCart = new ShopCart();
        }
        // 将完整的商品信息添加到购物车
        List<BuyerItem> itemList = shopCart.getItemList();
        if (itemList.size() > 0) {
            for (BuyerItem buyerItem : itemList) {
                buyerItem.setCommodity(commodityService.getCommodityById(buyerItem.getCommodity().getId()));
            }
        }
        model.addAttribute("itemList", shopCart.getItemList());
        return "shopCart";
    }

    // 结算页面
    @RequestMapping(value = {"/shopping/truebuy"})
    public String trueBuy(Model model,
                          String[] commodityIds,
                          HttpServletRequest request,
                          HttpServletResponse response) {
        String username = null;
        ShopCart shopCart = cartService.selectShopCartFromRedisByCommodiytId(commodityIds, username);
        List<BuyerItem> itemList = shopCart.getItemList();
        if (itemList != null) {
            Boolean flag = true;

        }
        return "order";
    }

    // 提交订单
    @RequestMapping(value = {"/shopping/submitOrder"})
    public String submitOrder(Model model,
                              HttpServletRequest request,
                              HttpServletResponse response) {
        String username = null;
        Order order = null;
        orderService.addOrder(order, username);
        return "";
    }

    // 获取用户名
    public String getUserNameByTicket(Cookie[] cookies) {
        String ticket = null;
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if ("ticket".equals(cookie.getName())) {
                    ticket = cookie.getValue();
                }
            }
        }
        if (ticket != null) {
            int userId = loginTicketService.getUserIdByTicket(ticket);
            return userService.getUserById(userId).getName();
        }
        return null;
    }
}
