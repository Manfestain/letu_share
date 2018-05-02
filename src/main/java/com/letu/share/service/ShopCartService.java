package com.letu.share.service;

import com.letu.share.model.BuyerItem;
import com.letu.share.model.Commodity;
import com.letu.share.model.ShopCart;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class ShopCartService {
    private Jedis jedis = new Jedis();

    // 取出Redis中的购物车
    public ShopCart selectShopCartFromRedis(String username){
        ShopCart shopCart = new ShopCart();
        // 获取商品，Redis中保存的是CommodityId为key，amount为value的Map集合
        Map<String, String> map = jedis.hgetAll("shopCart:" + username);
        Set<Map.Entry<String, String>> entrySet = map.entrySet();
        for (Map.Entry<String, String> entry : entrySet) {
            Commodity commodity = new Commodity();
            commodity.setId(Integer.parseInt(entry.getKey()));
            BuyerItem buyerItem = new BuyerItem();
            buyerItem.setCommodity(commodity);
            buyerItem.setAmount(Integer.parseInt(entry.getValue()));
            shopCart.addItem(buyerItem);
        }
        return shopCart;
    }

    // 保存购物车到Redis中
    public void insertShopCartToRedis(ShopCart shopCart, String username) {
        List<BuyerItem> itemList = shopCart.getItemList();
        if (itemList.size() > 0){
            Map<String, String> map = new HashMap<String, String>();
            for (BuyerItem buyerItem : itemList) {
                // 判断是否有同款
                if (jedis.hexists("shopCart:" + username, String.valueOf(buyerItem.getCommodity().getId()))) {
                    jedis.hincrBy("shopCart:" + username, String.valueOf(buyerItem.getCommodity().getId()), buyerItem.getAmount());
                } else {
                    map.put(String.valueOf(buyerItem.getCommodity().getId()),
                            String.valueOf(buyerItem.getAmount()));
                }
            }
            if (map.size() > 0) {
                jedis.hmset("shopCart:" + username, map);
            }
        }
    }

    // 从购物车取出指定商品
    public ShopCart selectShopCartFromRedisByCommodiytId(String[] commodityIds, String username) {
        ShopCart shopCart = new ShopCart();
        Map<String, String> map = jedis.hgetAll("shopCart:" + username);
        if (map != null && map.size() > 0){
            Set<Map.Entry<String, String>> entrySet = map.entrySet();
            for (Map.Entry<String, String> entry : entrySet) {
                for (String commodityId : commodityIds) {
                    if (commodityId.equals(entry.getKey())) {
                        Commodity commodity = new Commodity();
                        commodity.setId(Integer.parseInt(entry.getValue()));
                        BuyerItem buyerItem = new BuyerItem();
                        buyerItem.setCommodity(commodity);
                        buyerItem.setAmount(Integer.parseInt(entry.getValue()));
                        shopCart.addItem(buyerItem);
                    }
                }
            }
        }
        return shopCart;
    }
}
