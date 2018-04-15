package com.letu.share.controller;

import com.letu.share.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
public class RegisterController {

    @Autowired
    UserService userService;

    @RequestMapping(path = {"/reg/"}, method = {RequestMethod.POST})
    public String register(Model model,
                           @RequestParam("username") String username,
                           @RequestParam("password") String password,
                           @RequestParam("phonenumber") String phonenumber,
                           @RequestParam("gender") String gender,
                           @RequestParam("grade") int grade,
                           @RequestParam(value = "next", required = false) String next,
                           HttpServletResponse response) {
        try {
            Map<String, String> map = userService.register(username, password, phonenumber, gender, grade);
            if (map.containsKey("ticket")) {
                Cookie cookie = new Cookie("ticket", map.get("ticket"));
                cookie.setPath("/");
                response.addCookie(cookie);

//                if (!StringUtils.isEmpty(next)) {
//                    return "redirect:" + next;
//                }

                return "redirect:/";
            } else {
                model.addAttribute("msg", map.get("msg"));
                return "register";
            }
        } catch (Exception e) {
            model.addAttribute("msg", "");
            return "register";
        }
    }
}
