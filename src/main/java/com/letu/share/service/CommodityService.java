package com.letu.share.service;

import com.letu.share.Util.QiniuUtil;
import com.letu.share.dao.CommdityDAO;
import com.letu.share.model.Commodity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommodityService {
    @Autowired
    CommdityDAO commdityDAO;

    //获得食物
    public List<Commodity> getLateCommodity() {
        return commdityDAO.selectLateCommoditys();
    }


    // 验证添加的商品信息
    public Map<String, String> addCommodity(String commodityname,
                                            String commoditytype,
                                            String recommend,
                                            String region,
                                            File picture,
                                            String fileExtension,
                                            float price,
                                            int userId) {
        QiniuUtil qiniuUtil = new QiniuUtil();

        // 将图片上传到七牛云
        Map<String, String> map = qiniuUtil.uploadFile(picture, fileExtension);
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
        commodity.setUserId(userId);

        commdityDAO.addCommodity(commodity);
        map.clear();
        map.put("msg", "商品添加成功");
        return map;
    }

    // 获取商品信息
    public List<Commodity> getCommodity(int userId) {
        return commdityDAO.selectByUserId(userId);
    }

    // 通过Id获得商品
    public Commodity getCommodityById(int Id) {
        return commdityDAO.selectById(Id);
    }

    // 删除商品
    public Map<String, String>deleteCommodity(int id) {
        Map<String, String> map = new HashMap<String, String>();
        QiniuUtil qiniuUtil = new QiniuUtil();

        Commodity commodity = commdityDAO.selectById(id);
        if (commodity == null) {
            map.put("msg", "删除商品出错，请重新删除");
            return map;
        }

        String picture = commodity.getPicture();
        String filename = picture.substring(picture.lastIndexOf("/"));
        Map<String, String> tempMap = qiniuUtil.deleteFile(filename);
        if (tempMap.containsKey("msg")) {
            map.put("msg", tempMap.get("key"));
            return map;
        }

        commdityDAO.deleteById(id);
        map.put("key", "删除商品成功");
        return map;
    }
}
