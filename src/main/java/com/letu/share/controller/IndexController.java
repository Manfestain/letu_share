package com.letu.share.controller;

import com.alibaba.fastjson.JSONObject;
import com.letu.share.model.ShopCart;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class IndexController {

    @RequestMapping(path={"/", "/index"})
    @ResponseBody
    public String index(HttpSession httpSession) {
        return "hello letu" + httpSession.getAttribute("msg");
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
    @RequestMapping(path = {"/register"}, method = {RequestMethod.GET})
    public String registerin(Model model) {
        model.addAttribute("msg", "");
        return "register";
    }

    @RequestMapping(path = {"/login"}, method = {RequestMethod.GET})
    public String login(Model model) {
        model.addAttribute("msg", "");
        return "login";
    }
}
