package com.letu.share.service;

import com.letu.share.model.BuyerItem;
import com.letu.share.model.ShopCart;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ShopCartService {

    // 取出Redis中的购物车
    public ShopCart selectShopCartFromRedis(String username){
        ShopCart shopCart = new ShopCart();

    }

    // 保存购物车到Redis中
    public void insertShopCartToRedis(ShopCart shopCart, String username) {
        List<BuyerItem> itemList = shopCart.getItemList();
        if (itemList.size() > 0){
            Map<String, String> map = new HashMap<String, String>();
            for (BuyerItem buyerItem : itemList) {
                if ()
            }
        }
    }
}
