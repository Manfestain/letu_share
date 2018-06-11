package com.letu.share.controller;

import com.alibaba.fastjson.JSONObject;
import com.letu.share.model.*;
import com.letu.share.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.util.*;

@Controller
public class IndexController {

    @Autowired
    PostingService postingService;

    @Autowired
    CommodityService commodityService;

    @Autowired
    LoginTicketService loginTicketService;

    @Autowired
    UserService userService;

    @Autowired
    CommentService commentService;

//------------------------------------------------------网站首页---------------------------------------------------------

    @RequestMapping(path={"/", "/index"})
    public String index(Model model,
                        HttpServletResponse response,
                        HttpServletRequest request) {
        HttpSession session = request.getSession();
        Cookie[] cookies = request.getCookies();
        boolean login_flag = false;   // 判断是否登陆
        List<ViewObject> vos = new ArrayList<ViewObject>();

        for (Cookie cookie : cookies) {
            if ("ticket".equals(cookie.getName())) {
                login_flag = true;
            }
        }

        List<Posting> postings = postingService.getLatePosting();
        if (!postings.isEmpty()) {
            for (Posting posting: postings) {
                ViewObject vo = new ViewObject();
                vo.set("posting", posting);
                vos.add(vo);
            }
        }

        model.addAttribute("login_flag", login_flag);
        model.addAttribute("vos", vos);

        return "home";
    }

    @RequestMapping(path = {"/register"})
    public String registerin(Model model) {
        model.addAttribute("msg", "");
        return "register";
    }

    @RequestMapping(path = {"/login"})
    public String login(Model model) {
        model.addAttribute("msg", "");
        return "login";
    }

//------------------------------------------------------------美食主页----------------------------------------------------

    @RequestMapping(path={"/food"})
    public String foodindex(Model model,
                        HttpServletResponse response,
                        HttpServletRequest request) {
        HttpSession session = request.getSession();
        Cookie[] cookies = request.getCookies();
        boolean login_flag = false;   // 判断是否登陆
        List<ViewObject> vos = new ArrayList<ViewObject>();

        for (Cookie cookie : cookies) {
            if ("ticket".equals(cookie.getName())) {
                login_flag = true;
            }
        }

        List<Commodity> commodities = commodityService.getLateCommodity();
        if (!commodities.isEmpty()) {
            for (Commodity commodity: commodities) {
                ViewObject vo = new ViewObject();
                vo.set("food", commodity);
                vos.add(vo);
            }
        }

        model.addAttribute("login_flag", login_flag);
        model.addAttribute("vos", vos);

        return "food";
    }


//----------------------------------------------------个人主页-----------------------------------------------------------

    @RequestMapping(path={"/person"})
    public String personindex(Model model,
                            HttpServletResponse response,
                            HttpServletRequest request) {
        HttpSession session = request.getSession();
        Cookie[] cookies = request.getCookies();
        boolean login_flag = false;   // 判断是否登陆
        List<ViewObject> vos = new ArrayList<ViewObject>();



        for (Cookie cookie : cookies) {
            if ("ticket".equals(cookie.getName())) {
                login_flag = true;
                String ticket = cookie.getValue();

                int userId = loginTicketService.getUserIdByTicket(ticket);
                List<Posting> postings = postingService.getPostingByUserId(userId);
                if (!postings.isEmpty()) {
                    for (Posting posting: postings) {
                        ViewObject vo = new ViewObject();
                        vo.set("posting", posting);
                        vos.add(vo);
                    }
                }

                User user = userService.getUserById(userId);

                model.addAttribute("user", user);
                model.addAttribute("login_flag", login_flag);
                model.addAttribute("vos", vos);
            }
        }
        return "person";
    }

    @RequestMapping(path={"/person/{userId}"})
    public String personindex(Model model,
                              HttpServletResponse response,
                              HttpServletRequest request,
                              @PathVariable(value = "userId") int userId) {
        HttpSession session = request.getSession();
        Cookie[] cookies = request.getCookies();
        boolean login_flag = false;   // 判断是否登陆
        List<ViewObject> vos = new ArrayList<ViewObject>();

        for (Cookie cookie: cookies) {
            if ("ticket".equals(cookie.getName())) {
                login_flag = true;
            }
        }

        List<Posting> postings = postingService.getPostingByUserId(userId);
        if (!postings.isEmpty()) {
            for (Posting posting: postings) {
                ViewObject vo = new ViewObject();
                vo.set("posting", posting);
                vos.add(vo);
            }
        }

        User user = userService.getUserById(userId);

        model.addAttribute("user", user);
        model.addAttribute("login_flag", login_flag);
        model.addAttribute("vos", vos);

        return "person";
    }

//--------------------------------------------博客页---------------------------------------------------------------------

