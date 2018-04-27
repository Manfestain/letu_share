package com.letu.share.controller;

import com.letu.share.model.Comment;
import com.letu.share.model.Posting;
import com.letu.share.model.User;
import com.letu.share.model.ViewObject;
import com.letu.share.service.CommentService;
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

    @Autowired
    CommentService commentService;

    // 展示个人主页
    @RequestMapping(value = {"/person/{userId}/"}, method = {RequestMethod.GET})
    public String displayPerson(Model model,
                                @PathVariable("userId") int userId) {

        List<Posting> postingList = postingService.getPostingByUserId(userId);
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

    // 帖子详细页面
    @RequestMapping(value = {"/posting/{postingId}/"}, method = {RequestMethod.GET})
    public String postingDetail(Model model,
                                @PathVariable("postingId") int postingId) {

        Posting posting = postingService.getPostingById(postingId);
        List<ViewObject> vos = new ArrayList<ViewObject>();
        List<Comment> commentList = commentService.getCommentByPostingId(postingId);

        User user =userService.getUserById(posting.getUserId());
        model.addAttribute("user", user);

        if (posting != null) {
            model.addAttribute("posting", posting);
            if (commentList != null) {
                for(Comment comment: commentList) {
                    ViewObject vo = new ViewObject();
                    vo.set("comment", comment);
                    vo.set("sendU", userService.getUserById(comment.getSendId()));
                    vos.add(vo);
                }
            } else {
                ViewObject vo = new ViewObject();
                vo.set("flag", "您的帖子还没有评论哦！");
                vos.add(vo);
            }
        } else {
            model.addAttribute("msg", "打开出错！");
            return "redirect:/";
        }
        model.addAttribute("vos", vos);
        return "posting";
    }

    // 添加帖子
    @RequestMapping(value = {"/posting/{userId}/addposting"}, method = {RequestMethod.POST})
    public String addPosting(Model model,
                             @PathVariable("userId") int userId,
                             @RequestParam("title") String title,
                             @RequestParam("content") String content) {
        Map<String, String> map = postingService.addPosting(userId, title, content);
        return String.format("redirect:/person/%d/", userId);

    }

    // 添加帖子评论
    @RequestMapping(value = {"/posting/{postingId}/{userId}/"}, method = {RequestMethod.POST})
    public String addPostingComment(Model model,
                                    @PathVariable("postingId") int postingId,
                                    @PathVariable("userId") int userId,
                                    @RequestParam("sendId") int sendId,
                                    @RequestParam("content") String content) {
        Map<String, String> map = commentService.addComment(0, postingId, sendId, content);
        model.addAttribute("msg", map.get("msg"));
        return "";
    }
}
