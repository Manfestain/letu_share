package com.letu.share.model;

// 购物项
public class BuyerItem {
    private Commodity commodity;
    private Boolean isHave = true;
    private int amount = 1;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        Commodity temp = (Commodity) obj;
        if (this.commodity.getId() == temp.getId()) {
            return true;
        }
        return false;
    }

    public Commodity getCommodity() {
        return commodity;
    }

    public void setCommodity(Commodity commodity) {
        this.commodity = commodity;
    }

    public Boolean getHave() {
        return isHave;
    }

    public void setHave(Boolean have) {
        isHave = have;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
