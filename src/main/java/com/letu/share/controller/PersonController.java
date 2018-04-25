package com.letu.share.controller;

import com.letu.share.model.Posting;
import com.letu.share.model.User;
import com.letu.share.model.ViewObject;
import com.letu.share.service.PostingService;
import com.letu.share.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class PersonController {
    @Autowired
    PostingService postingService;

    @Autowired
    UserService userService;

    // 展示个人主页
    @RequestMapping(value = {"/person/{userId}/"}, method = {RequestMethod.GET})
    public String displayPerson(Model model,
                                @PathVariable("userId") int userId) {

        List<Posting> postingList = postingService.getPosting(userId);
        List<ViewObject> vos = new ArrayList<ViewObject>();

        User user = userService.getUserById(userId);
        model.addAttribute("user", user);

        if (!postingList.isEmpty()) {
            for (Posting posting: postingList) {
                ViewObject vo = new ViewObject();
                vo.set("posting", posting);
                vos.add(vo);
            }
            model.addAttribute("flag", "OK");
            model.addAttribute("vos", vos);
            return "person";
        }
        model.addAttribute("flag", "");
        return "person";
    }

    // 添加帖子
    @RequestMapping(value = {"/person/{userId}/addposting"}, method = {RequestMethod.POST})
    public String addPosting(Model model,
                             @PathVariable("userId") int userId,
                             @RequestParam("title") String title,
                             @RequestParam("content") String content) {
        Map<String, String> map = postingService.addPosting(userId, title, content);
        return String.format("redirect:/person/%d/", userId);

    }
}
