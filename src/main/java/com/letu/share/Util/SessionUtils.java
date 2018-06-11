package com.letu.share.Util;

import com.letu.share.dao.UserDAO;
import com.letu.share.service.LoginTicketService;
import com.letu.share.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

public class SessionUtils {

    @Autowired
    UserService userService;

    @Autowired
    LoginTicketService loginTicketService;

    // 从cookie中获取ticket，并得到userName
    public String getUserNameByTicket(Cookie[] cookies) {
        String ticket = null;
        Integer userId = null;
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if ("ticket".equals(cookie.getName())) {
                    ticket = cookie.getValue();
                }
            }
        }
        System.out.println("ticket:" + ticket);
        if (userService == null) {
            System.out.println("userService is null");
        }
        if (userService != null && loginTicketService != null) {
            userId = loginTicketService.getUserIdByTicket(ticket);
            System.out.println(String.format("userId: %d", userId));
            return userService.getUserById(userId).getName();
        }
        return null;
    }
}
