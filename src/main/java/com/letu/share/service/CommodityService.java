package com.letu.share.service;

import com.letu.share.Util.QiniuUtil;
import com.letu.share.dao.CommdityDAO;
import com.letu.share.model.Commodity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

@Service
public class CommodityService {
    @Autowired
    CommdityDAO commdityDAO;

    // 验证添加的商品信息
    public Map<String, String> addCommodity(String commodityname,
                                            String commoditytype,
                                            String recommend,
                                            String region,
                                            FileInputStream picture,
                                            float price) {
        QiniuUtil qiniuUtil = new QiniuUtil();
//        Map<String, String> map = new HashMap<String, String>();

        // 将图片上传到七牛云
        Map<String, String> map = qiniuUtil.uploadFile(picture);
        if (map.containsKey("msg")) {
            return map;
        }

        Commodity commodity = new Commodity();
        commodity.setName(commodityname);
        commodity.setType(commoditytype);
        commodity.setRecommend(recommend);
        commodity.setRegion(region);
        String pictureURl = map.get("domain") + map.get("key");
        commodity.setPicture(pictureURl);
        commodity.setPrice(price);

        commdityDAO.addCommodity(commodity);
        map.clear();
        map.put("msg", "商品添加成功");
        return map;

    }
}
