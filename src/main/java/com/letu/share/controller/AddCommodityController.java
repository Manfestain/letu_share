package com.letu.share.controller;

import com.letu.share.Util.MultipartFileUtil;
import com.letu.share.dao.CommdityDAO;
import com.letu.share.service.CommodityService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.util.Map;

@Controller
public class AddCommodityController {
    @Autowired
    CommodityService commodityService;

    @RequestMapping(value = {"/addcom"}, method = {RequestMethod.GET})
    public String addCommodityPage(Model model,
                                   HttpServletResponse servletResponse) {
        model.addAttribute("msg", "");
        return "addgoods";
    }

    @RequestMapping(value = {"/addcommodity/"}, method = {RequestMethod.POST})
    public String addCommodity(Model model,
                               @RequestParam("commodityname") String commodityname,
                               @RequestParam("commoditytype") String commoditytype,
                               @RequestParam("recommend") String recommend,
                               @RequestParam("region") String region,
                               @RequestParam("commodityimage") MultipartFile commodityimage,
                               @RequestParam("price") float price,
                               HttpServletResponse servletResponse) {
        MultipartFileUtil multipartFileUtil = new MultipartFileUtil();
        try {
            String fileExtension = commodityimage.getOriginalFilename().substring(commodityimage.getOriginalFilename().lastIndexOf("."));
            File file = multipartFileUtil.multipartFiletoFile(commodityimage);
            Map<String, String> map = commodityService.addCommodity(commodityname, commoditytype,
                    recommend, region, file, fileExtension, price);
            model.addAttribute("msg", map.get("msg"));
            return "redirect:/";
        } catch (Exception e) {
            model.addAttribute("msg", "上传出错");
            return "addgoods";
        }

    }

}