    @RequestMapping(path = {"/person/{userId}/{postingId}"}, method = RequestMethod.GET)
    public String postingIndex(Model model,
                               HttpServletRequest request,
                               HttpServletResponse response,
                               @PathVariable(value = "userId") int userId,
                               @PathVariable(value = "postingId") int postingId) {
        Cookie[] cookies = request.getCookies();
        boolean login_flag = false;   // 判断是否登陆
        List<ViewObject> vos = new ArrayList<ViewObject>();

        for (Cookie cookie: cookies) {
            if ("ticket".equals(cookie.getName())) {
                login_flag = true;
            }
        }

        Posting posting = postingService.getPostingById(postingId);
        User user = userService.getUserById(userId);

        List<Comment> comments = commentService.getCommentByPostingId(postingId);
        if (!comments.isEmpty()) {
            for (Comment comment: comments) {
                ViewObject vo = new ViewObject();
                User user_ = userService.getUserById(comment.getSendId());
                vo.set("comment", comment);
                vo.set("user", user_);
                vos.add(vo);
            }
        }

        model.addAttribute("vos", vos);
        model.addAttribute("user", user);
        model.addAttribute("posting", posting);
        model.addAttribute("login_flag", login_flag);

        return "posting";


    }



//
//    ///解析路径中的参数
//    @RequestMapping(path={"/profill/{groupId}/{userId}"})
//    ///@RequestMapping(path={"..."}, method={RequestMethod.GET}) 可以指定方法，当提交数据时一般使用post
//    @ResponseBody
//    public String profill(@PathVariable("userId") int userId,
//                          @PathVariable("groupId") String groupId,
//                          @RequestParam(value = "type", defaultValue = "1011") int type,
//                          @RequestParam(value = "key", required = false) String key) {
//        return String.format("Profill Page of %s / %d, type: %d  key: %s", groupId, userId, type, key);
//    }
//
//    ///使用模板引擎
//    @RequestMapping(path = {"/vm"}, method = {RequestMethod.GET})
//    public String template(Model model) {
//        model.addAttribute("value1", "vvvvvvv1");
//        List<String> colors = Arrays.asList(new String[]{"RED", "GREEN", "BLUE"});
//        model.addAttribute("colors", colors);
//
//        Map<String, String> map = new HashMap<>();
//        for (int i = 0; i < 4; ++i) {
//            map.put(String.valueOf(i), String.valueOf(i * i));
//        }
//        model.addAttribute("map", map);
//
//        return "home";
//    }
//
//    @RequestMapping(path = {"/request"}, method = {RequestMethod.GET})
//    @ResponseBody
//    public String request(Model model,
//                          HttpServletResponse response,
//                          HttpServletRequest request,
//                          HttpSession httpSession,
//                          @CookieValue("JSESSIONID") String sessionID) {
//        ///请求信息
//        StringBuilder sb = new StringBuilder();
//        sb.append("JSESSIONID:" + sessionID + "<br>");
//        Enumeration<String> headerNames = request.getHeaderNames();
//        while (headerNames.hasMoreElements()) {
//            String name = headerNames.nextElement();
//            sb.append(name + ":" + request.getHeader(name) + "<br>");
//        }
//        if (request.getCookies() != null) {
//            for (Cookie cookie: request.getCookies()) {
//                sb.append("Cookies:" + cookie.getName() + "value:" + cookie.getValue() + "<br>");
//            }
//        }
//        sb.append(request.getMethod() + "<br>");
//        sb.append(request.getQueryString() + "<br>");
//        sb.append(request.getPathInfo() + "<br>");
//        sb.append(request.getRequestURL() + "<br>");
//
//        ///返回信息
//        response.addHeader("letu", "hello");
//        response.addCookie(new Cookie("username", "admin"));
//
//        return sb.toString();
//    }
//
//    ///跳转
//    @RequestMapping(path = {"/redirect/{code}"}, method = {RequestMethod.GET})
//    public String redirect(@PathVariable("code") int code,
//                           HttpSession httpSession) {
//        httpSession.setAttribute("msg", "jump from redirect");
//        return "redirect:/";
//    }
//
//    ///异常处理
//    @RequestMapping(path = {"/admin"}, method = {RequestMethod.GET})
//    @ResponseBody
//    public String admin(@RequestParam("key") String key) {
//        if ("admin".equals(key)) {
//            return "hello admin!!!";
//        }
//        ///throw new IllegalAccessException("You have no right to access!");
//        return null;
//    }
//
//    @ExceptionHandler()
//    @ResponseBody
//    public String error(Exception e) {
//        return "error: " + e.getMessage();
//    }

}
