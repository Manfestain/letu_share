package com.letu.share.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.letu.share.model.BuyerItem;
import com.letu.share.model.Commodity;
import com.letu.share.model.ShopCart;
import com.letu.share.service.CommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;

@Controller
public class ShoppingController {

    @Autowired
    CommodityService commodityService;

    // 加入购物车
    @RequestMapping(value = {"/shopping/shopcart"}, method = {RequestMethod.POST})
    public String shopCart(Model model,
                           @RequestParam("commodity") String commodityId,
                           @RequestParam("amount") int amount,
                           HttpServletRequest request,
                           HttpServletResponse response) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)
        ShopCart shopCart = null;
        // 获取Cookie中的购物车
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if ("SHOP_CART".equals(cookie.getName())) {
                    shopCart = objectMapper.readValue(cookie.getValue(), shopCart.class);
                    break;
                }
            }
        }
        // Cookie中没有购物车，创建购物车对象
        if (shopCart == null) {
            shopCart = new ShopCart();
        }
        // 将当前商品添加到购物车
        if (commodityId != null && amount != null) {
            Commodity commodity = new Commodity();
            commodity.setUserId(commodityId);
            BuyerItem buyerItem = new BuyerItem();
            buyerItem.setCommodity(commodity);
            buyerItem.setAmount(amount);
            shopCart.addItem(buyerItem);
        }

        // 判断用户是否登陆
        String username = null;
        if (username != null) {
            // 登陆了, 将购物车追加到Redis中

        } else {
            // 没登陆，将购物车保存到cookie中
            Writer writer = new StringWriter();
            objectMapper.writeValue(writer, shopCart);
            Cookie cookie = new Cookie("SHOP_CART", writer.toString());
            cookie.setPath("/");
            cookie.setMaxAge(24*60*60);
            response.addCookie(cookie);
        }

        return "redirect:/shopping/toCart";
    }

    // 购物车和结算页
    @RequestMapping(value = {"/shopping/toCart"}, method = {RequestMethod.GET})
    public String toCart(Model model,
                         HttpServletRequest request,
                         HttpServletResponse response) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        ShopCart shopCart = null;

        // 获取cookie中的购物车
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if ("SHOP_CART".equals(cookie.getName())) {
                    shopCart = objectMapper.readValue(cookie.getValue(), ShopCart.class);
                    break;
                }
            }
        }
        String username = null;
        if (username != null) {
            if (shopCart == null) {

            }
            shopCart = null;
        }
        if (shopCart == null) {
            shopCart = new ShopCart();
        }
        List<BuyerItem> itemList = shopCart.getItemList();
        if (itemList.size() > 0) {
            for (BuyerItem buyerItem : itemList) {
                buyerItem.setCommodity(commodityService.getCommodityById(buyerItem.getCommodity().getId()));
            }
        }
        model.addAttribute("shopCart", shopCart);
        return "shopCart";
    }
}
