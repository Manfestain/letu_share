package com.letu.share.model;

import java.util.ArrayList;
import java.util.List;

// 购物车
public class ShopCart {
    private List<BuyerItem> itemList = new ArrayList<BuyerItem>();

    // 添加购物项到购物车
    public void addItem(BuyerItem item) {
        if (itemList.contains(item)) {
            for (BuyerItem buyerItem : itemList) {
                if (buyerItem.equals(item)) {
                    buyerItem.setAmount(item.getAmount() + buyerItem.getAmount());
                }
            }
        } else {
            itemList.add(item);
        }
    }

    // 商品总数量
    public int getProductAmount() {
        int result = 0;
        for (BuyerItem buyerItem: itemList) {
            result += buyerItem.getAmount();
        }
        return result;
    }

    // 商品总金额
    public float getTotalPrice() {
        float result = 0f;
        for (BuyerItem buyerItem : itemList) {
            result += buyerItem.getAmount() * buyerItem.getCommodity().getPrice();
        }
        return result;
    }

    public List<BuyerItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<BuyerItem> itemList) {
        this.itemList = itemList;
    }
}
