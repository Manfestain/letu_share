package com.letu.share.controller;

import com.letu.share.Util.MultipartFileUtil;
import com.letu.share.model.Commodity;
import com.letu.share.model.ViewObject;
import com.letu.share.service.CommodityService;
import com.letu.share.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
public class CommodityController {
    @Autowired
    CommodityService commodityService;

    @Autowired
    UserService userService;

    @RequestMapping(value = {"/addcom"}, method = {RequestMethod.GET})
    public String addCommodityPage(Model model,
                                   HttpServletResponse servletResponse) {
        model.addAttribute("msg", "");
        return "addgoods";
    }

    // 添加商品信息
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
            // MultipartFile转为File
            String fileName = commodityimage.getOriginalFilename();
            String fileExtension = fileName.substring(commodityimage.getOriginalFilename().lastIndexOf("."));
            final File file = multipartFileUtil.multipartFiletoFile(commodityimage, fileExtension);

            // 业务逻辑
            if (file != null) {
                Map<String, String> map = commodityService.addCommodity(commodityname, commoditytype,
                        recommend, region, file, fileExtension, price, 9);
                model.addAttribute("msg", map.get("msg"));
                multipartFileUtil.deleteFile(file);

                return "redirect:/";
            } else {
                model.addAttribute("msg", "上传出错, 请重新上传");
                return "addgoods";
            }
        } catch (Exception e) {
            model.addAttribute("msg", "上传出错, 请重新上传");
            return "addgoods";
        }
    }

    // 展示店家主页
    @RequestMapping(value = {"/shop/{userId}/"}, method = {RequestMethod.GET})
    public String displayShop(Model model,
                              @PathVariable("userId") int userId) {
        List<Commodity> commodityList = commodityService.getCommodity(userId);
        List<ViewObject> vos = new ArrayList<ViewObject>();
        String userName = userService.getUserById(userId).getName();
        model.addAttribute("userName", userName);

        if(!commodityList.isEmpty()) {
            for(Commodity commodity: commodityList) {
                ViewObject vo = new ViewObject();
                vo.set("commodity", commodity);
                vos.add(vo);
            }
            model.addAttribute("flag", "OK");
            model.addAttribute("vos", vos);
            return "seller";
        }
        model.addAttribute("flag", "");
        return "seller";
    }

}
